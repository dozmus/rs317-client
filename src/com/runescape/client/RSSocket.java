package com.runescape.client;

import java.io.*;
import java.net.Socket;

/**
 * A game socket, which holds both a {@link Socket} and {@link RSApplet} reference.
 * 
 * @author Pure_
 */
final class RSSocket implements Runnable {

    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final Socket socket;
    private boolean closed;
    private final RSApplet rsApplet;
    private byte[] buffer;
    private int writeIndex;
    private int buffIndex;
    private boolean isWriter;
    private boolean hasIOError;

    public RSSocket(RSApplet rsApplet, Socket socket) throws IOException {
        closed = false;
        isWriter = false;
        hasIOError = false;
        this.rsApplet = rsApplet;
        this.socket = socket;
        socket.setSoTimeout(30000);
        socket.setTcpNoDelay(true);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    public void close() {
        closed = true;
        
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            
            if (outputStream != null) {
                outputStream.close();
            }
            
            if (socket != null) {
                socket.close();
            }
        } catch (IOException _ex) {
            System.out.println("Error closing stream");
        }
        isWriter = false;
        
        synchronized (this) {
            notify();
        }
        buffer = null;
    }

    public int read() throws IOException {
        return closed ? 0 : inputStream.read();
    }

    public int available() throws IOException {
        return closed ? 0 : inputStream.available();
    }

    public void flushInputStream(byte buffer[], int length) throws IOException {
        int offset = 0;
        
        if (closed) {
            return;
        }
        int value;
        
        for (; length > 0; length -= value) {
            value = inputStream.read(buffer, offset, length);
            
            if (value <= 0) {
                throw new IOException("EOF");
            }
            offset += value;
        }
    }

    public void queueBytes(int i, byte buf[]) throws IOException {
        if (closed) {
            return;
        }
        
        if (hasIOError) {
            hasIOError = false;
            throw new IOException("Error in writer thread");
        }
        
        if (buffer == null) {
            buffer = new byte[5000];
        }
        
        synchronized (this) {
            for (int l = 0; l < i; l++) {
                buffer[buffIndex] = buf[l];
                buffIndex = (buffIndex + 1) % 5000;
                
                if (buffIndex == (writeIndex + 4900) % 5000) {
                    throw new IOException("buffer overflow");
                }
            }

            if (!isWriter) {
                isWriter = true;
                rsApplet.startRunnable(this, 3);
            }
            notify();
        }
    }

    public void run() {
        while (isWriter) {
            int i;
            int j;
            
            synchronized (this) {
                if (buffIndex == writeIndex) {
                    try {
                        wait();
                    } catch (InterruptedException _ex) {
                    }
                }
                
                if (!isWriter) {
                    return;
                }
                j = writeIndex;
                
                if (buffIndex >= writeIndex) {
                    i = buffIndex - writeIndex;
                } else {
                    i = 5000 - writeIndex;
                }
            }
            
            if (i > 0) {
                try {
                    outputStream.write(buffer, j, i);
                } catch (IOException _ex) {
                    hasIOError = true;
                }
                writeIndex = (writeIndex + i) % 5000;
                
                try {
                    if (buffIndex == writeIndex) {
                        outputStream.flush();
                    }
                } catch (IOException _ex) {
                    hasIOError = true;
                }
            }
        }
    }

    public void printDebug() {
        System.out.println("closed:" + closed);
        System.out.println("write-idx:" + writeIndex);
        System.out.println("buf-idx:" + buffIndex);
        System.out.println("writer?" + isWriter);
        System.out.println("io-error?" + hasIOError);
        
        try {
            System.out.println("available:" + available());
        } catch (IOException _ex) {
        }
    }
}