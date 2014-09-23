package com.runescape.client;

final class MouseDetection implements Runnable {

    private final Client instance;
    public final Object syncObject;
    public final int[] coordsY;
    public boolean running;
    public final int[] coordsX;
    public int coordsIndex;

    public MouseDetection(Client parent) {
        syncObject = new Object();
        coordsY = new int[500];
        coordsX = new int[500];
        running = true;
        instance = parent;
    }

    public void run() {
        while (running) {
            synchronized (syncObject) {
                if (coordsIndex < 500) {
                    coordsX[coordsIndex] = instance.mouseX;
                    coordsY[coordsIndex] = instance.mouseY;
                    coordsIndex++;
                }
            }
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }
        }
    }
}
