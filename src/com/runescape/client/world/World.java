package com.runescape.client.world;

import com.runescape.client.render.DrawingArea;
import com.runescape.client.util.node.NodeList;

public final class World {

    public static boolean lowMem = true;
    private static int anInt446;
    private static int anInt447;
    private static int anInt448;
    private static int anInt449;
    private static int anInt450;
    private static int anInt451;
    private static int anInt452;
    private static int anInt453;
    private static int anInt454;
    private static int anInt455;
    private static int anInt456;
    private static int anInt457;
    private static int anInt458;
    private static int anInt459;
    private static int anInt460;
    private static int anInt461;
    private static Object5[] aClass28Array462 = new Object5[100];
    private static final int[] anIntArray463 = {
        53, -53, -53, 53
    };
    private static final int[] anIntArray464 = {
        -53, -53, 53, 53
    };
    private static final int[] anIntArray465 = {
        -45, 45, 45, -45
    };
    private static final int[] anIntArray466 = {
        45, 45, -45, -45
    };
    private static boolean aBoolean467;
    private static int anInt468;
    private static int anInt469;
    public static int walkTargetX = -1;
    public static int walkTargetY = -1;
    private static final int anInt472;
    private static int[] anIntArray473;
    private static Class47[][] aClass47ArrayArray474;
    private static int anInt475;
    private static final Class47[] aClass47Array476 = new Class47[500];
    private static NodeList groundList = new NodeList();
    private static final int[] anIntArray478 = {
        19, 55, 38, 155, 255, 110, 137, 205, 76
    };
    private static final int[] anIntArray479 = {
        160, 192, 80, 96, 0, 144, 80, 48, 160
    };
    private static final int[] anIntArray480 = {
        76, 8, 137, 4, 0, 1, 38, 2, 19
    };
    private static final int[] anIntArray481 = {
        0, 0, 2, 0, 0, 2, 1, 1, 0
    };
    private static final int[] anIntArray482 = {
        2, 0, 0, 2, 0, 0, 0, 4, 4
    };
    private static final int[] anIntArray483 = {
        0, 4, 4, 8, 0, 0, 8, 0, 0
    };
    private static final int[] anIntArray484 = {
        1, 1, 0, 0, 0, 8, 0, 0, 8
    };
    private static final int[] anIntArray485 = {
        41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41,
        41, 41, 41, 41, 41, 43086, 41, 41, 41, 41,
        41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41,
        41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41,
        41, 41, 41, 41, 41, 41, 3131, 41, 41, 41
    };
    private static boolean[][][][] aBooleanArrayArrayArrayArray491 = new boolean[8][32][51][51];
    private static boolean[][] aBooleanArrayArray492;
    private static int anInt493;
    private static int anInt494;
    private static int anInt495;
    private static int anInt496;
    private static int anInt497;
    private static int anInt498;

    static {
        anInt472 = 4;
        anIntArray473 = new int[anInt472];
        aClass47ArrayArray474 = new Class47[anInt472][500];
    }

    public static void nullLoader() {
        aClass28Array462 = null;
        anIntArray473 = null;
        aClass47ArrayArray474 = null;
        groundList = null;
        aBooleanArrayArrayArrayArray491 = null;
        aBooleanArrayArray492 = null;
    }

    public static void method277(int i, int j, int k, int l, int i1, int j1, int l1, int i2) {
        Class47 class47 = new Class47();
        class47.anInt787 = j / 128;
        class47.anInt788 = l / 128;
        class47.anInt789 = l1 / 128;
        class47.anInt790 = i1 / 128;
        class47.anInt791 = i2;
        class47.anInt792 = j;
        class47.anInt793 = l;
        class47.anInt794 = l1;
        class47.anInt795 = i1;
        class47.anInt796 = j1;
        class47.anInt797 = k;
        aClass47ArrayArray474[i][anIntArray473[i]++] = class47;
    }

