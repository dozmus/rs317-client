package com.runescape.client;

import java.awt.*;
import java.awt.image.*;

final class RSImageProducer implements ImageProducer, ImageObserver {

    private static final ColorModel COLOR_MODEL = new DirectColorModel(32, 0xff0000, 65280, 255);
    public final int[] pixels;
    private final int width;
    private final int height;
    private final Image image;
    private ImageConsumer imageConsumer;

    public RSImageProducer(int width, int height, Component component) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        image = component.createImage(this);
        updateImagePixels();
        component.prepareImage(image, this);
        updateImagePixels();
        component.prepareImage(image, this);
        updateImagePixels();
        component.prepareImage(image, this);
        initDrawingArea();
    }

    public void initDrawingArea() {
        DrawingArea.init(height, width, pixels);
    }

    public void drawGraphics(Graphics g, int x, int y) {
        updateImagePixels();
        g.drawImage(image, x, y, this);
    }

    public synchronized void addConsumer(ImageConsumer imageconsumer) {
        imageConsumer = imageconsumer;
        imageconsumer.setDimensions(width, height);
        imageconsumer.setProperties(null);
        imageconsumer.setColorModel(COLOR_MODEL);
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

    private synchronized void updateImagePixels() {
        if (imageConsumer != null) {
            imageConsumer.setPixels(0, 0, width, height, COLOR_MODEL, pixels, 0, width);
            imageConsumer.imageComplete(2);
        }
    }

    public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
        return true;
    }
}
