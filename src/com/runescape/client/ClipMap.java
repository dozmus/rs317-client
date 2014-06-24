package com.runescape.client;

/**
 * The ClipMap, which essentially represents which tiles are traversable and
 * which are not, for a single plane. This means that four instances reside
 * within the Client class.
 *
 * @author Pure_
 */
public final class ClipMap {

    private final int offsetX;
    private final int offsetY;
    private final int regionsX;
    private final int regionsY;
    public final int[][] mapFlags;

    public ClipMap() {
        offsetX = 0;
        offsetY = 0;
        regionsX = 104;
        regionsY = 104;
        mapFlags = new int[regionsX][regionsY];
        init();
    }

    public void init() {
        for (int x = 0; x < regionsX; x++) {
            for (int y = 0; y < regionsY; y++) {
                if (x == 0 || y == 0 || x == regionsX - 1 || y == regionsY - 1) {
                    mapFlags[x][y] = 0xffffff;
                } else {
                    mapFlags[x][y] = 0x1000000;
                }
            }
        }
    }

    private void setValue(int x, int y, int val) {
        mapFlags[x][y] |= val;
    }

    private void setValue1(int i, int x, int y) {
        mapFlags[x][y] &= 0xffffff - i;
    }

    public void setValue2(int y, int x) {
        x -= offsetX;
        y -= offsetY;
        mapFlags[x][y] &= 0xdfffff;
    }

    public void setValue3(int y, int x) {
        x -= offsetX;
        y -= offsetY;
        mapFlags[x][y] |= 0x200000;
    }

    public void method211(int y, int j, int x, int l, boolean flag) {
        x -= offsetX;
        y -= offsetY;

        if (l == 0) {
            if (j == 0) {
                setValue(x, y, 128);
                setValue(x - 1, y, 8);
            }
            if (j == 1) {
                setValue(x, y, 2);
                setValue(x, y + 1, 32);
            }
            if (j == 2) {
                setValue(x, y, 8);
                setValue(x + 1, y, 128);
            }
            if (j == 3) {
                setValue(x, y, 32);
                setValue(x, y - 1, 2);
            }
        }

        if (l == 1 || l == 3) {
            if (j == 0) {
                setValue(x, y, 1);
                setValue(x - 1, y + 1, 16);
            }
            if (j == 1) {
                setValue(x, y, 4);
                setValue(x + 1, y + 1, 64);
            }
            if (j == 2) {
                setValue(x, y, 16);
                setValue(x + 1, y - 1, 1);
            }
            if (j == 3) {
                setValue(x, y, 64);
                setValue(x - 1, y - 1, 4);
            }
        }

        if (l == 2) {
            if (j == 0) {
                setValue(x, y, 130);
                setValue(x - 1, y, 8);
                setValue(x, y + 1, 32);
            }
            if (j == 1) {
                setValue(x, y, 10);
                setValue(x, y + 1, 32);
                setValue(x + 1, y, 128);
            }
            if (j == 2) {
                setValue(x, y, 40);
                setValue(x + 1, y, 128);
                setValue(x, y - 1, 2);
            }
            if (j == 3) {
                setValue(x, y, 160);
                setValue(x, y - 1, 2);
                setValue(x - 1, y, 8);
            }
        }

        if (flag) {
            if (l == 0) {
                if (j == 0) {
                    setValue(x, y, 0x10000);
                    setValue(x - 1, y, 4096);
                }
                if (j == 1) {
                    setValue(x, y, 1024);
                    setValue(x, y + 1, 16384);
                }
                if (j == 2) {
                    setValue(x, y, 4096);
                    setValue(x + 1, y, 0x10000);
                }
                if (j == 3) {
                    setValue(x, y, 16384);
                    setValue(x, y - 1, 1024);
                }
            }

            if (l == 1 || l == 3) {
                if (j == 0) {
                    setValue(x, y, 512);
                    setValue(x - 1, y + 1, 8192);
                }
                if (j == 1) {
                    setValue(x, y, 2048);
                    setValue(x + 1, y + 1, 32768);
                }
                if (j == 2) {
                    setValue(x, y, 8192);
                    setValue(x + 1, y - 1, 512);
                }
                if (j == 3) {
                    setValue(x, y, 32768);
                    setValue(x - 1, y - 1, 2048);
                }
            }

            if (l == 2) {
                if (j == 0) {
                    setValue(x, y, 0x10400);
                    setValue(x - 1, y, 4096);
                    setValue(x, y + 1, 16384);
                }
                if (j == 1) {
                    setValue(x, y, 5120);
                    setValue(x, y + 1, 16384);
                    setValue(x + 1, y, 0x10000);
                }
                if (j == 2) {
                    setValue(x, y, 20480);
                    setValue(x + 1, y, 0x10000);
                    setValue(x, y - 1, 1024);
                }
                if (j == 3) {
                    setValue(x, y, 0x14000);
                    setValue(x, y - 1, 1024);
                    setValue(x - 1, y, 4096);
                }
            }
        }
    }