    public static void method310(int i, int j, int k, int l, int ai[]) {
        anInt495 = 0;
        anInt496 = 0;
        anInt497 = k;
        anInt498 = l;
        anInt493 = k / 2;
        anInt494 = l / 2;
        boolean aflag[][][][] = new boolean[9][32][53][53];

        for (int i1 = 128; i1 <= 384; i1 += 32) {
            for (int j1 = 0; j1 < 2048; j1 += 64) {
                anInt458 = Model.modelIntArray1[i1];
                anInt459 = Model.modelIntArray2[i1];
                anInt460 = Model.modelIntArray1[j1];
                anInt461 = Model.modelIntArray2[j1];
                int l1 = (i1 - 128) / 32;
                int j2 = j1 / 64;

                for (int l2 = -26; l2 <= 26; l2++) {
                    for (int j3 = -26; j3 <= 26; j3++) {
                        int k3 = l2 * 128;
                        int i4 = j3 * 128;
                        boolean flag2 = false;

                        for (int k4 = -i; k4 <= j; k4 += 128) {
                            if (!method311(ai[l1] + k4, i4, k3)) {
                                continue;
                            }
                            flag2 = true;
                            break;
                        }
                        aflag[l1][j2][l2 + 25 + 1][j3 + 25 + 1] = flag2;
                    }
                }
            }
        }

        for (int k1 = 0; k1 < 8; k1++) {
            for (int i2 = 0; i2 < 32; i2++) {
                for (int k2 = -25; k2 < 25; k2++) {
                    for (int i3 = -25; i3 < 25; i3++) {
                        boolean flag1 = false;
                        label0:
                        for (int l3 = -1; l3 <= 1; l3++) {
                            for (int j4 = -1; j4 <= 1; j4++) {
                                if (aflag[k1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
                                    flag1 = true;
                                } else if (aflag[k1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
                                    flag1 = true;
                                } else if (aflag[k1 + 1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
                                    flag1 = true;
                                } else {
                                    if (!aflag[k1 + 1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
                                        continue;
                                    }
                                    flag1 = true;
                                }
                                break label0;
                            }
                        }
                        aBooleanArrayArrayArrayArray491[k1][i2][k2 + 25][i3 + 25] = flag1;
                    }
                }
            }
        }
    }

    private static boolean method311(int i, int j, int k) {
        int l = j * anInt460 + k * anInt461 >> 16;
        int i1 = j * anInt461 - k * anInt460 >> 16;
        int j1 = i * anInt458 + i1 * anInt459 >> 16;
        int k1 = i * anInt459 - i1 * anInt458 >> 16;

        if (j1 < 50 || j1 > 3500) {
            return false;
        }
        int l1 = anInt493 + (l << 9) / j1;
        int i2 = anInt494 + (k1 << 9) / j1;
        return l1 >= anInt495 && l1 <= anInt497 && i2 >= anInt496 && i2 <= anInt498;
    }

    private boolean aBoolean434;
    private final int regionsZ;
    private final int regionsX;
    private final int regionsY;
    private final int[][][] anIntArrayArrayArray440;
    private final Tile[][][] tiles;
    private int anInt442;
    private int obj5CacheCurrPos;
    private final Object5[] obj5Cache;
    private final int[][][] anIntArrayArrayArray445;
    private final int[] anIntArray486;
    private final int[] anIntArray487;
    private int anInt488;
    private final int[][] anIntArrayArray489 = {
        new int[16], {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1
        }, {
            1, 0, 0, 0, 1, 1, 0, 0, 1, 1,
            1, 0, 1, 1, 1, 1
        }, {
            1, 1, 0, 0, 1, 1, 0, 0, 1, 0,
            0, 0, 1, 0, 0, 0
        }, {
            0, 0, 1, 1, 0, 0, 1, 1, 0, 0,
            0, 1, 0, 0, 0, 1
        }, {
            0, 1, 1, 1, 0, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1
        }, {
            1, 1, 1, 0, 1, 1, 1, 0, 1, 1,
            1, 1, 1, 1, 1, 1
        }, {
            1, 1, 0, 0, 1, 1, 0, 0, 1, 1,
            0, 0, 1, 1, 0, 0
        }, {
            0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
            0, 0, 1, 1, 0, 0
        }, {
            1, 1, 1, 1, 1, 1, 1, 1, 0, 1,
            1, 1, 0, 0, 1, 1
        },
        {
            1, 1, 1, 1, 1, 1, 0, 0, 1, 0,
            0, 0, 1, 0, 0, 0
        }, {
            0, 0, 0, 0, 0, 0, 1, 1, 0, 1,
            1, 1, 0, 1, 1, 1
        }, {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 1, 1
        }
    };
    private final int[][] anIntArrayArray490 = {
        {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15
        }, {
            12, 8, 4, 0, 13, 9, 5, 1, 14, 10,
            6, 2, 15, 11, 7, 3
        }, {
            15, 14, 13, 12, 11, 10, 9, 8, 7, 6,
            5, 4, 3, 2, 1, 0
        }, {
            3, 7, 11, 15, 2, 6, 10, 14, 1, 5,
            9, 13, 0, 4, 8, 12
        }
    };

    public World(int ai[][][]) {
        int regionsY = 104;//was parameter
        int regionsX = 104;//was parameter
        int regionsZ = 4;//was parameter
        aBoolean434 = true;
        obj5Cache = new Object5[5000];
        anIntArray486 = new int[10000];
        anIntArray487 = new int[10000];
        this.regionsZ = regionsZ;
        this.regionsX = regionsX;
        this.regionsY = regionsY;
        tiles = new Tile[regionsZ][regionsX][regionsY];
        anIntArrayArrayArray445 = new int[regionsZ][regionsX + 1][regionsY + 1];
        anIntArrayArrayArray440 = ai;
        initToNull();
    }

    public void initToNull() {
        for (int z = 0; z < regionsZ; z++) {
            for (int x = 0; x < regionsX; x++) {
                for (int y = 0; y < regionsY; y++) {
                    tiles[z][x][y] = null;
                }
            }
        }

        for (int l = 0; l < anInt472; l++) {
            for (int j1 = 0; j1 < anIntArray473[l]; j1++) {
                aClass47ArrayArray474[l][j1] = null;
            }
            anIntArray473[l] = 0;
        }

        for (int k1 = 0; k1 < obj5CacheCurrPos; k1++) {
            obj5Cache[k1] = null;
        }
        obj5CacheCurrPos = 0;

        for (int l1 = 0; l1 < aClass28Array462.length; l1++) {
            aClass28Array462[l1] = null;
        }
    }

    public void method275(int z) {
        anInt442 = z;

        for (int x = 0; x < regionsX; x++) {
            for (int y = 0; y < regionsY; y++) {
                if (tiles[z][x][y] == null) {
                    tiles[z][x][y] = new Tile(z, x, y);
                }
            }
        }
    }

    public void method276(int y, int x) {
        Tile tile = tiles[0][x][y];

        for (int z = 0; z < 3; z++) {
            Tile tmp = tiles[z][x][y] = tiles[z + 1][x][y];

            if (tmp != null) {
                tmp.anInt1307--;

                for (int j1 = 0; j1 < tmp.anInt1317; j1++) {
                    Object5 obj5 = tmp.obj5Array[j1];

                    if ((obj5.uid >> 29 & 3) == 2 && obj5.x == x && obj5.y == y) {
                        obj5.z--;
                    }
                }
            }
        }

        if (tiles[0][x][y] == null) {
            tiles[0][x][y] = new Tile(0, x, y);
        }
        tiles[0][x][y].tile = tile;
        tiles[3][x][y] = null;
    }

    public void method278(int z, int x, int y, int l) {
        Tile tile = tiles[z][x][y];

        if (tile != null) {
            tiles[z][x][y].anInt1321 = l;
        }
    }

    public void method279(int z, int x, int y, int l, int i1, int j1, int k1,
            int l1, int i2, int j2, int k2, int l2, int i3, int j3, int k3,
            int l3, int i4, int j4, int k4, int l4) {
        if (l == 0) {
            Class43 class43 = new Class43(k2, l2, i3, j3, -1, k4, false);

            for (int z1 = z; z1 >= 0; z1--) {
                if (tiles[z1][x][y] == null) {
                    tiles[z1][x][y] = new Tile(z1, x, y);
                }
            }
            tiles[z][x][y].aClass43_1311 = class43;
            return;
        }

        if (l == 1) {
            Class43 class43_1 = new Class43(k3, l3, i4, j4, j1, l4, k1 == l1 && k1 == i2 && k1 == j2);

            for (int z1 = z; z1 >= 0; z1--) {
                if (tiles[z1][x][y] == null) {
                    tiles[z1][x][y] = new Tile(z1, x, y);
                }
            }
            tiles[z][x][y].aClass43_1311 = class43_1;
            return;
        }
        Class40 class40 = new Class40(y, k3, j3, i2, j1, i4, i1, k2, k4, i3, j2, l1, k1, l, j4, l3, l2, x, l4);

        for (int z1 = z; z1 >= 0; z1--) {
            if (tiles[z1][x][y] == null) {
                tiles[z1][x][y] = new Tile(z1, x, y);
            }
        }
        tiles[z][x][y].aClass40_1312 = class40;
    }

    public void createGroundDecoration(int z, int elevation, int y, Animable anim, byte byte0, int uid, int x) {
        if (anim == null) {
            return;
        }
        TileDecoration decoration = new TileDecoration();
        decoration.model = anim;
        decoration.x = x * 128 + 64;
        decoration.y = y * 128 + 64;
        decoration.elevation = elevation;
        decoration.uid = uid;
        decoration.aByte816 = byte0;

        if (tiles[z][x][y] == null) {
            tiles[z][x][y] = new Tile(z, x, y);
        }
        tiles[z][x][y].decoration = decoration;
    }

    public void method281(int x, int uid, Animable anim2, int k, Animable anim3, Animable anim1, int z, int y) {
        Object4 obj4 = new Object4();
        obj4.anim1 = anim1;
        obj4.x = x * 128 + 64;
        obj4.y = y * 128 + 64;
        obj4.anInt45 = k;
        obj4.uid = uid;
        obj4.anim2 = anim2;
        obj4.anim3 = anim3;
        int j1 = 0;
        Tile tile = tiles[z][x][y];

        if (tile != null) {
            for (int k1 = 0; k1 < tile.anInt1317; k1++) {
                if (tile.obj5Array[k1].anim instanceof Model) {
                    int l1 = ((Model) tile.obj5Array[k1].anim).anInt1654;

                    if (l1 > j1) {
                        j1 = l1;
                    }
                }
            }
        }
        obj4.anInt52 = j1;

        if (tiles[z][x][y] == null) {
            tiles[z][x][y] = new Tile(z, x, y);
        }
        tiles[z][x][y].obj4 = obj4;
    }

    public void createWall(int orientation, Animable anim1, int uid, int y,
            byte byte0, int x, Animable anim2, int i1, int orientation1, int z) {
        if (anim1 == null && anim2 == null) {
            return;
        }
        Wall wall = new Wall();
        wall.uid = uid;
        wall.aByte281 = byte0;
        wall.anInt274 = x * 128 + 64;
        wall.anInt275 = y * 128 + 64;
        wall.anInt273 = i1;
        wall.anim1 = anim1;
        wall.anim2 = anim2;
        wall.orientation = orientation;
        wall.orientation1 = orientation1;

        for (int z1 = z; z1 >= 0; z1--) {
            if (tiles[z1][x][y] == null) {
                tiles[z1][x][y] = new Tile(z1, x, y);
            }
        }
        tiles[z][x][y].wall = wall;
    }

    public void createWallDecoration(int uid, int y, int k, int z, int offsetX, int k1,
            Animable anim, int x, byte byte0, int offsetY, int j2) {
        if (anim == null) {
            return;
        }
        WallDecoration wallDecoration = new WallDecoration();
        wallDecoration.uid = uid;
        wallDecoration.aByte506 = byte0;
        wallDecoration.x = x * 128 + 64 + offsetX;
        wallDecoration.y = y * 128 + 64 + offsetY;
        wallDecoration.elevation = k1;
        wallDecoration.anim = anim;
        wallDecoration.anInt502 = j2;
        wallDecoration.anInt503 = k;

        for (int z1 = z; z1 >= 0; z1--) {
            if (tiles[z1][x][y] == null) {
                tiles[z1][x][y] = new Tile(z1, x, y);
            }
        }
        tiles[z][x][y].wallDecoration = wallDecoration;
    }

    public boolean method284(int uid, byte byte0, int j, int lengthY, Animable anim,
            int lengthX, int z, int j1, int y, int x) {
        if (anim == null) {
            return true;
        } else {
            int i2 = x * 128 + 64 * lengthX;
            int j2 = y * 128 + 64 * lengthY;
            return method287(z, x, y, lengthX, lengthY, i2, j2, j, anim, j1, false, uid, byte0);
        }
    }

    public boolean method285(int z, int j, int k, int uid, int i1, int j1, int k1,
            Animable anim, boolean flag) {
        if (anim == null) {
            return true;
        }
        int x = k1 - j1;
        int y = i1 - j1;
        int maxX = k1 + j1;
        int maxY = i1 + j1;

        if (flag) {
            if (j > 640 && j < 1408) {
                maxY += 128;
            }

            if (j > 1152 && j < 1920) {
                maxX += 128;
            }

            if (j > 1664 || j < 384) {
                y -= 128;
            }

            if (j > 128 && j < 896) {
                x -= 128;
            }
        }
        x /= 128;
        y /= 128;
        maxX /= 128;
        maxY /= 128;
        return method287(z, x, y, (maxX - x) + 1, (maxY - y) + 1, k1, i1, k, anim, j, true, uid, (byte) 0);
    }

    public boolean method286(int z, int k, Animable anim, int l, int maxY, int j1,
            int k1, int x, int maxX, int uid, int y) {
        return anim == null || method287(z, x, y, (maxX - x) + 1, (maxY - y) + 1, j1, k, k1, anim, l, true, uid, (byte) 0);
    }

    private boolean method287(int z, int x, int y, int lengthX, int lengthY, int j1, int k1,
            int l1, Animable anim, int i2, boolean flag, int uid, byte byte0) {
        for (int x1 = x; x1 < x + lengthX; x1++) {
            for (int y1 = y; y1 < y + lengthY; y1++) {
                if (x1 < 0 || y1 < 0 || x1 >= regionsX || y1 >= regionsY) {
                    return false;
                }
                Tile tile = tiles[z][x1][y1];

                if (tile != null && tile.anInt1317 >= 5) {
                    return false;
                }
            }
        }
        Object5 obj5 = new Object5();
        obj5.uid = uid;
        obj5.aByte530 = byte0;
        obj5.z = z;
        obj5.anInt519 = j1;
        obj5.anInt520 = k1;
        obj5.anInt518 = l1;
        obj5.anim = anim;
        obj5.anInt522 = i2;
        obj5.x = x;
        obj5.y = y;
        obj5.anInt524 = (x + lengthX) - 1;
        obj5.anInt526 = (y + lengthY) - 1;

        for (int x1 = x; x1 < x + lengthX; x1++) {
            for (int y1 = y; y1 < y + lengthY; y1++) {
                int k3 = 0;

                if (x1 > x) {
                    k3++;
                }

                if (x1 < (x + lengthX) - 1) {
                    k3 += 4;
                }

                if (y1 > y) {
                    k3 += 8;
                }

                if (y1 < (y + lengthY) - 1) {
                    k3 += 2;
                }

                for (int z1 = z; z1 >= 0; z1--) {
                    if (tiles[z1][x1][y1] == null) {
                        tiles[z1][x1][y1] = new Tile(z1, x1, y1);
                    }
                }
                Tile tile = tiles[z][x1][y1];
                tile.obj5Array[tile.anInt1317] = obj5;
                tile.anIntArray1319[tile.anInt1317] = k3;
                tile.anInt1320 |= k3;
                tile.anInt1317++;
            }
        }

        if (flag) {
            obj5Cache[obj5CacheCurrPos++] = obj5;
        }
        return true;
    }

    public void clearObj5Cache() {
        for (int i = 0; i < obj5CacheCurrPos; i++) {
            Object5 object5 = obj5Cache[i];
            method289(object5);
            obj5Cache[i] = null;
        }
        obj5CacheCurrPos = 0;
    }

    private void method289(Object5 obj5) {
        for (int x = obj5.x; x <= obj5.anInt524; x++) {
            for (int y = obj5.y; y <= obj5.anInt526; y++) {
                Tile tile = tiles[obj5.z][x][y];

                if (tile != null) {
                    for (int l = 0; l < tile.anInt1317; l++) {
                        if (tile.obj5Array[l] != obj5) {
                            continue;
                        }
                        tile.anInt1317--;

                        for (int i1 = l; i1 < tile.anInt1317; i1++) {
                            tile.obj5Array[i1] = tile.obj5Array[i1 + 1];
                            tile.anIntArray1319[i1] = tile.anIntArray1319[i1 + 1];
                        }
                        tile.obj5Array[tile.anInt1317] = null;
                        break;
                    }
                    tile.anInt1320 = 0;

                    for (int j1 = 0; j1 < tile.anInt1317; j1++) {
                        tile.anInt1320 |= tile.anIntArray1319[j1];
                    }
                }
            }
        }
    }

    public void method290(int y, int multiplier, int x, int z) {
        Tile tile = tiles[z][x][y];

        if (tile == null) {
            return;
        }
        WallDecoration wallDecoration = tile.wallDecoration;

        if (wallDecoration != null) {
            int x1 = x * 128 + 64;
            int y1 = y * 128 + 64;
            wallDecoration.x = x1 + ((wallDecoration.x - x1) * multiplier) / 16;
            wallDecoration.y = y1 + ((wallDecoration.y - y1) * multiplier) / 16;
        }
    }

    public void deleteWall(int x, int z, int y, byte byte0) {
        Tile tile = tiles[z][x][y];

        if (byte0 != -119) {
            aBoolean434 = !aBoolean434;
        }

        if (tile != null) {
            tile.wall = null;
        }
    }

    public void deleteWallDecoration(int y, int z, int x) {
        Tile tile = tiles[z][x][y];

        if (tile != null) {
            tile.wallDecoration = null;
        }
    }

    public void method293(int z, int x, int y) {
        Tile tile = tiles[z][x][y];

        if (tile == null) {
            return;
        }

        for (int j1 = 0; j1 < tile.anInt1317; j1++) {
            Object5 obj5 = tile.obj5Array[j1];

            if ((obj5.uid >> 29 & 3) == 2 && obj5.x == x && obj5.y == y) {
                method289(obj5);
                return;
            }
        }
    }

    public void deleteTileDecoration(int z, int y, int x) {
        Tile tile = tiles[z][x][y];

        if (tile != null) {
            tile.decoration = null;
        }
    }

    public void deleteObject4(int z, int x, int y) {
        Tile tile = tiles[z][x][y];

        if (tile != null) {
            tile.obj4 = null;
        }
    }

    public Wall getWall(int z, int x, int y) {
        Tile tile = tiles[z][x][y];

        if (tile == null) {
            return null;
        } else {
            return tile.wall;
        }
    }

    public WallDecoration getWallDecoration(int x, int y, int z) {
        Tile tile = tiles[z][x][y];

        if (tile == null) {
            return null;
        } else {
            return tile.wallDecoration;
        }
    }

    public Object5 getObject5(int x, int y, int z) {
        Tile tile = tiles[z][x][y];

        if (tile == null) {
            return null;
        }

        for (int l = 0; l < tile.anInt1317; l++) {
            Object5 obj5 = tile.obj5Array[l];

            if ((obj5.uid >> 29 & 3) == 2 && obj5.x == x && obj5.y == y) {
                return obj5;
            }
        }
        return null;
    }

    public TileDecoration getTileDecoration(int y, int x, int z) {
        Tile tile = tiles[z][x][y];

        if (tile == null || tile.decoration == null) {
            return null;
        } else {
            return tile.decoration;
        }
    }

    public int getObject1Uid(int z, int x, int y) {
        Tile tile = tiles[z][x][y];

        if (tile == null || tile.wall == null) {
            return 0;
        } else {
            return tile.wall.uid;
        }
    }

    public int getObject2Uid(int z, int x, int y) {
        Tile tile = tiles[z][x][y];

        if (tile == null || tile.wallDecoration == null) {
            return 0;
        } else {
            return tile.wallDecoration.uid;
        }
    }

    public int getObject5Uid(int z, int x, int y) {
        Tile tile = tiles[z][x][y];

        if (tile == null) {
            return 0;
        }

        for (int l = 0; l < tile.anInt1317; l++) {
            Object5 obj5 = tile.obj5Array[l];

            if ((obj5.uid >> 29 & 3) == 2 && obj5.x == x && obj5.y == y) {
                return obj5.uid;
            }
        }
        return 0;
    }

    public int getObject3Uid(int z, int x, int y) {
        Tile tile = tiles[z][x][y];

        if (tile == null || tile.decoration == null) {
            return 0;
        } else {
            return tile.decoration.uid;
        }
    }

    public int method304(int z, int x, int y, int uid) {
        Tile tile = tiles[z][x][y];

        if (tile == null) {
            return -1;
        }

        if (tile.wall != null && tile.wall.uid == uid) {
            return tile.wall.aByte281 & 0xff;
        }

        if (tile.wallDecoration != null && tile.wallDecoration.uid == uid) {
            return tile.wallDecoration.aByte506 & 0xff;
        }

        if (tile.decoration != null && tile.decoration.uid == uid) {
            return tile.decoration.aByte816 & 0xff;
        }

        for (int i = 0; i < tile.anInt1317; i++) {
            if (tile.obj5Array[i].uid == uid) {
                return tile.obj5Array[i].aByte530 & 0xff;
            }
        }
        return -1;
    }

    public void method305(int i, int k, int i1) {
        int j = 64;//was parameter
        int l = 768;//was parameter
        int j1 = (int) Math.sqrt(k * k + i * i + i1 * i1);
        int k1 = l * j1 >> 8;

        for (int z = 0; z < regionsZ; z++) {
            for (int x = 0; x < regionsX; x++) {
                for (int y = 0; y < regionsY; y++) {
                    Tile tile = tiles[z][x][y];

                    if (tile != null) {
                        Wall wall = tile.wall;

                        if (wall != null && wall.anim1 != null && wall.anim1.aClass33Array1425 != null) {
                            method307(z, 1, 1, x, y, (Model) wall.anim1);

                            if (wall.anim2 != null && wall.anim2.aClass33Array1425 != null) {
                                method307(z, 1, 1, x, y, (Model) wall.anim2);
                                method308((Model) wall.anim1, (Model) wall.anim2, 0, 0, 0, false);
                                ((Model) wall.anim2).method480(j, k1, k, i, i1);
                            }
                            ((Model) wall.anim1).method480(j, k1, k, i, i1);
                        }

                        for (int k2 = 0; k2 < tile.anInt1317; k2++) {
                            Object5 obj5 = tile.obj5Array[k2];

                            if (obj5 != null && obj5.anim != null && obj5.anim.aClass33Array1425 != null) {
                                method307(z, (obj5.anInt524 - obj5.x) + 1, (obj5.anInt526 - obj5.y) + 1, x, y, (Model) obj5.anim);
                                ((Model) obj5.anim).method480(j, k1, k, i, i1);
                            }
                        }
                        TileDecoration decoration = tile.decoration;

                        if (decoration != null && decoration.model.aClass33Array1425 != null) {
                            method306(x, z, (Model) decoration.model, y);
                            ((Model) decoration.model).method480(j, k1, k, i, i1);
                        }
                    }
                }
            }
        }
    }

    private void method306(int i, int j, Model model, int k) {
        if (i < regionsX) {
            Tile class30_sub3 = tiles[j][i + 1][k];
            if (class30_sub3 != null && class30_sub3.decoration != null && class30_sub3.decoration.model.aClass33Array1425 != null) {
                method308(model, (Model) class30_sub3.decoration.model, 128, 0, 0, true);
            }
        }
        if (k < regionsX) {
            Tile class30_sub3_1 = tiles[j][i][k + 1];
            if (class30_sub3_1 != null && class30_sub3_1.decoration != null && class30_sub3_1.decoration.model.aClass33Array1425 != null) {
                method308(model, (Model) class30_sub3_1.decoration.model, 0, 0, 128, true);
            }
        }
        if (i < regionsX && k < regionsY) {
            Tile class30_sub3_2 = tiles[j][i + 1][k + 1];
            if (class30_sub3_2 != null && class30_sub3_2.decoration != null && class30_sub3_2.decoration.model.aClass33Array1425 != null) {
                method308(model, (Model) class30_sub3_2.decoration.model, 128, 0, 128, true);
            }
        }
        if (i < regionsX && k > 0) {
            Tile class30_sub3_3 = tiles[j][i + 1][k - 1];
            if (class30_sub3_3 != null && class30_sub3_3.decoration != null && class30_sub3_3.decoration.model.aClass33Array1425 != null) {
                method308(model, (Model) class30_sub3_3.decoration.model, 128, 0, -128, true);
            }
        }
    }

    private void method307(int z, int lengthX, int lengthY, int x, int y, Model model) {
        boolean flag = true;
        int minX = x;
        int maxX = x + lengthX;
        int minY = y - 1;
        int maxY = y + lengthY;

        for (int z1 = z; z1 <= z + 1; z1++) {
            if (z1 != regionsZ) {
                for (int x1 = minX; x1 <= maxX; x1++) {
                    if (x1 >= 0 && x1 < regionsX) {
                        for (int y1 = minY; y1 <= maxY; y1++) {
                            if (y1 >= 0 && y1 < regionsY && (!flag || x1 >= maxX || y1 >= maxY || y1 < y && x1 != x)) {
                                Tile tile = tiles[z1][x1][y1];

                                if (tile != null) {
                                    int i3 = (anIntArrayArrayArray440[z1][x1][y1] + anIntArrayArrayArray440[z1][x1 + 1][y1] + anIntArrayArrayArray440[z1][x1][y1 + 1] + anIntArrayArrayArray440[z1][x1 + 1][y1 + 1]) / 4 - (anIntArrayArrayArray440[z][x][y] + anIntArrayArrayArray440[z][x + 1][y] + anIntArrayArrayArray440[z][x][y + 1] + anIntArrayArrayArray440[z][x + 1][y + 1]) / 4;
                                    Wall wall = tile.wall;

                                    if (wall != null && wall.anim1 != null && wall.anim1.aClass33Array1425 != null) {
                                        method308(model, (Model) wall.anim1, (x1 - x) * 128 + (1 - lengthX) * 64, i3, (y1 - y) * 128 + (1 - lengthY) * 64, flag);
                                    }

                                    if (wall != null && wall.anim2 != null && wall.anim2.aClass33Array1425 != null) {
                                        method308(model, (Model) wall.anim2, (x1 - x) * 128 + (1 - lengthX) * 64, i3, (y1 - y) * 128 + (1 - lengthY) * 64, flag);
                                    }

                                    for (int j3 = 0; j3 < tile.anInt1317; j3++) {
                                        Object5 obj5 = tile.obj5Array[j3];
                                        if (obj5 != null && obj5.anim != null && obj5.anim.aClass33Array1425 != null) {
                                            int k3 = (obj5.anInt524 - obj5.x) + 1;
                                            int l3 = (obj5.anInt526 - obj5.y) + 1;
                                            method308(model, (Model) obj5.anim, (obj5.x - x) * 128 + (k3 - lengthX) * 64, i3, (obj5.y - y) * 128 + (l3 - lengthY) * 64, flag);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                minX--;
                flag = false;
            }
        }
    }

    private void method308(Model model, Model model_1, int i, int j, int k, boolean flag) {
        anInt488++;
        int l = 0;
        int ai[] = model_1.anIntArray1627;
        int i1 = model_1.anInt1626;

        for (int j1 = 0; j1 < model.anInt1626; j1++) {
            Class33 class33 = model.aClass33Array1425[j1];
            Class33 class33_1 = model.aClass33Array1660[j1];

            if (class33_1.anInt605 != 0) {
                int i2 = model.anIntArray1628[j1] - j;

                if (i2 <= model_1.anInt1651) {
                    int j2 = model.anIntArray1627[j1] - i;

                    if (j2 >= model_1.anInt1646 && j2 <= model_1.anInt1647) {
                        int k2 = model.anIntArray1629[j1] - k;

                        if (k2 >= model_1.anInt1649 && k2 <= model_1.anInt1648) {
                            for (int l2 = 0; l2 < i1; l2++) {
                                Class33 class33_2 = model_1.aClass33Array1425[l2];
                                Class33 class33_3 = model_1.aClass33Array1660[l2];

                                if (j2 == ai[l2] && k2 == model_1.anIntArray1629[l2] && i2 == model_1.anIntArray1628[l2] && class33_3.anInt605 != 0) {
                                    class33.anInt602 += class33_3.anInt602;
                                    class33.anInt603 += class33_3.anInt603;
                                    class33.anInt604 += class33_3.anInt604;
                                    class33.anInt605 += class33_3.anInt605;
                                    class33_2.anInt602 += class33_1.anInt602;
                                    class33_2.anInt603 += class33_1.anInt603;
                                    class33_2.anInt604 += class33_1.anInt604;
                                    class33_2.anInt605 += class33_1.anInt605;
                                    l++;
                                    anIntArray486[j1] = anInt488;
                                    anIntArray487[l2] = anInt488;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (l < 3 || !flag) {
            return;
        }

        for (int k1 = 0; k1 < model.anInt1630; k1++) {
            if (anIntArray486[model.anIntArray1631[k1]] == anInt488 && anIntArray486[model.anIntArray1632[k1]] == anInt488 && anIntArray486[model.anIntArray1633[k1]] == anInt488) {
                model.anIntArray1637[k1] = -1;
            }
        }

        for (int l1 = 0; l1 < model_1.anInt1630; l1++) {
            if (anIntArray487[model_1.anIntArray1631[l1]] == anInt488 && anIntArray487[model_1.anIntArray1632[l1]] == anInt488 && anIntArray487[model_1.anIntArray1633[l1]] == anInt488) {
                model_1.anIntArray1637[l1] = -1;
            }
        }
    }

    public void method309(int ai[], int i, int z, int x, int y) {
        int j = 512;//was parameter
        Tile tile = tiles[z][x][y];

        if (tile == null) {
            return;
        }
        Class43 class43 = tile.aClass43_1311;

        if (class43 != null) {
            int j1 = class43.anInt722;

            if (j1 == 0) {
                return;
            }

            for (int k1 = 0; k1 < 4; k1++) {
                ai[i] = j1;
                ai[i + 1] = j1;
                ai[i + 2] = j1;
                ai[i + 3] = j1;
                i += j;
            }
            return;
        }
        Class40 class40 = tile.aClass40_1312;

        if (class40 == null) {
            return;
        }
        int l1 = class40.anInt684;
        int i2 = class40.anInt685;
        int j2 = class40.anInt686;
        int k2 = class40.anInt687;
        int ai1[] = anIntArrayArray489[l1];
        int ai2[] = anIntArrayArray490[i2];
        int l2 = 0;

        if (j2 != 0) {
            for (int i3 = 0; i3 < 4; i3++) {
                ai[i] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                i += j;
            }
            return;
        }

        for (int j3 = 0; j3 < 4; j3++) {
            if (ai1[ai2[l2++]] != 0) {
                ai[i] = k2;
            }

            if (ai1[ai2[l2++]] != 0) {
                ai[i + 1] = k2;
            }

            if (ai1[ai2[l2++]] != 0) {
                ai[i + 2] = k2;
            }

            if (ai1[ai2[l2++]] != 0) {
                ai[i + 3] = k2;
            }
            i += j;
        }
    }

    public void method312(int i, int j) {
        aBoolean467 = true;
        anInt468 = j;
        anInt469 = i;
        walkTargetX = -1;
        walkTargetY = -1;
    }

    public void method313(int i, int j, int k, int l, int i1, int j1) {
        if (i < 0) {
            i = 0;
        } else if (i >= regionsX * 128) {
            i = regionsX * 128 - 1;
        }

        if (j < 0) {
            j = 0;
        } else if (j >= regionsY * 128) {
            j = regionsY * 128 - 1;
        }

        anInt448++;
        anInt458 = Model.modelIntArray1[j1];
        anInt459 = Model.modelIntArray2[j1];
        anInt460 = Model.modelIntArray1[k];
        anInt461 = Model.modelIntArray2[k];
        aBooleanArrayArray492 = aBooleanArrayArrayArrayArray491[(j1 - 128) / 32][k / 64];
        anInt455 = i;
        anInt456 = l;
        anInt457 = j;
        anInt453 = i / 128;
        anInt454 = j / 128;
        anInt447 = i1;
        anInt449 = anInt453 - 25;

        if (anInt449 < 0) {
            anInt449 = 0;
        }
        anInt451 = anInt454 - 25;

        if (anInt451 < 0) {
            anInt451 = 0;
        }
        anInt450 = anInt453 + 25;

        if (anInt450 > regionsX) {
            anInt450 = regionsX;
        }
        anInt452 = anInt454 + 25;

        if (anInt452 > regionsY) {
            anInt452 = regionsY;
        }
        method319();
        anInt446 = 0;

        for (int z = anInt442; z < regionsZ; z++) {
            Tile tiles[][] = this.tiles[z];

            for (int x = anInt449; x < anInt450; x++) {
                for (int y = anInt451; y < anInt452; y++) {
                    Tile tile = tiles[x][y];

                    if (tile != null) {
                        if (tile.anInt1321 > i1 || !aBooleanArrayArray492[(x - anInt453) + 25][(y - anInt454) + 25] && anIntArrayArrayArray440[z][x][y] - l < 2000) {
                            tile.aBoolean1322 = false;
                            tile.aBoolean1323 = false;
                            tile.anInt1325 = 0;
                        } else {
                            tile.aBoolean1322 = true;
                            tile.aBoolean1323 = true;
                            tile.aBoolean1324 = tile.anInt1317 > 0;
                            anInt446++;
                        }
                    }
                }
            }
        }

        for (int z = anInt442; z < regionsZ; z++) {
            Tile tiles[][] = this.tiles[z];

            for (int l2 = -25; l2 <= 0; l2++) {
                int i3 = anInt453 + l2;
                int x = anInt453 - l2;

                if (i3 >= anInt449 || x < anInt450) {
                    for (int i4 = -25; i4 <= 0; i4++) {
                        int y = anInt454 + i4;
                        int i5 = anInt454 - i4;

                        if (i3 >= anInt449) {
                            if (y >= anInt451) {
                                Tile tile = tiles[i3][y];

                                if (tile != null && tile.aBoolean1322) {
                                    method314(tile, true);
                                }
                            }

                            if (i5 < anInt452) {
                                Tile tile = tiles[i3][i5];

                                if (tile != null && tile.aBoolean1322) {
                                    method314(tile, true);
                                }
                            }
                        }

                        if (x < anInt450) {
                            if (y >= anInt451) {
                                Tile tile = tiles[x][y];

                                if (tile != null && tile.aBoolean1322) {
                                    method314(tile, true);
                                }
                            }

                            if (i5 < anInt452) {
                                Tile tile = tiles[x][i5];

                                if (tile != null && tile.aBoolean1322) {
                                    method314(tile, true);
                                }
                            }
                        }

                        if (anInt446 == 0) {
                            aBoolean467 = false;
                            return;
                        }
                    }
                }
            }
        }

        for (int z = anInt442; z < regionsZ; z++) {
            Tile tiles[][] = this.tiles[z];

            for (int j3 = -25; j3 <= 0; j3++) {
                int posX = anInt453 + j3;
                int negX = anInt453 - j3;

                if (posX >= anInt449 || negX < anInt450) {
                    for (int l4 = -25; l4 <= 0; l4++) {
                        int posY = anInt454 + l4;
                        int negY = anInt454 - l4;

                        if (posX >= anInt449) {
                            if (posY >= anInt451) {
                                Tile tile = tiles[posX][posY];

                                if (tile != null && tile.aBoolean1322) {
                                    method314(tile, false);
                                }
                            }

                            if (negY < anInt452) {
                                Tile tile = tiles[posX][negY];

                                if (tile != null && tile.aBoolean1322) {
                                    method314(tile, false);
                                }
                            }
                        }

                        if (negX < anInt450) {
                            if (posY >= anInt451) {
                                Tile tile = tiles[negX][posY];

                                if (tile != null && tile.aBoolean1322) {
                                    method314(tile, false);
                                }
                            }

                            if (negY < anInt452) {
                                Tile tile = tiles[negX][negY];

                                if (tile != null && tile.aBoolean1322) {
                                    method314(tile, false);
                                }
                            }
                        }

                        if (anInt446 == 0) {
                            aBoolean467 = false;
                            return;
                        }
                    }
                }
            }
        }
        aBoolean467 = false;
    }

    private void method314(Tile tile, boolean flag) {
        groundList.insertHead(tile);

        do {
            Tile tile1;

            do {
                tile1 = (Tile) groundList.popHead();

                if (tile1 == null) {
                    return;
                }
            } while (!tile1.aBoolean1323);

            int x = tile1.x;
            int y = tile1.y;
            int z1 = tile1.anInt1307;
            int z = tile1.z;
            Tile tiles[][] = this.tiles[z1];

            if (tile1.aBoolean1322) {
                if (flag) {
                    if (z1 > 0) {
                        Tile tile2 = this.tiles[z1 - 1][x][y];

                        if (tile2 != null && tile2.aBoolean1323) {
                            continue;
                        }
                    }

                    if (x <= anInt453 && x > anInt449) {
                        Tile tile2 = tiles[x - 1][y];

                        if (tile2 != null && tile2.aBoolean1323 && (tile2.aBoolean1322 || (tile1.anInt1320 & 1) == 0)) {
                            continue;
                        }
                    }

                    if (x >= anInt453 && x < anInt450 - 1) {
                        Tile tile3 = tiles[x + 1][y];

                        if (tile3 != null && tile3.aBoolean1323 && (tile3.aBoolean1322 || (tile1.anInt1320 & 4) == 0)) {
                            continue;
                        }
                    }

                    if (y <= anInt454 && y > anInt451) {
                        Tile tile4 = tiles[x][y - 1];

                        if (tile4 != null && tile4.aBoolean1323 && (tile4.aBoolean1322 || (tile1.anInt1320 & 8) == 0)) {
                            continue;
                        }
                    }

                    if (y >= anInt454 && y < anInt452 - 1) {
                        Tile tile5 = tiles[x][y + 1];

                        if (tile5 != null && tile5.aBoolean1323 && (tile5.aBoolean1322 || (tile1.anInt1320 & 2) == 0)) {
                            continue;
                        }
                    }
                } else {
                    flag = true;
                }
                tile1.aBoolean1322 = false;

                if (tile1.tile != null) {
                    Tile tile2 = tile1.tile;

                    if (tile2.aClass43_1311 != null) {
                        if (!method320(0, x, y)) {
                            method315(tile2.aClass43_1311, 0, anInt458, anInt459, anInt460, anInt461, x, y);
                        }
                    } else if (tile2.aClass40_1312 != null && !method320(0, x, y)) {
                        method316(x, anInt458, anInt460, tile2.aClass40_1312, anInt459, y, anInt461);
                    }
                    Wall wall = tile2.wall;

                    if (wall != null) {
                        wall.anim1.method443(0, anInt458, anInt459, anInt460, anInt461, wall.anInt274 - anInt455, wall.anInt273 - anInt456, wall.anInt275 - anInt457, wall.uid);
                    }

                    for (int i2 = 0; i2 < tile2.anInt1317; i2++) {
                        Object5 obj5 = tile2.obj5Array[i2];

                        if (obj5 != null) {
                            obj5.anim.method443(obj5.anInt522, anInt458, anInt459, anInt460, anInt461, obj5.anInt519 - anInt455, obj5.anInt518 - anInt456, obj5.anInt520 - anInt457, obj5.uid);
                        }
                    }
                }
                boolean flag1 = false;

                if (tile1.aClass43_1311 != null) {
                    if (!method320(z, x, y)) {
                        flag1 = true;
                        method315(tile1.aClass43_1311, z, anInt458, anInt459, anInt460, anInt461, x, y);
                    }
                } else if (tile1.aClass40_1312 != null && !method320(z, x, y)) {
                    flag1 = true;
                    method316(x, anInt458, anInt460, tile1.aClass40_1312, anInt459, y, anInt461);
                }
                int j1 = 0;
                int j2 = 0;
                Wall wall = tile1.wall;
                WallDecoration wallDecoration = tile1.wallDecoration;

                if (wall != null || wallDecoration != null) {
                    if (anInt453 == x) {
                        j1++;
                    } else if (anInt453 < x) {
                        j1 += 2;
                    }

                    if (anInt454 == y) {
                        j1 += 3;
                    } else if (anInt454 > y) {
                        j1 += 6;
                    }
                    j2 = anIntArray478[j1];
                    tile1.anInt1328 = anIntArray480[j1];
                }

                if (wall != null) {
                    if ((wall.orientation & anIntArray479[j1]) != 0) {
                        if (wall.orientation == 16) {
                            tile1.anInt1325 = 3;
                            tile1.anInt1326 = anIntArray481[j1];
                            tile1.anInt1327 = 3 - tile1.anInt1326;
                        } else if (wall.orientation == 32) {
                            tile1.anInt1325 = 6;
                            tile1.anInt1326 = anIntArray482[j1];
                            tile1.anInt1327 = 6 - tile1.anInt1326;
                        } else if (wall.orientation == 64) {
                            tile1.anInt1325 = 12;
                            tile1.anInt1326 = anIntArray483[j1];
                            tile1.anInt1327 = 12 - tile1.anInt1326;
                        } else {
                            tile1.anInt1325 = 9;
                            tile1.anInt1326 = anIntArray484[j1];
                            tile1.anInt1327 = 9 - tile1.anInt1326;
                        }
                    } else {
                        tile1.anInt1325 = 0;
                    }

                    if ((wall.orientation & j2) != 0 && !method321(z, x, y, wall.orientation)) {
                        wall.anim1.method443(0, anInt458, anInt459, anInt460, anInt461, wall.anInt274 - anInt455, wall.anInt273 - anInt456, wall.anInt275 - anInt457, wall.uid);
                    }

                    if ((wall.orientation1 & j2) != 0 && !method321(z, x, y, wall.orientation1)) {
                        wall.anim2.method443(0, anInt458, anInt459, anInt460, anInt461, wall.anInt274 - anInt455, wall.anInt273 - anInt456, wall.anInt275 - anInt457, wall.uid);
                    }
                }

                if (wallDecoration != null && !method322(z, x, y, wallDecoration.anim.modelHeight)) {
                    if ((wallDecoration.anInt502 & j2) != 0) {
                        wallDecoration.anim.method443(wallDecoration.anInt503, anInt458, anInt459, anInt460, anInt461, wallDecoration.x - anInt455, wallDecoration.elevation - anInt456, wallDecoration.y - anInt457, wallDecoration.uid);
                    } else if ((wallDecoration.anInt502 & 0x300) != 0) {
                        int j4 = wallDecoration.x - anInt455;
                        int l5 = wallDecoration.elevation - anInt456;
                        int k6 = wallDecoration.y - anInt457;
                        int i8 = wallDecoration.anInt503;
                        int k9;

                        if (i8 == 1 || i8 == 2) {
                            k9 = -j4;
                        } else {
                            k9 = j4;
                        }
                        int k10;

                        if (i8 == 2 || i8 == 3) {
                            k10 = -k6;
                        } else {
                            k10 = k6;
                        }

                        if ((wallDecoration.anInt502 & 0x100) != 0 && k10 < k9) {
                            int i11 = j4 + anIntArray463[i8];
                            int k11 = k6 + anIntArray464[i8];
                            wallDecoration.anim.method443(i8 * 512 + 256, anInt458, anInt459, anInt460, anInt461, i11, l5, k11, wallDecoration.uid);
                        }

                        if ((wallDecoration.anInt502 & 0x200) != 0 && k10 > k9) {
                            int j11 = j4 + anIntArray465[i8];
                            int l11 = k6 + anIntArray466[i8];
                            wallDecoration.anim.method443(i8 * 512 + 1280 & 0x7ff, anInt458, anInt459, anInt460, anInt461, j11, l5, l11, wallDecoration.uid);
                        }
                    }
                }

                if (flag1) {
                    TileDecoration decoration = tile1.decoration;
                    if (decoration != null) {
                        decoration.model.method443(0, anInt458, anInt459, anInt460, anInt461, decoration.x - anInt455, decoration.elevation - anInt456, decoration.y - anInt457, decoration.uid);
                    }

                    Object4 obj4 = tile1.obj4;
                    if (obj4 != null && obj4.anInt52 == 0) {
                        if (obj4.anim2 != null) {
                            obj4.anim2.method443(0, anInt458, anInt459, anInt460, anInt461, obj4.x - anInt455, obj4.anInt45 - anInt456, obj4.y - anInt457, obj4.uid);
                        }

                        if (obj4.anim3 != null) {
                            obj4.anim3.method443(0, anInt458, anInt459, anInt460, anInt461, obj4.x - anInt455, obj4.anInt45 - anInt456, obj4.y - anInt457, obj4.uid);
                        }

                        if (obj4.anim1 != null) {
                            obj4.anim1.method443(0, anInt458, anInt459, anInt460, anInt461, obj4.x - anInt455, obj4.anInt45 - anInt456, obj4.y - anInt457, obj4.uid);
                        }
                    }
                }
                int k4 = tile1.anInt1320;
                if (k4 != 0) {
                    if (x < anInt453 && (k4 & 4) != 0) {
                        Tile class30_sub3_17 = tiles[x + 1][y];
                        if (class30_sub3_17 != null && class30_sub3_17.aBoolean1323) {
                            groundList.insertHead(class30_sub3_17);
                        }
                    }
                    if (y < anInt454 && (k4 & 2) != 0) {
                        Tile class30_sub3_18 = tiles[x][y + 1];
                        if (class30_sub3_18 != null && class30_sub3_18.aBoolean1323) {
                            groundList.insertHead(class30_sub3_18);
                        }
                    }
                    if (x > anInt453 && (k4 & 1) != 0) {
                        Tile class30_sub3_19 = tiles[x - 1][y];
                        if (class30_sub3_19 != null && class30_sub3_19.aBoolean1323) {
                            groundList.insertHead(class30_sub3_19);
                        }
                    }
                    if (y > anInt454 && (k4 & 8) != 0) {
                        Tile class30_sub3_20 = tiles[x][y - 1];
                        if (class30_sub3_20 != null && class30_sub3_20.aBoolean1323) {
                            groundList.insertHead(class30_sub3_20);
                        }
                    }
                }
            }
            if (tile1.anInt1325 != 0) {
                boolean flag2 = true;
                for (int k1 = 0; k1 < tile1.anInt1317; k1++) {
                    if (tile1.obj5Array[k1].anInt528 == anInt448 || (tile1.anIntArray1319[k1] & tile1.anInt1325) != tile1.anInt1326) {
                        continue;
                    }
                    flag2 = false;
                    break;
                }

                if (flag2) {
                    Wall wall = tile1.wall;
                    if (!method321(z, x, y, wall.orientation)) {
                        wall.anim1.method443(0, anInt458, anInt459, anInt460, anInt461, wall.anInt274 - anInt455, wall.anInt273 - anInt456, wall.anInt275 - anInt457, wall.uid);
                    }
                    tile1.anInt1325 = 0;
                }
            }
            if (tile1.aBoolean1324) {
                try {
                    int i1 = tile1.anInt1317;
                    tile1.aBoolean1324 = false;
                    int l1 = 0;
                    label0:
                    for (int k2 = 0; k2 < i1; k2++) {
                        Object5 class28_1 = tile1.obj5Array[k2];
                        if (class28_1.anInt528 == anInt448) {
                            continue;
                        }
                        for (int k3 = class28_1.x; k3 <= class28_1.anInt524; k3++) {
                            for (int l4 = class28_1.y; l4 <= class28_1.anInt526; l4++) {
                                Tile class30_sub3_21 = tiles[k3][l4];
                                if (class30_sub3_21.aBoolean1322) {
                                    tile1.aBoolean1324 = true;
                                } else {
                                    if (class30_sub3_21.anInt1325 == 0) {
                                        continue;
                                    }
                                    int l6 = 0;
                                    if (k3 > class28_1.x) {
                                        l6++;
                                    }
                                    if (k3 < class28_1.anInt524) {
                                        l6 += 4;
                                    }
                                    if (l4 > class28_1.y) {
                                        l6 += 8;
                                    }
                                    if (l4 < class28_1.anInt526) {
                                        l6 += 2;
                                    }
                                    if ((l6 & class30_sub3_21.anInt1325) != tile1.anInt1327) {
                                        continue;
                                    }
                                    tile1.aBoolean1324 = true;
                                }
                                continue label0;
                            }

                        }

                        aClass28Array462[l1++] = class28_1;
                        int i5 = anInt453 - class28_1.x;
                        int i6 = class28_1.anInt524 - anInt453;
                        if (i6 > i5) {
                            i5 = i6;
                        }
                        int i7 = anInt454 - class28_1.y;
                        int j8 = class28_1.anInt526 - anInt454;
                        if (j8 > i7) {
                            class28_1.anInt527 = i5 + j8;
                        } else {
                            class28_1.anInt527 = i5 + i7;
                        }
                    }

                    while (l1 > 0) {
                        int i3 = -50;
                        int l3 = -1;
                        for (int j5 = 0; j5 < l1; j5++) {
                            Object5 class28_2 = aClass28Array462[j5];
                            if (class28_2.anInt528 != anInt448) {
                                if (class28_2.anInt527 > i3) {
                                    i3 = class28_2.anInt527;
                                    l3 = j5;
                                } else if (class28_2.anInt527 == i3) {
                                    int j7 = class28_2.anInt519 - anInt455;
                                    int k8 = class28_2.anInt520 - anInt457;
                                    int l9 = aClass28Array462[l3].anInt519 - anInt455;
                                    int l10 = aClass28Array462[l3].anInt520 - anInt457;
                                    if (j7 * j7 + k8 * k8 > l9 * l9 + l10 * l10) {
                                        l3 = j5;
                                    }
                                }
                            }
                        }

                        if (l3 == -1) {
                            break;
                        }
                        Object5 class28_3 = aClass28Array462[l3];
                        class28_3.anInt528 = anInt448;
                        if (!method323(z, class28_3.x, class28_3.anInt524, class28_3.y, class28_3.anInt526, class28_3.anim.modelHeight)) {
                            class28_3.anim.method443(class28_3.anInt522, anInt458, anInt459, anInt460, anInt461, class28_3.anInt519 - anInt455, class28_3.anInt518 - anInt456, class28_3.anInt520 - anInt457, class28_3.uid);
                        }
                        for (int k7 = class28_3.x; k7 <= class28_3.anInt524; k7++) {
                            for (int l8 = class28_3.y; l8 <= class28_3.anInt526; l8++) {
                                Tile class30_sub3_22 = tiles[k7][l8];
                                if (class30_sub3_22.anInt1325 != 0) {
                                    groundList.insertHead(class30_sub3_22);
                                } else if ((k7 != x || l8 != y) && class30_sub3_22.aBoolean1323) {
                                    groundList.insertHead(class30_sub3_22);
                                }
                            }

                        }

                    }
                    if (tile1.aBoolean1324) {
                        continue;
                    }
                } catch (Exception _ex) {
                    tile1.aBoolean1324 = false;
                }
            }
            if (!tile1.aBoolean1323 || tile1.anInt1325 != 0) {
                continue;
            }
            if (x <= anInt453 && x > anInt449) {
                Tile class30_sub3_8 = tiles[x - 1][y];
                if (class30_sub3_8 != null && class30_sub3_8.aBoolean1323) {
                    continue;
                }
            }
            if (x >= anInt453 && x < anInt450 - 1) {
                Tile class30_sub3_9 = tiles[x + 1][y];
                if (class30_sub3_9 != null && class30_sub3_9.aBoolean1323) {
                    continue;
                }
            }
            if (y <= anInt454 && y > anInt451) {
                Tile class30_sub3_10 = tiles[x][y - 1];
                if (class30_sub3_10 != null && class30_sub3_10.aBoolean1323) {
                    continue;
                }
            }
            if (y >= anInt454 && y < anInt452 - 1) {
                Tile class30_sub3_11 = tiles[x][y + 1];
                if (class30_sub3_11 != null && class30_sub3_11.aBoolean1323) {
                    continue;
                }
            }
            tile1.aBoolean1323 = false;
            anInt446--;
            Object4 object4 = tile1.obj4;
            if (object4 != null && object4.anInt52 != 0) {
                if (object4.anim2 != null) {
                    object4.anim2.method443(0, anInt458, anInt459, anInt460, anInt461, object4.x - anInt455, object4.anInt45 - anInt456 - object4.anInt52, object4.y - anInt457, object4.uid);
                }
                if (object4.anim3 != null) {
                    object4.anim3.method443(0, anInt458, anInt459, anInt460, anInt461, object4.x - anInt455, object4.anInt45 - anInt456 - object4.anInt52, object4.y - anInt457, object4.uid);
                }
                if (object4.anim1 != null) {
                    object4.anim1.method443(0, anInt458, anInt459, anInt460, anInt461, object4.x - anInt455, object4.anInt45 - anInt456 - object4.anInt52, object4.y - anInt457, object4.uid);
                }
            }
            if (tile1.anInt1328 != 0) {
                WallDecoration wallDecoration = tile1.wallDecoration;
                if (wallDecoration != null && !method322(z, x, y, wallDecoration.anim.modelHeight)) {
                    if ((wallDecoration.anInt502 & tile1.anInt1328) != 0) {
                        wallDecoration.anim.method443(wallDecoration.anInt503, anInt458, anInt459, anInt460, anInt461, wallDecoration.x - anInt455, wallDecoration.elevation - anInt456, wallDecoration.y - anInt457, wallDecoration.uid);
                    } else if ((wallDecoration.anInt502 & 0x300) != 0) {
                        int l2 = wallDecoration.x - anInt455;
                        int j3 = wallDecoration.elevation - anInt456;
                        int i4 = wallDecoration.y - anInt457;
                        int k5 = wallDecoration.anInt503;
                        int j6;
                        if (k5 == 1 || k5 == 2) {
                            j6 = -l2;
                        } else {
                            j6 = l2;
                        }
                        int l7;
                        if (k5 == 2 || k5 == 3) {
                            l7 = -i4;
                        } else {
                            l7 = i4;
                        }
                        if ((wallDecoration.anInt502 & 0x100) != 0 && l7 >= j6) {
                            int i9 = l2 + anIntArray463[k5];
                            int i10 = i4 + anIntArray464[k5];
                            wallDecoration.anim.method443(k5 * 512 + 256, anInt458, anInt459, anInt460, anInt461, i9, j3, i10, wallDecoration.uid);
                        }
                        if ((wallDecoration.anInt502 & 0x200) != 0 && l7 <= j6) {
                            int j9 = l2 + anIntArray465[k5];
                            int j10 = i4 + anIntArray466[k5];
                            wallDecoration.anim.method443(k5 * 512 + 1280 & 0x7ff, anInt458, anInt459, anInt460, anInt461, j9, j3, j10, wallDecoration.uid);
                        }
                    }
                }
                Wall wall = tile1.wall;
                if (wall != null) {
                    if ((wall.orientation1 & tile1.anInt1328) != 0 && !method321(z, x, y, wall.orientation1)) {
                        wall.anim2.method443(0, anInt458, anInt459, anInt460, anInt461, wall.anInt274 - anInt455, wall.anInt273 - anInt456, wall.anInt275 - anInt457, wall.uid);
                    }
                    if ((wall.orientation & tile1.anInt1328) != 0 && !method321(z, x, y, wall.orientation)) {
                        wall.anim1.method443(0, anInt458, anInt459, anInt460, anInt461, wall.anInt274 - anInt455, wall.anInt273 - anInt456, wall.anInt275 - anInt457, wall.uid);
                    }
                }
            }
            if (z1 < regionsZ - 1) {
                Tile class30_sub3_12 = this.tiles[z1 + 1][x][y];
                if (class30_sub3_12 != null && class30_sub3_12.aBoolean1323) {
                    groundList.insertHead(class30_sub3_12);
                }
            }
            if (x < anInt453) {
                Tile class30_sub3_13 = tiles[x + 1][y];
                if (class30_sub3_13 != null && class30_sub3_13.aBoolean1323) {
                    groundList.insertHead(class30_sub3_13);
                }
            }
            if (y < anInt454) {
                Tile class30_sub3_14 = tiles[x][y + 1];
                if (class30_sub3_14 != null && class30_sub3_14.aBoolean1323) {
                    groundList.insertHead(class30_sub3_14);
                }
            }
            if (x > anInt453) {
                Tile class30_sub3_15 = tiles[x - 1][y];
                if (class30_sub3_15 != null && class30_sub3_15.aBoolean1323) {
                    groundList.insertHead(class30_sub3_15);
                }
            }
            if (y > anInt454) {
                Tile class30_sub3_16 = tiles[x][y - 1];
                if (class30_sub3_16 != null && class30_sub3_16.aBoolean1323) {
                    groundList.insertHead(class30_sub3_16);
                }
            }
        } while (true);
    }

    private void method315(Class43 class43, int i, int j, int k, int l, int i1, int j1, int k1) {
        int l1;
        int i2 = l1 = (j1 << 7) - anInt455;
        int j2;
        int k2 = j2 = (k1 << 7) - anInt457;
        int l2;
        int i3 = l2 = i2 + 128;
        int j3;
        int k3 = j3 = k2 + 128;
        int l3 = anIntArrayArrayArray440[i][j1][k1] - anInt456;
        int i4 = anIntArrayArrayArray440[i][j1 + 1][k1] - anInt456;
        int j4 = anIntArrayArrayArray440[i][j1 + 1][k1 + 1] - anInt456;
        int k4 = anIntArrayArrayArray440[i][j1][k1 + 1] - anInt456;
        int l4 = k2 * l + i2 * i1 >> 16;
        k2 = k2 * i1 - i2 * l >> 16;
        i2 = l4;
        l4 = l3 * k - k2 * j >> 16;
        k2 = l3 * j + k2 * k >> 16;
        l3 = l4;
        if (k2 < 50) {
            return;
        }
        l4 = j2 * l + i3 * i1 >> 16;
        j2 = j2 * i1 - i3 * l >> 16;
        i3 = l4;
        l4 = i4 * k - j2 * j >> 16;
        j2 = i4 * j + j2 * k >> 16;
        i4 = l4;
        if (j2 < 50) {
            return;
        }
        l4 = k3 * l + l2 * i1 >> 16;
        k3 = k3 * i1 - l2 * l >> 16;
        l2 = l4;
        l4 = j4 * k - k3 * j >> 16;
        k3 = j4 * j + k3 * k >> 16;
        j4 = l4;
        if (k3 < 50) {
            return;
        }
        l4 = j3 * l + l1 * i1 >> 16;
        j3 = j3 * i1 - l1 * l >> 16;
        l1 = l4;
        l4 = k4 * k - j3 * j >> 16;
        j3 = k4 * j + j3 * k >> 16;
        k4 = l4;
        if (j3 < 50) {
            return;
        }
        int i5 = Texture.textureInt1 + (i2 << 9) / k2;
        int j5 = Texture.textureInt2 + (l3 << 9) / k2;
        int k5 = Texture.textureInt1 + (i3 << 9) / j2;
        int l5 = Texture.textureInt2 + (i4 << 9) / j2;
        int i6 = Texture.textureInt1 + (l2 << 9) / k3;
        int j6 = Texture.textureInt2 + (j4 << 9) / k3;
        int k6 = Texture.textureInt1 + (l1 << 9) / j3;
        int l6 = Texture.textureInt2 + (k4 << 9) / j3;
        Texture.anInt1465 = 0;
        if ((i6 - k6) * (l5 - l6) - (j6 - l6) * (k5 - k6) > 0) {
            Texture.aBoolean1462 = i6 < 0 || k6 < 0 || k5 < 0 || i6 > DrawingArea.centerX || k6 > DrawingArea.centerX || k5 > DrawingArea.centerX;
            if (aBoolean467 && method318(anInt468, anInt469, j6, l6, l5, i6, k6, k5)) {
                walkTargetX = j1;
                walkTargetY = k1;
            }
            if (class43.anInt720 == -1) {
                if (class43.anInt718 != 0xbc614e) {
                    Texture.method374(j6, l6, l5, i6, k6, k5, class43.anInt718, class43.anInt719, class43.anInt717);
                }
            } else if (!lowMem) {
                if (class43.aBoolean721) {
                    Texture.method378(j6, l6, l5, i6, k6, k5, class43.anInt718, class43.anInt719, class43.anInt717, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.anInt720);
                } else {
                    Texture.method378(j6, l6, l5, i6, k6, k5, class43.anInt718, class43.anInt719, class43.anInt717, l2, l1, i3, j4, k4, i4, k3, j3, j2, class43.anInt720);
                }
            } else {
                int i7 = anIntArray485[class43.anInt720];
                Texture.method374(j6, l6, l5, i6, k6, k5, method317(i7, class43.anInt718), method317(i7, class43.anInt719), method317(i7, class43.anInt717));
            }
        }
        if ((i5 - k5) * (l6 - l5) - (j5 - l5) * (k6 - k5) > 0) {
            Texture.aBoolean1462 = i5 < 0 || k5 < 0 || k6 < 0 || i5 > DrawingArea.centerX || k5 > DrawingArea.centerX || k6 > DrawingArea.centerX;
            if (aBoolean467 && method318(anInt468, anInt469, j5, l5, l6, i5, k5, k6)) {
                walkTargetX = j1;
                walkTargetY = k1;
            }
            if (class43.anInt720 == -1) {
                if (class43.anInt716 != 0xbc614e) {
                    Texture.method374(j5, l5, l6, i5, k5, k6, class43.anInt716, class43.anInt717, class43.anInt719);
                }
            } else {
                if (!lowMem) {
                    Texture.method378(j5, l5, l6, i5, k5, k6, class43.anInt716, class43.anInt717, class43.anInt719, i2, i3, l1, l3, i4, k4, k2, j2, j3, class43.anInt720);
                    return;
                }
                int j7 = anIntArray485[class43.anInt720];
                Texture.method374(j5, l5, l6, i5, k5, k6, method317(j7, class43.anInt716), method317(j7, class43.anInt717), method317(j7, class43.anInt719));
            }
        }
    }

    private void method316(int i, int j, int k, Class40 class40, int l, int i1, int j1) {
        int k1 = class40.anIntArray673.length;
        for (int l1 = 0; l1 < k1; l1++) {
            int i2 = class40.anIntArray673[l1] - anInt455;
            int k2 = class40.anIntArray674[l1] - anInt456;
            int i3 = class40.anIntArray675[l1] - anInt457;
            int k3 = i3 * k + i2 * j1 >> 16;
            i3 = i3 * j1 - i2 * k >> 16;
            i2 = k3;
            k3 = k2 * l - i3 * j >> 16;
            i3 = k2 * j + i3 * l >> 16;
            k2 = k3;
            if (i3 < 50) {
                return;
            }
            if (class40.anIntArray682 != null) {
                Class40.anIntArray690[l1] = i2;
                Class40.anIntArray691[l1] = k2;
                Class40.anIntArray692[l1] = i3;
            }
            Class40.anIntArray688[l1] = Texture.textureInt1 + (i2 << 9) / i3;
            Class40.anIntArray689[l1] = Texture.textureInt2 + (k2 << 9) / i3;
        }

        Texture.anInt1465 = 0;
        k1 = class40.anIntArray679.length;
        for (int j2 = 0; j2 < k1; j2++) {
            int l2 = class40.anIntArray679[j2];
            int j3 = class40.anIntArray680[j2];
            int l3 = class40.anIntArray681[j2];
            int i4 = Class40.anIntArray688[l2];
            int j4 = Class40.anIntArray688[j3];
            int k4 = Class40.anIntArray688[l3];
            int l4 = Class40.anIntArray689[l2];
            int i5 = Class40.anIntArray689[j3];
            int j5 = Class40.anIntArray689[l3];
            if ((i4 - j4) * (j5 - i5) - (l4 - i5) * (k4 - j4) > 0) {
                Texture.aBoolean1462 = i4 < 0 || j4 < 0 || k4 < 0 || i4 > DrawingArea.centerX || j4 > DrawingArea.centerX || k4 > DrawingArea.centerX;
                if (aBoolean467 && method318(anInt468, anInt469, l4, i5, j5, i4, j4, k4)) {
                    walkTargetX = i;
                    walkTargetY = i1;
                }
                if (class40.anIntArray682 == null || class40.anIntArray682[j2] == -1) {
                    if (class40.anIntArray676[j2] != 0xbc614e) {
                        Texture.method374(l4, i5, j5, i4, j4, k4, class40.anIntArray676[j2], class40.anIntArray677[j2], class40.anIntArray678[j2]);
                    }
                } else if (!lowMem) {
                    if (class40.aBoolean683) {
                        Texture.method378(l4, i5, j5, i4, j4, k4, class40.anIntArray676[j2], class40.anIntArray677[j2], class40.anIntArray678[j2], Class40.anIntArray690[0], Class40.anIntArray690[1], Class40.anIntArray690[3], Class40.anIntArray691[0], Class40.anIntArray691[1], Class40.anIntArray691[3], Class40.anIntArray692[0], Class40.anIntArray692[1], Class40.anIntArray692[3], class40.anIntArray682[j2]);
                    } else {
                        Texture.method378(l4, i5, j5, i4, j4, k4, class40.anIntArray676[j2], class40.anIntArray677[j2], class40.anIntArray678[j2], Class40.anIntArray690[l2], Class40.anIntArray690[j3], Class40.anIntArray690[l3], Class40.anIntArray691[l2], Class40.anIntArray691[j3], Class40.anIntArray691[l3], Class40.anIntArray692[l2], Class40.anIntArray692[j3], Class40.anIntArray692[l3], class40.anIntArray682[j2]);
                    }
                } else {
                    int k5 = anIntArray485[class40.anIntArray682[j2]];
                    Texture.method374(l4, i5, j5, i4, j4, k4, method317(k5, class40.anIntArray676[j2]), method317(k5, class40.anIntArray677[j2]), method317(k5, class40.anIntArray678[j2]));
                }
            }
        }
    }

    private int method317(int j, int k) {
        k = 127 - k;
        k = (k * (j & 0x7f)) / 160;
        if (k < 2) {
            k = 2;
        } else if (k > 126) {
            k = 126;
        }
        return (j & 0xff80) + k;
    }

    private boolean method318(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        if (j < k && j < l && j < i1) {
            return false;
        }
        if (j > k && j > l && j > i1) {
            return false;
        }
        if (i < j1 && i < k1 && i < l1) {
            return false;
        }
        if (i > j1 && i > k1 && i > l1) {
            return false;
        }
        int i2 = (j - k) * (k1 - j1) - (i - j1) * (l - k);
        int j2 = (j - i1) * (j1 - l1) - (i - l1) * (k - i1);
        int k2 = (j - l) * (l1 - k1) - (i - k1) * (i1 - l);
        return i2 * k2 > 0 && k2 * j2 > 0;
    }

    private void method319() {
        int j = anIntArray473[anInt447];
        Class47 aclass47[] = aClass47ArrayArray474[anInt447];
        anInt475 = 0;
        for (int k = 0; k < j; k++) {
            Class47 class47 = aclass47[k];
            if (class47.anInt791 == 1) {
                int l = (class47.anInt787 - anInt453) + 25;
                if (l < 0 || l > 50) {
                    continue;
                }
                int k1 = (class47.anInt789 - anInt454) + 25;
                if (k1 < 0) {
                    k1 = 0;
                }
                int j2 = (class47.anInt790 - anInt454) + 25;
                if (j2 > 50) {
                    j2 = 50;
                }
                boolean flag = false;
                while (k1 <= j2) {
                    if (aBooleanArrayArray492[l][k1++]) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    continue;
                }
                int j3 = anInt455 - class47.anInt792;
                if (j3 > 32) {
                    class47.anInt798 = 1;
                } else {
                    if (j3 >= -32) {
                        continue;
                    }
                    class47.anInt798 = 2;
                    j3 = -j3;
                }
                class47.anInt801 = (class47.anInt794 - anInt457 << 8) / j3;
                class47.anInt802 = (class47.anInt795 - anInt457 << 8) / j3;
                class47.anInt803 = (class47.anInt796 - anInt456 << 8) / j3;
                class47.anInt804 = (class47.anInt797 - anInt456 << 8) / j3;
                aClass47Array476[anInt475++] = class47;
                continue;
            }
            if (class47.anInt791 == 2) {
                int i1 = (class47.anInt789 - anInt454) + 25;
                if (i1 < 0 || i1 > 50) {
                    continue;
                }
                int l1 = (class47.anInt787 - anInt453) + 25;
                if (l1 < 0) {
                    l1 = 0;
                }
                int k2 = (class47.anInt788 - anInt453) + 25;
                if (k2 > 50) {
                    k2 = 50;
                }
                boolean flag1 = false;
                while (l1 <= k2) {
                    if (aBooleanArrayArray492[l1++][i1]) {
                        flag1 = true;
                        break;
                    }
                }
                if (!flag1) {
                    continue;
                }
                int k3 = anInt457 - class47.anInt794;
                if (k3 > 32) {
                    class47.anInt798 = 3;
                } else {
                    if (k3 >= -32) {
                        continue;
                    }
                    class47.anInt798 = 4;
                    k3 = -k3;
                }
                class47.anInt799 = (class47.anInt792 - anInt455 << 8) / k3;
                class47.anInt800 = (class47.anInt793 - anInt455 << 8) / k3;
                class47.anInt803 = (class47.anInt796 - anInt456 << 8) / k3;
                class47.anInt804 = (class47.anInt797 - anInt456 << 8) / k3;
                aClass47Array476[anInt475++] = class47;
            } else if (class47.anInt791 == 4) {
                int j1 = class47.anInt796 - anInt456;
                if (j1 > 128) {
                    int i2 = (class47.anInt789 - anInt454) + 25;
                    if (i2 < 0) {
                        i2 = 0;
                    }
                    int l2 = (class47.anInt790 - anInt454) + 25;
                    if (l2 > 50) {
                        l2 = 50;
                    }
                    if (i2 <= l2) {
                        int i3 = (class47.anInt787 - anInt453) + 25;
                        if (i3 < 0) {
                            i3 = 0;
                        }
                        int l3 = (class47.anInt788 - anInt453) + 25;
                        if (l3 > 50) {
                            l3 = 50;
                        }
                        boolean flag2 = false;
                        label0:
                        for (int i4 = i3; i4 <= l3; i4++) {
                            for (int j4 = i2; j4 <= l2; j4++) {
                                if (!aBooleanArrayArray492[i4][j4]) {
                                    continue;
                                }
                                flag2 = true;
                                break label0;
                            }

                        }

                        if (flag2) {
                            class47.anInt798 = 5;
                            class47.anInt799 = (class47.anInt792 - anInt455 << 8) / j1;
                            class47.anInt800 = (class47.anInt793 - anInt455 << 8) / j1;
                            class47.anInt801 = (class47.anInt794 - anInt457 << 8) / j1;
                            class47.anInt802 = (class47.anInt795 - anInt457 << 8) / j1;
                            aClass47Array476[anInt475++] = class47;
                        }
                    }
                }
            }
        }
    }

    private boolean method320(int i, int j, int k) {
        int l = anIntArrayArrayArray445[i][j][k];
        if (l == -anInt448) {
            return false;
        }
        if (l == anInt448) {
            return true;
        }
        int i1 = j << 7;
        int j1 = k << 7;
        if (method324(i1 + 1, anIntArrayArrayArray440[i][j][k], j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k], j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k + 1], (j1 + 128) - 1) && method324(i1 + 1, anIntArrayArrayArray440[i][j][k + 1], (j1 + 128) - 1)) {
            anIntArrayArrayArray445[i][j][k] = anInt448;
            return true;
        } else {
            anIntArrayArrayArray445[i][j][k] = -anInt448;
            return false;
        }
    }

    private boolean method321(int i, int j, int k, int l) {
        if (!method320(i, j, k)) {
            return false;
        }
        int i1 = j << 7;
        int j1 = k << 7;
        int k1 = anIntArrayArrayArray440[i][j][k] - 1;
        int l1 = k1 - 120;
        int i2 = k1 - 230;
        int j2 = k1 - 238;
        if (l < 16) {
            if (l == 1) {
                if (i1 > anInt455) {
                    if (!method324(i1, k1, j1)) {
                        return false;
                    }
                    if (!method324(i1, k1, j1 + 128)) {
                        return false;
                    }
                }
                if (i > 0) {
                    if (!method324(i1, l1, j1)) {
                        return false;
                    }
                    if (!method324(i1, l1, j1 + 128)) {
                        return false;
                    }
                }
                return method324(i1, i2, j1) && method324(i1, i2, j1 + 128);
            }
            if (l == 2) {
                if (j1 < anInt457) {
                    if (!method324(i1, k1, j1 + 128)) {
                        return false;
                    }
                    if (!method324(i1 + 128, k1, j1 + 128)) {
                        return false;
                    }
                }
                if (i > 0) {
                    if (!method324(i1, l1, j1 + 128)) {
                        return false;
                    }
                    if (!method324(i1 + 128, l1, j1 + 128)) {
                        return false;
                    }
                }
                return method324(i1, i2, j1 + 128) && method324(i1 + 128, i2, j1 + 128);
            }
            if (l == 4) {
                if (i1 < anInt455) {
                    if (!method324(i1 + 128, k1, j1)) {
                        return false;
                    }
                    if (!method324(i1 + 128, k1, j1 + 128)) {
                        return false;
                    }
                }
                if (i > 0) {
                    if (!method324(i1 + 128, l1, j1)) {
                        return false;
                    }
                    if (!method324(i1 + 128, l1, j1 + 128)) {
                        return false;
                    }
                }
                return method324(i1 + 128, i2, j1) && method324(i1 + 128, i2, j1 + 128);
            }
            if (l == 8) {
                if (j1 > anInt457) {
                    if (!method324(i1, k1, j1)) {
                        return false;
                    }
                    if (!method324(i1 + 128, k1, j1)) {
                        return false;
                    }
                }
                if (i > 0) {
                    if (!method324(i1, l1, j1)) {
                        return false;
                    }
                    if (!method324(i1 + 128, l1, j1)) {
                        return false;
                    }
                }
                return method324(i1, i2, j1) && method324(i1 + 128, i2, j1);
            }
        }
        if (!method324(i1 + 64, j2, j1 + 64)) {
            return false;
        }
        if (l == 16) {
            return method324(i1, i2, j1 + 128);
        }
        if (l == 32) {
            return method324(i1 + 128, i2, j1 + 128);
        }
        if (l == 64) {
            return method324(i1 + 128, i2, j1);
        }
        if (l == 128) {
            return method324(i1, i2, j1);
        } else {
            System.out.println("Warning unsupported wall type");
            return true;
        }
    }

    private boolean method322(int i, int j, int k, int l) {
        if (!method320(i, j, k)) {
            return false;
        }
        int i1 = j << 7;
        int j1 = k << 7;
        return method324(i1 + 1, anIntArrayArrayArray440[i][j][k] - l, j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k] - l, j1 + 1) && method324((i1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][k + 1] - l, (j1 + 128) - 1) && method324(i1 + 1, anIntArrayArrayArray440[i][j][k + 1] - l, (j1 + 128) - 1);
    }

    private boolean method323(int i, int j, int k, int l, int i1, int j1) {
        if (j == k && l == i1) {
            if (!method320(i, j, l)) {
                return false;
            }
            int k1 = j << 7;
            int i2 = l << 7;
            return method324(k1 + 1, anIntArrayArrayArray440[i][j][l] - j1, i2 + 1) && method324((k1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][l] - j1, i2 + 1) && method324((k1 + 128) - 1, anIntArrayArrayArray440[i][j + 1][l + 1] - j1, (i2 + 128) - 1) && method324(k1 + 1, anIntArrayArrayArray440[i][j][l + 1] - j1, (i2 + 128) - 1);
        }
        for (int l1 = j; l1 <= k; l1++) {
            for (int j2 = l; j2 <= i1; j2++) {
                if (anIntArrayArrayArray445[i][l1][j2] == -anInt448) {
                    return false;
                }
            }

        }

        int k2 = (j << 7) + 1;
        int l2 = (l << 7) + 2;
        int i3 = anIntArrayArrayArray440[i][j][l] - j1;
        if (!method324(k2, i3, l2)) {
            return false;
        }
        int j3 = (k << 7) - 1;
        if (!method324(j3, i3, l2)) {
            return false;
        }
        int k3 = (i1 << 7) - 1;
        return method324(k2, i3, k3) && method324(j3, i3, k3);
    }

    private boolean method324(int i, int j, int k) {
        for (int l = 0; l < anInt475; l++) {
            Class47 class47 = aClass47Array476[l];
            if (class47.anInt798 == 1) {
                int i1 = class47.anInt792 - i;
                if (i1 > 0) {
                    int j2 = class47.anInt794 + (class47.anInt801 * i1 >> 8);
                    int k3 = class47.anInt795 + (class47.anInt802 * i1 >> 8);
                    int l4 = class47.anInt796 + (class47.anInt803 * i1 >> 8);
                    int i6 = class47.anInt797 + (class47.anInt804 * i1 >> 8);
                    if (k >= j2 && k <= k3 && j >= l4 && j <= i6) {
                        return true;
                    }
                }
            } else if (class47.anInt798 == 2) {
                int j1 = i - class47.anInt792;
                if (j1 > 0) {
                    int k2 = class47.anInt794 + (class47.anInt801 * j1 >> 8);
                    int l3 = class47.anInt795 + (class47.anInt802 * j1 >> 8);
                    int i5 = class47.anInt796 + (class47.anInt803 * j1 >> 8);
                    int j6 = class47.anInt797 + (class47.anInt804 * j1 >> 8);
                    if (k >= k2 && k <= l3 && j >= i5 && j <= j6) {
                        return true;
                    }
                }
            } else if (class47.anInt798 == 3) {
                int k1 = class47.anInt794 - k;
                if (k1 > 0) {
                    int l2 = class47.anInt792 + (class47.anInt799 * k1 >> 8);
                    int i4 = class47.anInt793 + (class47.anInt800 * k1 >> 8);
                    int j5 = class47.anInt796 + (class47.anInt803 * k1 >> 8);
                    int k6 = class47.anInt797 + (class47.anInt804 * k1 >> 8);
                    if (i >= l2 && i <= i4 && j >= j5 && j <= k6) {
                        return true;
                    }
                }
            } else if (class47.anInt798 == 4) {
                int l1 = k - class47.anInt794;
                if (l1 > 0) {
                    int i3 = class47.anInt792 + (class47.anInt799 * l1 >> 8);
                    int j4 = class47.anInt793 + (class47.anInt800 * l1 >> 8);
                    int k5 = class47.anInt796 + (class47.anInt803 * l1 >> 8);
                    int l6 = class47.anInt797 + (class47.anInt804 * l1 >> 8);
                    if (i >= i3 && i <= j4 && j >= k5 && j <= l6) {
                        return true;
                    }
                }
            } else if (class47.anInt798 == 5) {
                int i2 = j - class47.anInt796;
                if (i2 > 0) {
                    int j3 = class47.anInt792 + (class47.anInt799 * i2 >> 8);
                    int k4 = class47.anInt793 + (class47.anInt800 * i2 >> 8);
                    int l5 = class47.anInt794 + (class47.anInt801 * i2 >> 8);
                    int i7 = class47.anInt795 + (class47.anInt802 * i2 >> 8);
                    if (i >= j3 && i <= k4 && k >= l5 && k <= i7) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
