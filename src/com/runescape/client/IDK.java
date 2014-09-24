package com.runescape.client;

import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;

public final class IDK {

    public static int length;
    public static IDK cache[];

    public static void unpackConfig(StreamLoader streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("idk.dat"));
        length = stream.readUShort();

        if (cache == null) {
            cache = new IDK[length];
        }

        for (int i = 0; i < length; i++) {
            if (cache[i] == null) {
                cache[i] = new IDK();
            }
            cache[i].readValues(stream);
        }
    }
    
    public int anInt657;
    private int[] anIntArray658;
    private final int[] anIntArray659;
    private final int[] anIntArray660;
    private final int[] anIntArray661 = {
        -1, -1, -1, -1, -1
    };
    public boolean aBoolean662;

    private IDK() {
        anInt657 = -1;
        anIntArray659 = new int[6];
        anIntArray660 = new int[6];
        aBoolean662 = false;
    }

    private void readValues(Stream stream) {
        do {
            int opcode = stream.readUByte();

            if (opcode == 0) {
                return;
            }

            if (opcode == 1) {
                anInt657 = stream.readUByte();
            } else if (opcode == 2) {
                int j = stream.readUByte();
                anIntArray658 = new int[j];

                for (int k = 0; k < j; k++) {
                    anIntArray658[k] = stream.readUShort();
                }
            } else if (opcode == 3) {
                aBoolean662 = true;
            } else if (opcode >= 40 && opcode < 50) {
                anIntArray659[opcode - 40] = stream.readUShort();
            } else if (opcode >= 50 && opcode < 60) {
                anIntArray660[opcode - 50] = stream.readUShort();
            } else if (opcode >= 60 && opcode < 70) {
                anIntArray661[opcode - 60] = stream.readUShort();
            } else {
                System.out.println("Error unrecognised config code: " + opcode);
            }
        } while (true);
    }

    public boolean method537() {
        if (anIntArray658 == null) {
            return true;
        }
        boolean flag = true;

        for (int j = 0; j < anIntArray658.length; j++) {
            if (!Model.method463(anIntArray658[j])) {
                flag = false;
            }
        }
        return flag;
    }

    public Model method538() {
        if (anIntArray658 == null) {
            return null;
        }
        Model models[] = new Model[anIntArray658.length];

        for (int i = 0; i < anIntArray658.length; i++) {
            models[i] = Model.method462(anIntArray658[i]);
        }
        Model model;

        if (models.length == 1) {
            model = models[0];
        } else {
            model = new Model(models.length, models);
        }

        for (int j = 0; j < 6; j++) {
            if (anIntArray659[j] == 0) {
                break;
            }
            model.method476(anIntArray659[j], anIntArray660[j]);
        }
        return model;
    }

    public boolean method539() {
        boolean flag1 = true;

        for (int i = 0; i < 5; i++) {
            if (anIntArray661[i] != -1 && !Model.method463(anIntArray661[i])) {
                flag1 = false;
            }
        }
        return flag1;
    }

    public Model method540() {
        Model models[] = new Model[5];
        int j = 0;

        for (int k = 0; k < 5; k++) {
            if (anIntArray661[k] != -1) {
                models[j++] = Model.method462(anIntArray661[k]);
            }
        }

        Model model = new Model(j, models);

        for (int l = 0; l < 6; l++) {
            if (anIntArray659[l] == 0) {
                break;
            }
            model.method476(anIntArray659[l], anIntArray660[l]);
        }

        return model;
    }
}
