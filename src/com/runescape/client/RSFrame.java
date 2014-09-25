package com.runescape.client;

import java.awt.Frame;
import java.awt.Graphics;

/**
 * The parent game frame component.
 * 
 * @author Pure_
 */
final class RSFrame extends Frame {

    private final RSApplet rsApplet;

    public RSFrame(RSApplet rsApplet, int width, int height) {
        this.rsApplet = rsApplet;
        setTitle("Jagex");
        setResizable(false);
        setVisible(true);
        toFront();
        setSize(width + 8, height + 28);
    }

    public Graphics getGraphics() {
        Graphics g = super.getGraphics();
        g.translate(4, 24);
        return g;
    }

    public void update(Graphics g) {
        rsApplet.update(g);
    }

    public void paint(Graphics g) {
        rsApplet.paint(g);
    }
}
