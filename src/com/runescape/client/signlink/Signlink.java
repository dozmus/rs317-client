package com.runescape.client.signlink;

import java.applet.Applet;
import java.io.*;
import java.net.*;

public final class Signlink
        implements Runnable {

    public static final int CLIENT_VERSION = 317;
    public static int uid;
    public static int storeid = 32;
    public static RandomAccessFile cache_dat = null;
    public static final RandomAccessFile[] cache_idx = new RandomAccessFile[5];
    public static boolean sunjava;
    public static final Applet mainapp = null;
    private static boolean active;
    private static int threadliveid;
    private static InetAddress socketip;
    private static int socketreq;
    private static Socket socket = null;
    private static int threadreqpri = 1;
    private static Runnable threadreq = null;
    private static String dnsreq = null;
    public static String dns = null;
    private static String urlreq = null;
    private static DataInputStream urlstream = null;
    private static int saveItemLength;
    private static String savereq = null;
    private static byte[] saveItemData = null;
    private static boolean midiplay;
    private static int midiPos;
    public static String midi = null;
    public static int midivol;
    public static int midifade;
    private static boolean waveplay;
    private static int wavePos;
    public static int wavevol;
    public static boolean reporterror = true;
    public static String errorname = "";

    public static void startSignlink(InetAddress address) {
        threadliveid = (int) (Math.random() * 99999999D);
        
        if (active) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException _ex) {
            }
            active = false;
        }
        socketreq = 0;
        threadreq = null;
        dnsreq = null;
        savereq = null;
        urlreq = null;
        socketip = address;
        Thread thread = new Thread(new Signlink());
        thread.setDaemon(true);
        thread.start();
        
        while (!active) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException _ex) {
            }
        }
    }

    private static String getCacheDir() {
        return "./cache/";
    }

    public static String findCacheDir() {
        String directories[] = {
            "c:/windows/", "c:/winnt/", "d:/windows/", "d:/winnt/", "e:/windows/",
            "e:/winnt/", "f:/windows/", "f:/winnt/", "c:/", "~/", "/tmp/", "",
            "c:/rscache", "/rscache"
        };
        
        if (storeid < 32 || storeid > 34) {
            storeid = 32;
        }
        String s = ".file_store_" + storeid;
        
        for (int i = 0; i < directories.length; i++) {
            try {
                String s1 = directories[i];
                
                if (s1.length() > 0) {
                    File file = new File(s1);
                    
                    if (!file.exists()) {
                        continue;
                    }
                }
                File file1 = new File(s1 + s);
                
                if (file1.exists() || file1.mkdir()) {
                    return s1 + s + "/";
                }
            } catch (Exception _ex) {
            }
        }
        return null;
    }

    private static int getUid(String cacheDir) {
        // Generating a UID, if one doesn't exist
        try {
            File file = new File(cacheDir + "uid.dat");
            
            if (!file.exists() || file.length() < 4L) {
                int uid = (int) (Math.random() * 99999999D);
                
                try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(cacheDir + "uid.dat"))) {
                    dos.writeInt(uid);
                }
                return uid;
            }
        } catch (IOException _ex) {
        }
        
        // Reading the stored UID
        try {
            int uid;
            
            try (DataInputStream dis = new DataInputStream(new FileInputStream(cacheDir + "uid.dat"))) {
                uid = dis.readInt();
            }
            return uid + 1;
        } catch (IOException _ex) {
            return 0;
        }
    }

    public static synchronized Socket getSocket(int socketReq) throws IOException {
        for (socketreq = socketReq; socketreq != 0;) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException _ex) {
            }
        }

        if (socket == null) {
            throw new IOException("could not open socket");
        } else {
            return socket;
        }
    }

    public static synchronized DataInputStream openUrl(String s) throws IOException {
        for (urlreq = s; urlreq != null;) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException _ex) {
            }
        }

        if (urlstream == null) {
            throw new IOException("could not open: " + s);
        } else {
            return urlstream;
        }
    }

    public static synchronized void dnsLookup(String s) {
        dns = s;
        dnsreq = s;
    }

    public static synchronized void startThread(Runnable runnable, int i) {
        threadreqpri = i;
        threadreq = runnable;
    }

    public static synchronized boolean saveWave(byte buffer[], int length) {
        if (length > 0x1e8480) {
            return false;
        }
        
        if (savereq == null) {
            wavePos = (wavePos + 1) % 5;
            saveItemLength = length;
            saveItemData = buffer;
            waveplay = true;
            savereq = "sound" + wavePos + ".wav";
            return true;
        }
        return false;
    }

    public static synchronized boolean replayWave() {
        if (savereq == null) {
            saveItemData = null;
            waveplay = true;
            savereq = "sound" + wavePos + ".wav";
            return true;
        }
        return false;
    }

    public static synchronized void saveMidi(byte buffer[], int length) {
        if (length > 0x1e8480) {
            return;
        }
        
        if (savereq == null) {
            midiPos = (midiPos + 1) % 5;
            saveItemLength = length;
            saveItemData = buffer;
            midiplay = true;
            savereq = "jingle" + midiPos + ".mid";
        }
    }

    public static void printError(String error) {
        System.out.println("Error: " + error);
    }

    private Signlink() {
    }

    public void run() {
        active = true;
        String cacheDir = getCacheDir();
        uid = getUid(cacheDir);
        
        try {
            File file = new File(cacheDir + "main_file_cache.dat");
            
            if (file.exists() && file.length() > 0x3200000L) {
                file.delete();
            }
            cache_dat = new RandomAccessFile(cacheDir + "main_file_cache.dat", "rw");
            
            for (int index = 0; index < 5; index++) {
                cache_idx[index] = new RandomAccessFile(cacheDir + "main_file_cache.idx" + index, "rw");
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        
        for (int i = threadliveid; threadliveid == i;) {
            if (socketreq != 0) {
                try {
                    socket = new Socket(socketip, socketreq);
                } catch (IOException _ex) {
                    socket = null;
                }
                socketreq = 0;
            } else if (threadreq != null) {
                Thread thread = new Thread(threadreq);
                thread.setDaemon(true);
                thread.start();
                thread.setPriority(threadreqpri);
                threadreq = null;
            } else if (dnsreq != null) {
                try {
                    dns = InetAddress.getByName(dnsreq).getHostName();
                } catch (UnknownHostException _ex) {
                    dns = "unknown";
                }
                dnsreq = null;
            } else if (savereq != null) {
                if (saveItemData != null) {
                    try {
                        try (FileOutputStream fos = new FileOutputStream(cacheDir + savereq)) {
                            fos.write(saveItemData, 0, saveItemLength);
                        }
                    } catch (IOException _ex) {
                    }
                }
                
                if (waveplay) {
                    String wave = cacheDir + savereq;
                    waveplay = false;
                }
                
                if (midiplay) {
                    midi = cacheDir + savereq;
                    midiplay = false;
                }
                savereq = null;
            } else if (urlreq != null) {
                try {
                    System.out.println("urlstream");
                    urlstream = new DataInputStream((new URL(mainapp.getCodeBase(), urlreq)).openStream());
                } catch (IOException _ex) {
                    urlstream = null;
                }
                urlreq = null;
            }
            
            try {
                Thread.sleep(50L);
            } catch (InterruptedException _ex) {
            }
        }
    }
}