package com.runescape.client;

import java.awt.*;
import java.awt.image.*;

final class RSImageProducer implements ImageProducer, ImageObserver {

    public final int[] pixels;
    private final int width;
    private final int height;
    private final ColorModel colorModel;
    private ImageConsumer imageConsumer;
    private final Image image;

    public RSImageProducer(int width, int height, Component component) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        colorModel = new DirectColorModel(32, 0xff0000, 65280, 255);
        image = component.createImage(this);
        method239();
        component.prepareImage(image, this);
        method239();
        component.prepareImage(image, this);
        method239();
        component.prepareImage(image, this);
        initDrawingArea();
    }

    public void initDrawingArea() {
        DrawingArea.initDrawingArea(height, width, pixels);
    }

    public void drawGraphics(int y, Graphics g, int x) {
        method239();
        g.drawImage(image, x, y, this);
    }

    public synchronized void addConsumer(ImageConsumer imageconsumer) {
        imageConsumer = imageconsumer;
        imageconsumer.setDimensions(width, height);
        imageconsumer.setProperties(null);
        imageconsumer.setColorModel(colorModel);
        imageconsumer.setHints(14);
    }

    public synchronized boolean isConsumer(ImageConsumer imageconsumer) {
        return imageConsumer == imageconsumer;
    }

    public synchronized void removeConsumer(ImageConsumer imageconsumer) {
        if (imageConsumer == imageconsumer) {
            imageConsumer = null;
        }
    }

    public void startProduction(ImageConsumer imageconsumer) {
        addConsumer(imageconsumer);
    }

    public void requestTopDownLeftRightResend(ImageConsumer imageconsumer) {
        System.out.println("TDLR");
    }

    private synchronized void method239() {
        if (imageConsumer != null) {
            imageConsumer.setPixels(0, 0, width, height, colorModel, pixels, 0, width);
            imageConsumer.imageComplete(2);
        }
    }

    public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
        return true;
    }
}
