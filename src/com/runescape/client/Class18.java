package com.runescape.client;

import com.runescape.client.io.Stream;

public final class Class18 {

    public final int[] anIntArray342;
    public final int[][] anIntArrayArray343;

    public Class18(Stream stream) {
        int length = stream.readUByte();
        anIntArray342 = new int[length];
        anIntArrayArray343 = new int[length][];
        
        for (int i = 0; i < length; i++) {
            anIntArray342[i] = stream.readUByte();
        }

        for (int k = 0; k < length; k++) {
            int len = stream.readUByte();
            anIntArrayArray343[k] = new int[len];
            
            for (int i = 0; i < len; i++) {
                anIntArrayArray343[k][i] = stream.readUByte();
            }
        }
    }
}
