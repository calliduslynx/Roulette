package de.mabe.roulette.util;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.Animator;

public class GLUtil {

    public static GLCanvas getCanvasInDefaultFrame(String frameTitle) {
        Frame frame = getFrame(frameTitle);
        GLCanvas canvas = getGLCanvas();

        frame.add(canvas);

        applyAnimator(frame, canvas);

        frame.setVisible(true);

        return canvas;
    }

    private static Frame getFrame(String frameTitle) {
        Frame frame = new Frame(frameTitle);
        frame.setLocation(0, 0);
        frame.setSize(800, 600);
        frame.setBackground(Color.WHITE);
        return frame;
    }

    private static void applyAnimator(Frame frame, GLCanvas canvas) {
        final Animator animator = new Animator(canvas);

        // fügt eine Routine hinzu, die dafür sorgt, dass die Animation nach dem
        // schließen des Fensters auch beendet wird
        frame.addWindowListener(
                new WindowAdapter() {
                    // new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // Run this on another thread than the AWT event queue to
                        // make sure the call to Animator.stop() completes before
                        // exiting
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                animator.stop();
                                System.exit(0);
                            }
                        }).start();
                    }
                }
                );
        animator.start();
    }

    private static GLCanvas getGLCanvas() {
        GLCapabilities capabilities = new GLCapabilities(null);
        // einige Einstellungen
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);

        // stellt Fullscreen Antialiasing an
        capabilities.setDoubleBuffered(true);
        capabilities.setStencilBits(1);
        capabilities.setSampleBuffers(true);
        capabilities.setNumSamples(8); // <-- für 8-fach Sampling

        capabilities.setHardwareAccelerated(true);

        GLCanvas canvas = new GLCanvas(capabilities);

        return canvas;
    }

}
