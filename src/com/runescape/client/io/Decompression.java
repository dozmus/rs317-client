package com.runescape.client.io;

import java.io.IOException;
import java.io.RandomAccessFile;

public final class Decompression {

    private static final byte[] buffer = new byte[520];
    private final RandomAccessFile dataFile;
    private final RandomAccessFile indexFile;
    private final int anInt311;

    public Decompression(RandomAccessFile dataFile, RandomAccessFile indexFile, int j) {
        anInt311 = j;
        this.dataFile = dataFile;
        this.indexFile = indexFile;
    }

    public synchronized byte[] decompress(int pos) {
        try {
            seek(indexFile, pos * 6);
            int l;
            
            for (int j = 0; j < 6; j += l) {
                l = indexFile.read(buffer, j, 6 - j);
                
                if (l == -1) {
                    return null;
                }
            }
            int i1 = ((buffer[0] & 0xff) << 16) + ((buffer[1] & 0xff) << 8) + (buffer[2] & 0xff);
            int pos1 = ((buffer[3] & 0xff) << 16) + ((buffer[4] & 0xff) << 8) + (buffer[5] & 0xff);
            
            if (i1 < 0 || i1 > 0x7a120) {
                return null;
            }
            
            if (pos1 <= 0 || (long) pos1 > dataFile.length() / 520L) {
                return null;
            }
            byte buf[] = new byte[i1];
            int k1 = 0;
            
            for (int l1 = 0; k1 < i1; l1++) {
                if (pos1 == 0) {
                    return null;
                }
                seek(dataFile, pos1 * 520);
                int k = 0;
                int i2 = i1 - k1;
                
                if (i2 > 512) {
                    i2 = 512;
                }
                int j2;
                
                for (; k < i2 + 8; k += j2) {
                    j2 = dataFile.read(buffer, k, (i2 + 8) - k);
                    if (j2 == -1) {
                        return null;
                    }
                }
                int k2 = ((buffer[0] & 0xff) << 8) + (buffer[1] & 0xff);
                int l2 = ((buffer[2] & 0xff) << 8) + (buffer[3] & 0xff);
                int i3 = ((buffer[4] & 0xff) << 16) + ((buffer[5] & 0xff) << 8) + (buffer[6] & 0xff);
                int j3 = buffer[7] & 0xff;
                
                if (k2 != pos || l2 != l1 || j3 != anInt311) {
                    return null;
                }
                
                if (i3 < 0 || (long) i3 > dataFile.length() / 520L) {
                    return null;
                }
                
                for (int k3 = 0; k3 < i2; k3++) {
                    buf[k1++] = buffer[k3 + 8];
                }
                pos1 = i3;
            }
            return buf;
        } catch (IOException _ex) {
            return null;
        }
    }

    public synchronized boolean method234(int length, byte buf[], int j) {
        boolean flag = method235(true, j, length, buf);
        
        if (!flag) {
            flag = method235(false, j, length, buf);
        }
        return flag;
    }

    private synchronized boolean method235(boolean flag, int pos, int length, byte buf[]) {
        try {
            int basePos;
            
            if (flag) {
                seek(indexFile, pos * 6);
                int k1;
                
                for (int i1 = 0; i1 < 6; i1 += k1) {
                    k1 = indexFile.read(buffer, i1, 6 - i1);
                    
                    if (k1 == -1) {
                        return false;
                    }
                }
                basePos = ((buffer[3] & 0xff) << 16) + ((buffer[4] & 0xff) << 8) + (buffer[5] & 0xff);
                
                if (basePos <= 0 || (long) basePos > dataFile.length() / 520L) {
                    return false;
                }
            } else {
                basePos = (int) ((dataFile.length() + 519L) / 520L);
                
                if (basePos == 0) {
                    basePos = 1;
                }
            }
            buffer[0] = (byte) (length >> 16);
            buffer[1] = (byte) (length >> 8);
            buffer[2] = (byte) length;
            buffer[3] = (byte) (basePos >> 16);
            buffer[4] = (byte) (basePos >> 8);
            buffer[5] = (byte) basePos;
            seek(indexFile, pos * 6);
            indexFile.write(buffer, 0, 6);
            int startPosition = 0;
            
            for (int l1 = 0; startPosition < length; l1++) {
                int nextPos = 0;
                
                if (flag) {
                    seek(dataFile, basePos * 520);
                    int j2;
                    int l2;
                    
                    for (j2 = 0; j2 < 8; j2 += l2) {
                        l2 = dataFile.read(buffer, j2, 8 - j2);
                        
                        if (l2 == -1) {
                            break;
                        }
                    }

                    if (j2 == 8) {
                        int i3 = ((buffer[0] & 0xff) << 8) + (buffer[1] & 0xff);
                        int j3 = ((buffer[2] & 0xff) << 8) + (buffer[3] & 0xff);
                        nextPos = ((buffer[4] & 0xff) << 16) + ((buffer[5] & 0xff) << 8) + (buffer[6] & 0xff);
                        int k3 = buffer[7] & 0xff;
                        
                        if (i3 != pos || j3 != l1 || k3 != anInt311) {
                            return false;
                        }
                        
                        if (nextPos < 0 || (long) nextPos > dataFile.length() / 520L) {
                            return false;
                        }
                    }
                }
                
                if (nextPos == 0) {
                    flag = false;
                    nextPos = (int) ((dataFile.length() + 519L) / 520L);
                    
                    if (nextPos == 0) {
                        nextPos++;
                    }
                    
                    if (nextPos == basePos) {
                        nextPos++;
                    }
                }
                
                if (length - startPosition <= 512) {
                    nextPos = 0;
                }
                buffer[0] = (byte) (pos >> 8);
                buffer[1] = (byte) pos;
                buffer[2] = (byte) (l1 >> 8);
                buffer[3] = (byte) l1;
                buffer[4] = (byte) (nextPos >> 16);
                buffer[5] = (byte) (nextPos >> 8);
                buffer[6] = (byte) nextPos;
                buffer[7] = (byte) anInt311;
                seek(dataFile, basePos * 520);
                dataFile.write(buffer, 0, 8);
                int writeLength = length - startPosition;
                
                if (writeLength > 512) {
                    writeLength = 512;
                }
                dataFile.write(buf, startPosition, writeLength);
                startPosition += writeLength;
                basePos = nextPos;
            }
            return true;
        } catch (IOException _ex) {
            return false;
        }
    }

    private synchronized void seek(RandomAccessFile file, int pos) throws IOException {
        if (pos < 0 || pos > 0x3c00000) {
            System.out.println("Badseek - pos:" + pos + " len:" + file.length());
            pos = 0x3c00000;
            
            try {
                Thread.sleep(1000L);
            } catch (Exception _ex) {
            }
        }
        file.seek(pos);
    }

}
