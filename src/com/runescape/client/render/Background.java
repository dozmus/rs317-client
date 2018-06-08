package com.runescape.client.render;

import com.runescape.client.io.Stream;
import com.runescape.client.io.StreamLoader;

public final class Background extends DrawingArea {

    public byte bgPixels[];
    public final int[] anIntArray1451;
    public int bgWidth;
    public int bgHeight;
    public int offsetX;
    public int offsetY;
    public int anInt1456;
    private int anInt1457;

    public Background(StreamLoader streamLoader, String fileName, int length) {
        Stream file = new Stream(streamLoader.getDataForName(fileName + ".dat"));
        Stream index = new Stream(streamLoader.getDataForName("index.dat"));
        index.currentOffset = file.readUShort();
        anInt1456 = index.readUShort();
        anInt1457 = index.readUShort();
        int j = index.readUByte();
        anIntArray1451 = new int[j];
        
        for (int k = 0; k < j - 1; k++) {
            anIntArray1451[k + 1] = index.readUTriByte();
        }

        for (int l = 0; l < length; l++) {
            index.currentOffset += 2;
            file.currentOffset += index.readUShort() * index.readUShort();
            index.currentOffset++;
        }
        offsetX = index.readUByte();
        offsetY = index.readUByte();
        bgWidth = index.readUShort();
        bgHeight = index.readUShort();
        int i1 = index.readUByte();
        int pixelCount = bgWidth * bgHeight;
        bgPixels = new byte[pixelCount];
        
        if (i1 == 0) {
            for (int i = 0; i < pixelCount; i++) {
                bgPixels[i] = file.readByte();
            }
            return;
        }
        
        if (i1 == 1) {
            for (int l1 = 0; l1 < bgWidth; l1++) {
                for (int i2 = 0; i2 < bgHeight; i2++) {
                    bgPixels[l1 + i2 * bgWidth] = file.readByte();
                }
            }
        }
    }

    public void method356() {
        anInt1456 /= 2;
        anInt1457 /= 2;
        byte buf[] = new byte[anInt1456 * anInt1457];
        int i = 0;
        
        for (int j = 0; j < bgHeight; j++) {
            for (int k = 0; k < bgWidth; k++) {
                buf[(k + offsetX >> 1) + (j + offsetY >> 1) * anInt1456] = bgPixels[i++];
            }
        }
        bgPixels = buf;
        bgWidth = anInt1456;
        bgHeight = anInt1457;
        offsetX = 0;
        offsetY = 0;
    }

    public void method357() {
        if (bgWidth == anInt1456 && bgHeight == anInt1457) {
            return;
        }
        byte buf[] = new byte[anInt1456 * anInt1457];
        int i = 0;
        
        for (int j = 0; j < bgHeight; j++) {
            for (int k = 0; k < bgWidth; k++) {
                buf[k + offsetX + (j + offsetY) * anInt1456] = bgPixels[i++];
            }
        }
        bgPixels = buf;
        bgWidth = anInt1456;
        bgHeight = anInt1457;
        offsetX = 0;
        offsetY = 0;
    }

    public void method358() {
        byte buf[] = new byte[bgWidth * bgHeight];
        int j = 0;
        
        for (int k = 0; k < bgHeight; k++) {
            for (int l = bgWidth - 1; l >= 0; l--) {
                buf[j++] = bgPixels[l + k * bgWidth];
            }
        }
        bgPixels = buf;
        offsetX = anInt1456 - bgWidth - offsetX;
    }

    public void method359() {
        byte buf[] = new byte[bgWidth * bgHeight];
        int i = 0;
        
        for (int j = bgHeight - 1; j >= 0; j--) {
            for (int k = 0; k < bgWidth; k++) {
                buf[i++] = bgPixels[k + j * bgWidth];
            }
        }
        bgPixels = buf;
        offsetY = anInt1457 - bgHeight - offsetY;
    }

    public void method360(int i, int j, int k) {
        for (int i1 = 0; i1 < anIntArray1451.length; i1++) {
            int j1 = anIntArray1451[i1] >> 16 & 0xff;
            j1 += i;
            
            if (j1 < 0) {
                j1 = 0;
            } else if (j1 > 255) {
                j1 = 255;
            }
            int k1 = anIntArray1451[i1] >> 8 & 0xff;
            k1 += j;
            
            if (k1 < 0) {
                k1 = 0;
            } else if (k1 > 255) {
                k1 = 255;
            }
            int l1 = anIntArray1451[i1] & 0xff;
            l1 += k;
            
            if (l1 < 0) {
                l1 = 0;
            } else if (l1 > 255) {
                l1 = 255;
            }
            anIntArray1451[i1] = (j1 << 16) + (k1 << 8) + l1;
        }
    }

    public void draw(int x, int y) {
        x += offsetX;
        y += offsetY;
        int l = x + y * DrawingArea.width;
        int i1 = 0;
        int j1 = bgHeight;
        int k1 = bgWidth;
        int l1 = DrawingArea.width - k1;
        int i2 = 0;
        
        if (y < DrawingArea.topY) {
            int j2 = DrawingArea.topY - y;
            j1 -= j2;
            y = DrawingArea.topY;
            i1 += j2 * k1;
            l += j2 * DrawingArea.width;
        }
        
        if (y + j1 > DrawingArea.bottomY) {
            j1 -= (y + j1) - DrawingArea.bottomY;
        }
        
        if (x < DrawingArea.topX) {
            int k2 = DrawingArea.topX - x;
            k1 -= k2;
            x = DrawingArea.topX;
            i1 += k2;
            l += k2;
            i2 += k2;
            l1 += k2;
        }
        
        if (x + k1 > DrawingArea.bottomX) {
            int l2 = (x + k1) - DrawingArea.bottomX;
            k1 -= l2;
            i2 += l2;
            l1 += l2;
        }
        
        if (!(k1 <= 0 || j1 <= 0)) {
            copyPixels(j1, DrawingArea.pixels, bgPixels, l1, l, k1, i1, anIntArray1451, i2);
        }
    }

    private void copyPixels(int i, int dest[], byte copiedIndices[], int j, int k, int l, int i1, int src[], int j1) {
        int k1 = -(l >> 2);
        l = -(l & 3);
        
        for (int l1 = -i; l1 < 0; l1++) {
            for (int i2 = k1; i2 < 0; i2++) {
                byte idx = copiedIndices[i1++];
                
                if (idx != 0) {
                    dest[k++] = src[idx & 0xff];
                } else {
                    k++;
                }
                idx = copiedIndices[i1++];
                
                if (idx != 0) {
                    dest[k++] = src[idx & 0xff];
                } else {
                    k++;
                }
                idx = copiedIndices[i1++];
                
                if (idx != 0) {
                    dest[k++] = src[idx & 0xff];
                } else {
                    k++;
                }
                idx = copiedIndices[i1++];
                
                if (idx != 0) {
                    dest[k++] = src[idx & 0xff];
                } else {
                    k++;
                }
            }

            for (int j2 = l; j2 < 0; j2++) {
                byte byte2 = copiedIndices[i1++];
                
                if (byte2 != 0) {
                    dest[k++] = src[byte2 & 0xff];
                } else {
                    k++;
                }
            }
            k += j;
            i1 += j1;
        }
    }
}