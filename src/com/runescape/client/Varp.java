package com.runescape.client;

import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;

public final class Varp {

    public static Varp cache[];
    private static int anInt702;
    private static int[] anIntArray703;

    public static void unpackConfig(StreamLoader streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("varp.dat"));
        anInt702 = 0;
        int cacheSize = stream.readUShort();

        if (cache == null) {
            cache = new Varp[cacheSize];
        }

        if (anIntArray703 == null) {
            anIntArray703 = new int[cacheSize];
        }

        for (int j = 0; j < cacheSize; j++) {
            if (cache[j] == null) {
                cache[j] = new Varp();
            }
            cache[j].readValues(stream, j);
        }

        if (stream.currentOffset != stream.buffer.length) {
            System.out.println("varptype load mismatch");
        }
    }
    public int anInt709;
    public boolean aBoolean713;

    private Varp() {
        aBoolean713 = false;
    }

    private void readValues(Stream stream, int i) {
        do {
            int opcode = stream.readUByte();

            if (opcode == 0) {
                return;
            }
            int dummy;

            if (opcode == 1) {
                stream.readUByte();
            } else if (opcode == 2) {
                stream.readUByte();
            } else if (opcode == 3) {
                anIntArray703[anInt702++] = i;
            } else if (opcode == 4) {
                dummy = 2;
            } else if (opcode == 5) {
                anInt709 = stream.readUShort();
            } else if (opcode == 6) {
                dummy = 2;
            } else if (opcode == 7) {
                stream.readUInt();
            } else if (opcode == 8) {
                aBoolean713 = true;
            } else if (opcode == 10) {
                stream.readString();
            } else if (opcode == 11) {
                aBoolean713 = true;
            } else if (opcode == 12) {
                stream.readUInt();
            } else if (opcode == 13) {
                dummy = 2;
            } else {
                System.out.println("Error unrecognised config code: " + opcode);
            }
        } while (true);
    }
}
