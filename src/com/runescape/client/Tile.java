package com.runescape.client;

import com.runescape.client.util.node.Node;

public final class Tile extends Node {

    int anInt1307;
    final int x;
    final int y;
    final int z;
    public Class43 aClass43_1311;
    public Class40 aClass40_1312;
    public Wall wall;
    public WallDecoration wallDecoration;
    public TileDecoration decoration;
    public Object4 obj4;
    int anInt1317;
    public final Object5[] obj5Array;
    final int[] anIntArray1319;
    int anInt1320;
    int anInt1321;
    boolean aBoolean1322;
    boolean aBoolean1323;
    boolean aBoolean1324;
    int anInt1325;
    int anInt1326;
    int anInt1327;
    int anInt1328;
    public Tile tile;

    public Tile(int z, int x, int y) {
        obj5Array = new Object5[5];
        anIntArray1319 = new int[5];
        this.z = anInt1307 = z;
        this.x = x;
        this.y = y;
    }
}
