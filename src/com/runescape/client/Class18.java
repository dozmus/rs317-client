package com.runescape.client;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.

import com.runescape.client.io.Stream;

// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
public final class Class18 {

    public Class18(Stream stream) {
        int anInt341 = stream.readUByte();
        anIntArray342 = new int[anInt341];
        anIntArrayArray343 = new int[anInt341][];
        for (int j = 0; j < anInt341; j++) {
            anIntArray342[j] = stream.readUByte();
        }

        for (int k = 0; k < anInt341; k++) {
            int l = stream.readUByte();
            anIntArrayArray343[k] = new int[l];
            for (int i1 = 0; i1 < l; i1++) {
                anIntArrayArray343[k][i1] = stream.readUByte();
            }

        }

    }

    public final int[] anIntArray342;
    public final int[][] anIntArrayArray343;
}
