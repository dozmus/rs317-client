package com.runescape.client.world;

import com.runescape.client.MRUNodes;
import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;

public final class StaticAnimation {

    public static StaticAnimation[] cache;
    public static MRUNodes aMRUNodes_415 = new MRUNodes(30);

    public static void unpackConfig(StreamLoader streamLoader) {
        Stream stream = new Stream(streamLoader.getDataForName("spotanim.dat"));
        int length = stream.readUShort();

        if (cache == null) {
            cache = new StaticAnimation[length];
        }

        for (int j = 0; j < length; j++) {
            if (cache[j] == null) {
                cache[j] = new StaticAnimation();
            }
            cache[j].anInt404 = j;
            cache[j].readValues(stream);
        }
    }

    private int anInt404;
    private int anInt405;
    private int animId;
    public Animation anim;
    private final int[] anIntArray408;
    private final int[] anIntArray409;
    public int anInt410;
    public int anInt411;
    public int anInt412;
    public int anInt413;
    public int anInt414;

    private StaticAnimation() {
        int anInt400 = 9;
        animId = -1;
        anIntArray408 = new int[6];
        anIntArray409 = new int[6];
        anInt410 = 128;
        anInt411 = 128;
    }

    private void readValues(Stream stream) {
        do {
            int opcode = stream.readUByte();

            if (opcode == 0) {
                return;
            }

            if (opcode == 1) {
                anInt405 = stream.readUShort();
            } else if (opcode == 2) {
                animId = stream.readUShort();

                if (Animation.anims != null) {
                    anim = Animation.anims[animId];
                }
            } else if (opcode == 4) {
                anInt410 = stream.readUShort();
            } else if (opcode == 5) {
                anInt411 = stream.readUShort();
            } else if (opcode == 6) {
                anInt412 = stream.readUShort();
            } else if (opcode == 7) {
                anInt413 = stream.readUByte();
            } else if (opcode == 8) {
                anInt414 = stream.readUByte();
            } else if (opcode >= 40 && opcode < 50) {
                anIntArray408[opcode - 40] = stream.readUShort();
            } else if (opcode >= 50 && opcode < 60) {
                anIntArray409[opcode - 50] = stream.readUShort();
            } else {
                System.out.println("Error unrecognised spotanim config code: " + opcode);
            }
        } while (true);
    }

    public Model getModel() {
        Model model = (Model) aMRUNodes_415.insertFromCache(anInt404);

        if (model != null) {
            return model;
        }
        model = Model.method462(anInt405);

        if (model == null) {
            return null;
        }

        for (int i = 0; i < 6; i++) {
            if (anIntArray408[0] != 0) {
                model.method476(anIntArray408[i], anIntArray409[i]);
            }
        }
        aMRUNodes_415.removeFromCache(model, anInt404);
        return model;
    }
}
