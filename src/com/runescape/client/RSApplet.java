package com.runescape.client;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class RSApplet extends Applet
        implements Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener, WindowListener {

    public static int anInt34;

    private int anInt4;
    private int delayTime;
    int minDelay;
    private final long[] aLongArray7;
    int fps;
    boolean shouldDebug;
    int myWidth;
    int myHeight;
    Graphics graphics;
    RSImageProducer fullGameScreen;
    RSFrame gameFrame;
    private boolean shouldClearScreen;
    boolean awtFocus;
    int idleTime;
    int clickMode2;
    public int mouseX;
    public int mouseY;
    private int clickMode1;
    private int clickX;
    private int clickY;
    private long clickTime;
    int clickMode3;
    int saveClickX;
    int saveClickY;
    long aLong29;
    final int[] keyArray;
    private final int[] charQueue;
    private int readIndex;
    private int writeIndex;

    RSApplet() {
        delayTime = 20;
        minDelay = 1;
        aLongArray7 = new long[10];
        shouldDebug = false;
        shouldClearScreen = true;
        awtFocus = true;
        keyArray = new int[128];
        charQueue = new int[128];
    }

    final void createClientFrame(int width, int height) {
        myWidth = height;
        myHeight = width;
        gameFrame = new RSFrame(this, myWidth, myHeight);
        graphics = getGameComponent().getGraphics();
        fullGameScreen = new RSImageProducer(myWidth, myHeight, getGameComponent());
        startRunnable(this, 1);
    }

    final void initClientFrame(int height, int width) {
        myWidth = width;
        myHeight = height;
        graphics = getGameComponent().getGraphics();
        fullGameScreen = new RSImageProducer(myWidth, myHeight, getGameComponent());
        startRunnable(this, 1);
    }

    public void run() {
        getGameComponent().addMouseListener(this);
        getGameComponent().addMouseMotionListener(this);
        getGameComponent().addKeyListener(this);
        getGameComponent().addFocusListener(this);
        
        if (gameFrame != null) {
            gameFrame.addWindowListener(this);
        }
        drawLoadingText(0, "Loading...");
        startUp();
        int i = 0;
        int j = 256;
        int k = 1;
        int i1 = 0;
        int j1 = 0;
        
        for (int k1 = 0; k1 < 10; k1++) {
            aLongArray7[k1] = System.currentTimeMillis();
        }
        long l = System.currentTimeMillis();
        
        while (anInt4 >= 0) {
            if (anInt4 > 0) {
                anInt4--;
                
                if (anInt4 == 0) {
                    exit();
                    return;
                }
            }
            int i2 = j;
            int j2 = k;
            j = 300;
            k = 1;
            long l1 = System.currentTimeMillis();
            
            if (aLongArray7[i] == 0L) {
                j = i2;
                k = j2;
            } else if (l1 > aLongArray7[i]) {
                j = (int) ((long) (2560 * delayTime) / (l1 - aLongArray7[i]));
            }
            if (j < 25) {
                j = 25;
            }
            if (j > 256) {
                j = 256;
                k = (int) ((long) delayTime - (l1 - aLongArray7[i]) / 10L);
            }
            if (k > delayTime) {
                k = delayTime;
            }
            aLongArray7[i] = l1;
            i = (i + 1) % 10;
            if (k > 1) {
                for (int k2 = 0; k2 < 10; k2++) {
                    if (aLongArray7[k2] != 0L) {
                        aLongArray7[k2] += k;
                    }
                }
            }
            if (k < minDelay) {
                k = minDelay;
            }
            try {
                Thread.sleep(k);
            } catch (InterruptedException _ex) {
                j1++;
            }
            for (; i1 < 256; i1 += j) {
                clickMode3 = clickMode1;
                saveClickX = clickX;
                saveClickY = clickY;
                aLong29 = clickTime;
                clickMode1 = 0;
                tick();
                readIndex = writeIndex;
            }
            i1 &= 0xff;
            
            if (delayTime > 0) {
                fps = (1000 * j) / (delayTime * 256);
            }
            draw();
            
            if (shouldDebug) {
                System.out.println("ntime:" + l1);
                
                for (int l2 = 0; l2 < 10; l2++) {
                    int i3 = ((i - l2 - 1) + 20) % 10;
                    System.out.println("otim" + i3 + ":" + aLongArray7[i3]);
                }
                System.out.println("fps:" + fps + " ratio:" + j + " count:" + i1);
                System.out.println("del:" + k + " deltime:" + delayTime + " mindel:" + minDelay);
                System.out.println("intex:" + j1 + " opos:" + i);
                shouldDebug = false;
                j1 = 0;
            }
        }
        if (anInt4 == -1) {
            exit();
        }
    }

    private void exit() {
        anInt4 = -2;
        cleanUpForExit();
        
        if (gameFrame != null) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException _ex) {
            }
            
            try {
                System.exit(0);
            } catch (Throwable _ex) {
            }
        }
    }

    final void setDelayTime(int frequency) {
        delayTime = 1000 / frequency;
    }

    public final void start() {
        if (anInt4 >= 0) {
            anInt4 = 0;
        }
    }

    public final void stop() {
        if (anInt4 >= 0) {
            anInt4 = 4000 / delayTime;
        }
    }

    public final void destroy() {
        anInt4 = -1;
        
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException _ex) {
        }
        
        if (anInt4 == -1) {
            exit();
        }
    }

    public final void update(Graphics g) {
        if (graphics == null) {
            graphics = g;
        }
        shouldClearScreen = true;
        raiseWelcomeScreen();
    }

    public final void paint(Graphics g) {
        if (graphics == null) {
            graphics = g;
        }
        shouldClearScreen = true;
        raiseWelcomeScreen();
    }

    public final void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        if (gameFrame != null) {
            mouseX -= 4;
            mouseY -= 22;
        }
        idleTime = 0;
        clickX = mouseX;
        clickY = mouseY;
        clickTime = System.currentTimeMillis();
        
        if (e.isMetaDown()) {
            clickMode1 = 2;
            clickMode2 = 2;
        } else {
            clickMode1 = 1;
            clickMode2 = 1;
        }
    }

    public final void mouseReleased(MouseEvent e) {
        idleTime = 0;
        clickMode2 = 0;
    }

    public final void mouseClicked(MouseEvent e) {
    }

    public final void mouseEntered(MouseEvent e) {
    }

    public final void mouseExited(MouseEvent e) {
        idleTime = 0;
        mouseX = -1;
        mouseY = -1;
    }

    public final void mouseDragged(MouseEvent e) {
        updateMouseCoordinates(e);
    }

    public final void mouseMoved(MouseEvent e) {
        updateMouseCoordinates(e);
    }

    private void updateMouseCoordinates(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (gameFrame != null) {
            mouseX -= 4;
            mouseY -= 22;
        }
        idleTime = 0;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public final void keyPressed(KeyEvent e) {
        idleTime = 0;
        int keyCode = e.getKeyCode();
        int keyChar = e.getKeyChar();
        
        if (keyChar < 30) {
            keyChar = 0;
        }
        if (keyCode == 37) {
            keyChar = 1;
        }
        if (keyCode == 39) {
            keyChar = 2;
        }
        if (keyCode == 38) {
            keyChar = 3;
        }
        if (keyCode == 40) {
            keyChar = 4;
        }
        if (keyCode == 17) {
            keyChar = 5;
        }
        if (keyCode == 8) {
            keyChar = 8;
        }
        if (keyCode == 127) {
            keyChar = 8;
        }
        if (keyCode == 9) {
            keyChar = 9;
        }
        if (keyCode == 10) {
            keyChar = 10;
        }
        if (keyCode >= 112 && keyCode <= 123) {
            keyChar = (1008 + keyCode) - 112;
        }
        if (keyCode == 36) {
            keyChar = 1000;
        }
        if (keyCode == 35) {
            keyChar = 1001;
        }
        if (keyCode == 33) {
            keyChar = 1002;
        }
        if (keyCode == 34) {
            keyChar = 1003;
        }
        if (keyChar > 0 && keyChar < 128) {
            keyArray[keyChar] = 1;
        }
        if (keyChar > 4) {
            charQueue[writeIndex] = keyChar;
            writeIndex = writeIndex + 1 & 0x7f;
        }
    }

    public final void keyReleased(KeyEvent e) {
        idleTime = 0;
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();
        
        if (keyChar < '\036') {
            keyChar = '\0';
        }
        if (keyCode == 37) {
            keyChar = '\001';
        }
        if (keyCode == 39) {
            keyChar = '\002';
        }
        if (keyCode == 38) {
            keyChar = '\003';
        }
        if (keyCode == 40) {
            keyChar = '\004';
        }
        if (keyCode == 17) {
            keyChar = '\005';
        }
        if (keyCode == 8) {
            keyChar = '\b';
        }
        if (keyCode == 127) {
            keyChar = '\b';
        }
        if (keyCode == 9) {
            keyChar = '\t';
        }
        if (keyCode == 10) {
            keyChar = '\n';
        }
        if (keyChar > 0 && keyChar < '\200') {
            keyArray[keyChar] = 0;
        }
    }

    public final void keyTyped(KeyEvent e) {
    }

    final int readChar(int dummy) {
        while (dummy >= 0) {
            for (int j = 1; j > 0; j++);
        }
        int k = -1;
        
        if (writeIndex != readIndex) {
            k = charQueue[readIndex];
            readIndex = readIndex + 1 & 0x7f;
        }
        return k;
    }

    public final void focusGained(FocusEvent e) {
        awtFocus = true;
        shouldClearScreen = true;
        raiseWelcomeScreen();
    }

    public final void focusLost(FocusEvent e) {
        awtFocus = false;
        
        for (int i = 0; i < 128; i++) {
            keyArray[i] = 0;
        }
    }

    public final void windowActivated(WindowEvent e) {
    }

    public final void windowClosed(WindowEvent e) {
    }

    public final void windowClosing(WindowEvent e) {
        destroy();
    }

    public final void windowDeactivated(WindowEvent e) {
    }

    public final void windowDeiconified(WindowEvent e) {
    }

    public final void windowIconified(WindowEvent e) {
    }

    public final void windowOpened(WindowEvent e) {
    }

    void startUp() {
    }

    /**
     * The game processing tick.
     */
    void tick() {
    }

    void cleanUpForExit() {
    }

    /**
     * Draws the game.
     */
    void draw() {
    }

    void raiseWelcomeScreen() {
    }

    Component getGameComponent() {
        if (gameFrame != null) {
            return gameFrame;
        } else {
            return this;
        }
    }

    public void startRunnable(Runnable r, int priority) {
        Thread thread = new Thread(r);
        thread.start();
        thread.setPriority(priority);
    }

    void drawLoadingText(int widthFactor, String loadingText) {
        while (graphics == null) {
            graphics = getGameComponent().getGraphics();
            
            try {
                getGameComponent().repaint();
            } catch (Exception _ex) {
            }
            
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException _ex) {
            }
        }
        Font font = new Font("Helvetica", 1, 13);
        FontMetrics fontmetrics = getGameComponent().getFontMetrics(font);
        Font font1 = new Font("Helvetica", 0, 13);
        getGameComponent().getFontMetrics(font1);
        
        if (shouldClearScreen) {
            graphics.setColor(Color.black);
            graphics.fillRect(0, 0, myWidth, myHeight);
            shouldClearScreen = false;
        }
        Color color = new Color(140, 17, 17);
        int j = myHeight / 2 - 18;
        graphics.setColor(color);
        graphics.drawRect(myWidth / 2 - 152, j, 304, 34);
        graphics.fillRect(myWidth / 2 - 150, j + 2, widthFactor * 3, 30);
        graphics.setColor(Color.black);
        graphics.fillRect((myWidth / 2 - 150) + widthFactor * 3, j + 2, 300 - widthFactor * 3, 30);
        graphics.setFont(font);
        graphics.setColor(Color.white);
        graphics.drawString(loadingText, (myWidth - fontmetrics.stringWidth(loadingText)) / 2, j + 22);
    }
}