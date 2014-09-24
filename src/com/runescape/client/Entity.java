package com.runescape.client;

public class Entity extends Animable {

    public int entScreenX;
    public int entScreenY;
    public final int index = -1;

    public final int[] smallX;
    public final int[] smallY;
    public int interactingEntity;
    int anInt1503;
    int anInt1504;
    int anInt1505;
    public String textSpoken;
    public int height;
    public int turnDirection;
    int anInt1511;
    int anInt1512;
    int anInt1513;
    final int[] hitArray;
    final int[] hitMarkTypes;
    final int[] hitsLoopCycle;
    int anInt1517;
    int anInt1518;
    int anInt1519;
    int anInt1520;
    int anInt1521;
    int anInt1522;
    int anInt1523;
    int anInt1524;
    int smallXYIndex;
    public int anim;
    int anInt1527;
    int anInt1528;
    int anInt1529;
    int anInt1530;
    int anInt1531;
    public int loopCycleStatus;
    public int currentHealth;
    public int maxHealth;
    int textCycle;
    int loopCycle;
    int anInt1538;
    int anInt1539;
    int anInt1540;
    boolean aBoolean1541;
    int anInt1542;
    int anInt1543;
    int anInt1544;
    int anInt1545;
    int anInt1546;
    int anInt1547;
    int anInt1548;
    int anInt1549;
    public int x;
    public int y;
    int anInt1552;
    final boolean[] aBooleanArray1553;
    int anInt1554;
    int anInt1555;
    int anInt1556;
    int anInt1557;

    Entity() {
        smallX = new int[10];
        smallY = new int[10];
        interactingEntity = -1;
        anInt1504 = 32;
        anInt1505 = -1;
        height = 200;
        anInt1511 = -1;
        anInt1512 = -1;
        hitArray = new int[4];
        hitMarkTypes = new int[4];
        hitsLoopCycle = new int[4];
        anInt1517 = -1;
        anInt1520 = -1;
        anim = -1;
        loopCycleStatus = -1000;
        textCycle = 100;
        anInt1540 = 1;
        aBoolean1541 = false;
        aBooleanArray1553 = new boolean[10];
        anInt1554 = -1;
        anInt1555 = -1;
        anInt1556 = -1;
        anInt1557 = -1;
    }

    public final void setPos(int x, int y, boolean flag) {
        if (anim != -1 && Animation.anims[anim].anInt364 == 1) {
            anim = -1;
        }

        if (!flag) {
            int newX = x - smallX[0];
            int newY = y - smallY[0];

            if (newX >= -8 && newX <= 8 && newY >= -8 && newY <= 8) {
                if (smallXYIndex < 9) {
                    smallXYIndex++;
                }

                for (int i1 = smallXYIndex; i1 > 0; i1--) {
                    smallX[i1] = smallX[i1 - 1];
                    smallY[i1] = smallY[i1 - 1];
                    aBooleanArray1553[i1] = aBooleanArray1553[i1 - 1];
                }
                smallX[0] = x;
                smallY[0] = y;
                aBooleanArray1553[0] = false;
                return;
            }
        }
        smallXYIndex = 0;
        anInt1542 = 0;
        anInt1503 = 0;
        smallX[0] = x;
        smallY[0] = y;
        x = smallX[0] * 128 + anInt1540 * 64;
        y = smallY[0] * 128 + anInt1540 * 64;
    }

    public final void method446() {
        smallXYIndex = 0;
        anInt1542 = 0;
    }

    public final void updateHitData(int j, int k, int l) {
        for (int i1 = 0; i1 < 4; i1++) {
            if (hitsLoopCycle[i1] <= l) {
                hitArray[i1] = k;
                hitMarkTypes[i1] = j;
                hitsLoopCycle[i1] = l + 70;
                return;
            }
        }
    }

    public final void moveInDir(boolean flag, int moveDirection) {
        int tmpX = smallX[0];
        int tmpY = smallY[0];

        if (moveDirection == 0) {
            tmpX--;
            tmpY++;
        }

        if (moveDirection == 1) {
            tmpY++;
        }

        if (moveDirection == 2) {
            tmpX++;
            tmpY++;
        }

        if (moveDirection == 3) {
            tmpX--;
        }

        if (moveDirection == 4) {
            tmpX++;
        }

        if (moveDirection == 5) {
            tmpX--;
            tmpY--;
        }

        if (moveDirection == 6) {
            tmpY--;
        }

        if (moveDirection == 7) {
            tmpX++;
            tmpY--;
        }

        if (anim != -1 && Animation.anims[anim].anInt364 == 1) {
            anim = -1;
        }

        if (smallXYIndex < 9) {
            smallXYIndex++;
        }

        for (int l = smallXYIndex; l > 0; l--) {
            smallX[l] = smallX[l - 1];
            smallY[l] = smallY[l - 1];
            aBooleanArray1553[l] = aBooleanArray1553[l - 1];
        }
        smallX[0] = tmpX;
        smallY[0] = tmpY;
        aBooleanArray1553[0] = flag;
    }

    public boolean isVisible() {
        return false;
    }
}
