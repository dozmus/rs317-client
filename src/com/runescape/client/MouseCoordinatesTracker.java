package com.runescape.client;

final class MouseCoordinatesTracker implements Runnable {

    private final Client instance;
    public final Object lock;
    public final int[] coordsX;
    public final int[] coordsY;
    public boolean running;
    public int idx;

    public MouseCoordinatesTracker(Client instance) {
        this.instance = instance;
        lock = new Object();
        coordsX = new int[500];
        coordsY = new int[500];
        running = true;
    }

    public void run() {
        while (running) {
            synchronized (lock) {
                if (idx < 500) {
                    coordsX[idx] = instance.mouseX;
                    coordsY[idx] = instance.mouseY;
                    idx++;
                }
            }
            
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }
        }
    }
}
