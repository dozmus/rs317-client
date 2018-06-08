package com.runescape.client.render;

import com.runescape.client.util.node.NodeSub;

public class DrawingArea extends NodeSub {

    public static int pixels[];
    public static int width;
    public static int height;
    public static int topY;
    public static int bottomY;
    public static int topX;
    public static int bottomX;
    public static int centerX;
    public static int centerY;
    public static int anInt1387;

    public DrawingArea() {
    }

    public static void init(int height, int width, int pixels[]) {
        DrawingArea.pixels = pixels;
        DrawingArea.width = width;
        DrawingArea.height = height;
        setSize(height, 0, width, 0);
    }

    public static void resetSize() {
        topX = 0;
        topY = 0;
        bottomX = width;
        bottomY = height;
        centerX = bottomX - 1;
        centerY = bottomX / 2;
    }

    public static void setSize(int bottomY, int topX, int bottomX, int topY) {
        if (topX < 0) {
            topX = 0;
        }
        if (topY < 0) {
            topY = 0;
        }
        if (bottomX > width) {
            bottomX = width;
        }
        if (bottomY > height) {
            bottomY = height;
        }
        DrawingArea.topX = topX;
        DrawingArea.topY = topY;
        DrawingArea.bottomX = bottomX;
        DrawingArea.bottomY = bottomY;
        centerX = DrawingArea.bottomX - 1;
        centerY = DrawingArea.bottomX / 2;
        anInt1387 = DrawingArea.bottomY / 2;
    }

    /**
     * Sets all pixels to 0.
     */
    public static void clear() {
        int length = width * height;

        for (int i = 0; i < length; i++) {
            pixels[i] = 0;
        }
    }

    public static void method335(int i, int j, int k, int l, int i1, int k1) {
        if (k1 < topX) {
            k -= topX - k1;
            k1 = topX;
        }
        if (j < topY) {
            l -= topY - j;
            j = topY;
        }
        if (k1 + k > bottomX) {
            k = bottomX - k1;
        }
        if (j + l > bottomY) {
            l = bottomY - j;
        }
        int l1 = 256 - i1;
        int i2 = (i >> 16 & 0xff) * i1;
        int j2 = (i >> 8 & 0xff) * i1;
        int k2 = (i & 0xff) * i1;
        int k3 = width - k;
        int l3 = k1 + j * width;
        for (int i4 = 0; i4 < l; i4++) {
            for (int j4 = -k; j4 < 0; j4++) {
                int l2 = (pixels[l3] >> 16 & 0xff) * l1;
                int i3 = (pixels[l3] >> 8 & 0xff) * l1;
                int j3 = (pixels[l3] & 0xff) * l1;
                int k4 = ((i2 + l2 >> 8) << 16) + ((j2 + i3 >> 8) << 8) + (k2 + j3 >> 8);
                pixels[l3++] = k4;
            }

            l3 += k3;
        }
    }

    public static void fillPixelsReverseOrder(int height, int topY, int topX, int pixel, int width) {
        if (topX < DrawingArea.topX) {
            width -= DrawingArea.topX - topX;
            topX = DrawingArea.topX;
        }
        if (topY < DrawingArea.topY) {
            height -= DrawingArea.topY - topY;
            topY = DrawingArea.topY;
        }
        if (topX + width > bottomX) {
            width = bottomX - topX;
        }
        if (topY + height > bottomY) {
            height = bottomY - topY;
        }
        int increment = DrawingArea.width - width;
        int i = topX + topY * DrawingArea.width;

        for (int y = -height; y < 0; y++) {
            for (int x = -width; x < 0; x++) {
                pixels[i++] = pixel;
            }
            i += increment;
        }
    }

    public static void fillPixels(int i, int j, int k, int l, int i1) {
        method339(i1, l, j, i);
        method339((i1 + k) - 1, l, j, i);
        method341(i1, l, k, i);
        method341(i1, l, k, (i + j) - 1);
    }

    public static void method338(int i, int j, int k, int l, int i1, int j1) {
        method340(l, i1, i, k, j1);
        method340(l, i1, (i + j) - 1, k, j1);

        if (j >= 3) {
            method342(l, j1, k, i + 1, j - 2);
            method342(l, (j1 + i1) - 1, k, i + 1, j - 2);
        }
    }

    public static void method339(int i, int j, int k, int l) {
        if (i < topY || i >= bottomY) {
            return;
        }
        if (l < topX) {
            k -= topX - l;
            l = topX;
        }
        if (l + k > bottomX) {
            k = bottomX - l;
        }
        int i1 = l + i * width;

        for (int j1 = 0; j1 < k; j1++) {
            pixels[i1 + j1] = j;
        }
    }

    private static void method340(int i, int j, int k, int l, int i1) {
        if (k < topY || k >= bottomY) {
            return;
        }
        if (i1 < topX) {
            j -= topX - i1;
            i1 = topX;
        }
        if (i1 + j > bottomX) {
            j = bottomX - i1;
        }
        int j1 = 256 - l;
        int k1 = (i >> 16 & 0xff) * l;
        int l1 = (i >> 8 & 0xff) * l;
        int i2 = (i & 0xff) * l;
        int i3 = i1 + k * width;

        for (int j3 = 0; j3 < j; j3++) {
            int j2 = (pixels[i3] >> 16 & 0xff) * j1;
            int k2 = (pixels[i3] >> 8 & 0xff) * j1;
            int l2 = (pixels[i3] & 0xff) * j1;
            int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
            pixels[i3++] = k3;
        }
    }

    public static void method341(int i, int j, int k, int l) {
        if (l < topX || l >= bottomX) {
            return;
        }
        if (i < topY) {
            k -= topY - i;
            i = topY;
        }
        if (i + k > bottomY) {
            k = bottomY - i;
        }
        int j1 = l + i * width;

        for (int k1 = 0; k1 < k; k1++) {
            pixels[j1 + k1 * width] = j;
        }
    }

    private static void method342(int i, int j, int k, int l, int i1) {
        if (j < topX || j >= bottomX) {
            return;
        }
        if (l < topY) {
            i1 -= topY - l;
            l = topY;
        }
        if (l + i1 > bottomY) {
            i1 = bottomY - l;
        }
        int j1 = 256 - k;
        int k1 = (i >> 16 & 0xff) * k;
        int l1 = (i >> 8 & 0xff) * k;
        int i2 = (i & 0xff) * k;
        int i3 = j + l * width;

        for (int j3 = 0; j3 < i1; j3++) {
            int j2 = (pixels[i3] >> 16 & 0xff) * j1;
            int k2 = (pixels[i3] >> 8 & 0xff) * j1;
            int l2 = (pixels[i3] & 0xff) * j1;
            int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
            pixels[i3] = k3;
            i3 += width;
        }
    }
}
