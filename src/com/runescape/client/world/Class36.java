package com.runescape.client.world;

import com.runescape.client.io.Stream;

public final class Class36 {

    private static Class36[] cache;
    private static boolean[] aBooleanArray643;

    public static void method528(int length) {
        cache = new Class36[length + 1];
        aBooleanArray643 = new boolean[length + 1];

        for (int j = 0; j < length + 1; j++) {
            aBooleanArray643[j] = true;
        }
    }

    public static void method529(byte buf[]) {
        Stream stream = new Stream(buf);
        stream.currentOffset = buf.length - 8;
        int i = stream.readUShort();
        int j = stream.readUShort();
        int k = stream.readUShort();
        int l = stream.readUShort();
        int offset = 0;
        Stream stream_1 = new Stream(buf);
        stream_1.currentOffset = offset;
        offset += i + 2;
        Stream stream_2 = new Stream(buf);
        stream_2.currentOffset = offset;
        offset += j;
        Stream stream_3 = new Stream(buf);
        stream_3.currentOffset = offset;
        offset += k;
        Stream stream_4 = new Stream(buf);
        stream_4.currentOffset = offset;
        offset += l;
        Stream stream_5 = new Stream(buf);
        stream_5.currentOffset = offset;
        Class18 class18 = new Class18(stream_5);
        int k1 = stream_1.readUShort();
        int ai[] = new int[500];
        int ai1[] = new int[500];
        int ai2[] = new int[500];
        int ai3[] = new int[500];

        for (int l1 = 0; l1 < k1; l1++) {
            int i2 = stream_1.readUShort();
            Class36 class36 = cache[i2] = new Class36();
            class36.anInt636 = stream_4.readUByte();
            class36.aClass18_637 = class18;
            int j2 = stream_1.readUByte();
            int k2 = -1;
            int l2 = 0;
            for (int i3 = 0; i3 < j2; i3++) {
                int j3 = stream_2.readUByte();
                if (j3 > 0) {
                    if (class18.anIntArray342[i3] != 0) {
                        for (int l3 = i3 - 1; l3 > k2; l3--) {
                            if (class18.anIntArray342[l3] != 0) {
                                continue;
                            }
                            ai[l2] = l3;
                            ai1[l2] = 0;
                            ai2[l2] = 0;
                            ai3[l2] = 0;
                            l2++;
                            break;
                        }

                    }
                    ai[l2] = i3;
                    char c = '\0';
                    if (class18.anIntArray342[i3] == 3) {
                        c = '\200';
                    }
                    if ((j3 & 1) != 0) {
                        ai1[l2] = stream_3.readSmart();
                    } else {
                        ai1[l2] = c;
                    }
                    if ((j3 & 2) != 0) {
                        ai2[l2] = stream_3.readSmart();
                    } else {
                        ai2[l2] = c;
                    }
                    if ((j3 & 4) != 0) {
                        ai3[l2] = stream_3.readSmart();
                    } else {
                        ai3[l2] = c;
                    }
                    k2 = i3;
                    l2++;
                    
                    if (class18.anIntArray342[i3] == 5) {
                        aBooleanArray643[i2] = false;
                    }
                }
            }

            class36.anInt638 = l2;
            class36.anIntArray639 = new int[l2];
            class36.anIntArray640 = new int[l2];
            class36.anIntArray641 = new int[l2];
            class36.anIntArray642 = new int[l2];
            
            for (int k3 = 0; k3 < l2; k3++) {
                class36.anIntArray639[k3] = ai[k3];
                class36.anIntArray640[k3] = ai1[k3];
                class36.anIntArray641[k3] = ai2[k3];
                class36.anIntArray642[k3] = ai3[k3];
            }
        }
    }

    public static void nullLoader() {
        cache = null;
    }

    public static Class36 method531(int i) {
        if (cache == null) {
            return null;
        } else {
            return cache[i];
        }
    }

    public static boolean isNotMinus1(int i) {
        return i == -1;
    }

    public int anInt636;
    public Class18 aClass18_637;
    public int anInt638;
    public int anIntArray639[];
    public int anIntArray640[];
    public int anIntArray641[];
    public int anIntArray642[];

    private Class36() {
        
    }

}
