package com.runescape.client.sound;

import com.runescape.client.io.Stream;

final class Class29 {

    private int length;
    private int[] anIntArray536;
    private int[] anIntArray537;
    int anInt538;
    int anInt539;
    int anInt540;
    private int anInt541;
    private int anInt542;
    private int anInt543;
    private int anInt544;
    private int anInt545;

    public Class29() {
    }

    public void method325(Stream stream) {
        anInt540 = stream.readUByte();
        anInt538 = stream.readUInt();
        anInt539 = stream.readUInt();
        method326(stream);
    }

    public void method326(Stream stream) {
        length = stream.readUByte();
        anIntArray536 = new int[length];
        anIntArray537 = new int[length];
        
        for (int i = 0; i < length; i++) {
            anIntArray536[i] = stream.readUShort();
            anIntArray537[i] = stream.readUShort();
        }
    }

    void resetValues() {
        anInt541 = 0;
        anInt542 = 0;
        anInt543 = 0;
        anInt544 = 0;
        anInt545 = 0;
    }

    int method328(int i) {
        if (anInt545 >= anInt541) {
            anInt544 = anIntArray537[anInt542++] << 15;
            
            if (anInt542 >= length) {
                anInt542 = length - 1;
            }
            anInt541 = (int) (((double) anIntArray536[anInt542] / 65536D) * (double) i);
            
            if (anInt541 > anInt545) {
                anInt543 = ((anIntArray537[anInt542] << 15) - anInt544) / (anInt541 - anInt545);
            }
        }
        anInt544 += anInt543;
        anInt545++;
        return anInt544 - anInt543 >> 15;
    }
}