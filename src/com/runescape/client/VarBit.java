package com.runescape.client;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.

import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
public final class VarBit {

    public static void unpackConfig(StreamLoader streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("varbit.dat"));
        int cacheSize = stream.readUShort();
        if (cache == null) {
            cache = new VarBit[cacheSize];
        }
        for (int j = 0; j < cacheSize; j++) {
            if (cache[j] == null) {
                cache[j] = new VarBit();
            }
            cache[j].readValues(stream);
            if (cache[j].aBoolean651) {
                Varp.cache[cache[j].anInt648].aBoolean713 = true;
            }
        }

        if (stream.currentOffset != stream.buffer.length) {
            System.out.println("varbit load mismatch");
        }
    }

    private void readValues(Stream stream) {
        do {
            int j = stream.readUByte();
            if (j == 0) {
                return;
            }
            if (j == 1) {
                anInt648 = stream.readUShort();
                anInt649 = stream.readUByte();
                anInt650 = stream.readUByte();
            } else if (j == 10) {
                stream.readString();
            } else if (j == 2) {
                aBoolean651 = true;
            } else if (j == 3) {
                stream.readUInt();
            } else if (j == 4) {
                stream.readUInt();
            } else {
                System.out.println("Error unrecognised config code: " + j);
            }
        } while (true);
    }

    private VarBit() {
        aBoolean651 = false;
    }

    public static VarBit cache[];
    public int anInt648;
    public int anInt649;
    public int anInt650;
    private boolean aBoolean651;
}
