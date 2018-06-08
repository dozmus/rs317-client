package com.runescape.client.world;

import com.runescape.client.util.node.Node;

public final class Tile extends Node {

    public int anInt1307;
    public final int x;
    public final int y;
    public final int z;
    public Class43 aClass43_1311;
    public Class40 aClass40_1312;
    public Wall wall;
    public WallDecoration wallDecoration;
    public TileDecoration decoration;
    public Object4 obj4;
    public int anInt1317;
    public final Object5[] obj5Array;
    public final int[] anIntArray1319;
    public int anInt1320;
    public int anInt1321;
    public boolean aBoolean1322;
    public boolean aBoolean1323;
    public boolean aBoolean1324;
    public int anInt1325;
    public int anInt1326;
    public int anInt1327;
    public int anInt1328;
    public Tile tile;

    public Tile(int z, int x, int y) {
        obj5Array = new Object5[5];
        anIntArray1319 = new int[5];
        this.z = anInt1307 = z;
        this.x = x;
        this.y = y;
    }
}