    public void method212(boolean flag, int lengthX, int lengthY, int x, int y,
            int j1) {
        int val = 256;

        if (flag) {
            val += 0x20000;
        }
        x -= offsetX;
        y -= offsetY;

        if (j1 == 1 || j1 == 3) {
            int tmp = lengthX;
            lengthX = lengthY;
            lengthY = tmp;
        }

        for (int x1 = x; x1 < x + lengthX; x1++) {
            if (x1 >= 0 && x1 < regionsX) {
                for (int y1 = y; y1 < y + lengthY; y1++) {
                    if (y1 >= 0 && y1 < regionsY) {
                        setValue(x1, y1, val);
                    }
                }
            }
        }
    }

    public void method215(int i, int j, boolean flag, int x, int y) {
        x -= offsetX;
        y -= offsetY;

        if (j == 0) {
            if (i == 0) {
                setValue1(128, x, y);
                setValue1(8, x - 1, y);
            }
            if (i == 1) {
                setValue1(2, x, y);
                setValue1(32, x, y + 1);
            }
            if (i == 2) {
                setValue1(8, x, y);
                setValue1(128, x + 1, y);
            }
            if (i == 3) {
                setValue1(32, x, y);
                setValue1(2, x, y - 1);
            }
        }

        if (j == 1 || j == 3) {
            if (i == 0) {
                setValue1(1, x, y);
                setValue1(16, x - 1, y + 1);
            }
            if (i == 1) {
                setValue1(4, x, y);
                setValue1(64, x + 1, y + 1);
            }
            if (i == 2) {
                setValue1(16, x, y);
                setValue1(1, x + 1, y - 1);
            }
            if (i == 3) {
                setValue1(64, x, y);
                setValue1(4, x - 1, y - 1);
            }
        }

        if (j == 2) {
            if (i == 0) {
                setValue1(130, x, y);
                setValue1(8, x - 1, y);
                setValue1(32, x, y + 1);
            }
            if (i == 1) {
                setValue1(10, x, y);
                setValue1(32, x, y + 1);
                setValue1(128, x + 1, y);
            }
            if (i == 2) {
                setValue1(40, x, y);
                setValue1(128, x + 1, y);
                setValue1(2, x, y - 1);
            }
            if (i == 3) {
                setValue1(160, x, y);
                setValue1(2, x, y - 1);
                setValue1(8, x - 1, y);
            }
        }

        if (flag) {
            if (j == 0) {
                if (i == 0) {
                    setValue1(0x10000, x, y);
                    setValue1(4096, x - 1, y);
                }
                if (i == 1) {
                    setValue1(1024, x, y);
                    setValue1(16384, x, y + 1);
                }
                if (i == 2) {
                    setValue1(4096, x, y);
                    setValue1(0x10000, x + 1, y);
                }
                if (i == 3) {
                    setValue1(16384, x, y);
                    setValue1(1024, x, y - 1);
                }
            }

            if (j == 1 || j == 3) {
                if (i == 0) {
                    setValue1(512, x, y);
                    setValue1(8192, x - 1, y + 1);
                }
                if (i == 1) {
                    setValue1(2048, x, y);
                    setValue1(32768, x + 1, y + 1);
                }
                if (i == 2) {
                    setValue1(8192, x, y);
                    setValue1(512, x + 1, y - 1);
                }
                if (i == 3) {
                    setValue1(32768, x, y);
                    setValue1(2048, x - 1, y - 1);
                }
            }

            if (j == 2) {
                if (i == 0) {
                    setValue1(0x10400, x, y);
                    setValue1(4096, x - 1, y);
                    setValue1(16384, x, y + 1);
                }
                if (i == 1) {
                    setValue1(5120, x, y);
                    setValue1(16384, x, y + 1);
                    setValue1(0x10000, x + 1, y);
                }
                if (i == 2) {
                    setValue1(20480, x, y);
                    setValue1(0x10000, x + 1, y);
                    setValue1(1024, x, y - 1);
                }
                if (i == 3) {
                    setValue1(0x14000, x, y);
                    setValue1(1024, x, y - 1);
                    setValue1(4096, x - 1, y);
                }
            }
        }
    }

    public void method216(int i, int lengthX, int x, int y, int lengthY, boolean flag) {
        int j1 = 256;

        if (flag) {
            j1 += 0x20000;
        }
        x -= offsetX;
        y -= offsetY;

        if (i == 1 || i == 3) {
            int k1 = lengthX;
            lengthX = lengthY;
            lengthY = k1;
        }

        for (int x1 = x; x1 < x + lengthX; x1++) {
            if (x1 >= 0 && x1 < regionsX) {
                for (int y1 = y; y1 < y + lengthY; y1++) {
                    if (y1 >= 0 && y1 < regionsY) {
                        setValue1(j1, x1, y1);
                    }
                }
            }
        }
    }

    public boolean method219(int i, int x, int y, int i1, int j1, int k1) {
        if (x == i && y == k1) {
            return true;
        }
        x -= offsetX;
        y -= offsetY;
        i -= offsetX;
        k1 -= offsetY;

        if (j1 == 0) {
            if (i1 == 0) {
                if (x == i - 1 && y == k1) {
                    return true;
                }
                if (x == i && y == k1 + 1 && (mapFlags[x][y] & 0x1280120) == 0) {
                    return true;
                }
                if (x == i && y == k1 - 1 && (mapFlags[x][y] & 0x1280102) == 0) {
                    return true;
                }
            } else if (i1 == 1) {
                if (x == i && y == k1 + 1) {
                    return true;
                }
                if (x == i - 1 && y == k1 && (mapFlags[x][y] & 0x1280108) == 0) {
                    return true;
                }
                if (x == i + 1 && y == k1 && (mapFlags[x][y] & 0x1280180) == 0) {
                    return true;
                }
            } else if (i1 == 2) {
                if (x == i + 1 && y == k1) {
                    return true;
                }
                if (x == i && y == k1 + 1 && (mapFlags[x][y] & 0x1280120) == 0) {
                    return true;
                }
                if (x == i && y == k1 - 1 && (mapFlags[x][y] & 0x1280102) == 0) {
                    return true;
                }
            } else if (i1 == 3) {
                if (x == i && y == k1 - 1) {
                    return true;
                }
                if (x == i - 1 && y == k1 && (mapFlags[x][y] & 0x1280108) == 0) {
                    return true;
                }
                if (x == i + 1 && y == k1 && (mapFlags[x][y] & 0x1280180) == 0) {
                    return true;
                }
            }
        }

        if (j1 == 2) {
            if (i1 == 0) {
                if (x == i - 1 && y == k1) {
                    return true;
                }
                if (x == i && y == k1 + 1) {
                    return true;
                }
                if (x == i + 1 && y == k1 && (mapFlags[x][y] & 0x1280180) == 0) {
                    return true;
                }
                if (x == i && y == k1 - 1 && (mapFlags[x][y] & 0x1280102) == 0) {
                    return true;
                }
            } else if (i1 == 1) {
                if (x == i - 1 && y == k1 && (mapFlags[x][y] & 0x1280108) == 0) {
                    return true;
                }
                if (x == i && y == k1 + 1) {
                    return true;
                }
                if (x == i + 1 && y == k1) {
                    return true;
                }
                if (x == i && y == k1 - 1 && (mapFlags[x][y] & 0x1280102) == 0) {
                    return true;
                }
            } else if (i1 == 2) {
                if (x == i - 1 && y == k1 && (mapFlags[x][y] & 0x1280108) == 0) {
                    return true;
                }
                if (x == i && y == k1 + 1 && (mapFlags[x][y] & 0x1280120) == 0) {
                    return true;
                }
                if (x == i + 1 && y == k1) {
                    return true;
                }
                if (x == i && y == k1 - 1) {
                    return true;
                }
            } else if (i1 == 3) {
                if (x == i - 1 && y == k1) {
                    return true;
                }
                if (x == i && y == k1 + 1 && (mapFlags[x][y] & 0x1280120) == 0) {
                    return true;
                }
                if (x == i + 1 && y == k1 && (mapFlags[x][y] & 0x1280180) == 0) {
                    return true;
                }
                if (x == i && y == k1 - 1) {
                    return true;
                }
            }
        }

        if (j1 == 9) {
            if (x == i && y == k1 + 1 && (mapFlags[x][y] & 0x20) == 0) {
                return true;
            }
            if (x == i && y == k1 - 1 && (mapFlags[x][y] & 2) == 0) {
                return true;
            }
            if (x == i - 1 && y == k1 && (mapFlags[x][y] & 8) == 0) {
                return true;
            }
            if (x == i + 1 && y == k1 && (mapFlags[x][y] & 0x80) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean method220(int i, int j, int y, int l, int i1, int x) {
        if (x == i && y == j) {
            return true;
        }
        x -= offsetX;
        y -= offsetY;
        i -= offsetX;
        j -= offsetY;

        if (l == 6 || l == 7) {
            if (l == 7) {
                i1 = i1 + 2 & 3;
            }
            if (i1 == 0) {
                if (x == i + 1 && y == j && (mapFlags[x][y] & 0x80) == 0) {
                    return true;
                }
                if (x == i && y == j - 1 && (mapFlags[x][y] & 2) == 0) {
                    return true;
                }
            } else if (i1 == 1) {
                if (x == i - 1 && y == j && (mapFlags[x][y] & 8) == 0) {
                    return true;
                }
                if (x == i && y == j - 1 && (mapFlags[x][y] & 2) == 0) {
                    return true;
                }
            } else if (i1 == 2) {
                if (x == i - 1 && y == j && (mapFlags[x][y] & 8) == 0) {
                    return true;
                }
                if (x == i && y == j + 1 && (mapFlags[x][y] & 0x20) == 0) {
                    return true;
                }
            } else if (i1 == 3) {
                if (x == i + 1 && y == j && (mapFlags[x][y] & 0x80) == 0) {
                    return true;
                }
                if (x == i && y == j + 1 && (mapFlags[x][y] & 0x20) == 0) {
                    return true;
                }
            }
        }

        if (l == 8) {
            if (x == i && y == j + 1 && (mapFlags[x][y] & 0x20) == 0) {
                return true;
            }
            if (x == i && y == j - 1 && (mapFlags[x][y] & 2) == 0) {
                return true;
            }
            if (x == i - 1 && y == j && (mapFlags[x][y] & 8) == 0) {
                return true;
            }
            if (x == i + 1 && y == j && (mapFlags[x][y] & 0x80) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean method221(int i, int j, int x, int l, int i1, int j1, int y) {
        int l1 = (j + j1) - 1;
        int i2 = (i + l) - 1;

        if (x >= j && x <= l1 && y >= i && y <= i2) {
            return true;
        }

        if (x == j - 1 && y >= i && y <= i2 && (mapFlags[x - offsetX][y - offsetY] & 8) == 0 && (i1 & 8) == 0) {
            return true;
        }

        if (x == l1 + 1 && y >= i && y <= i2 && (mapFlags[x - offsetX][y - offsetY] & 0x80) == 0 && (i1 & 2) == 0) {
            return true;
        }
        return y == i - 1 && x >= j && x <= l1 && (mapFlags[x - offsetX][y - offsetY] & 2) == 0
                && (i1 & 4) == 0 || y == i2 + 1 && x >= j && x <= l1
                && (mapFlags[x - offsetX][y - offsetY] & 0x20) == 0 && (i1 & 1) == 0;
    }
}
