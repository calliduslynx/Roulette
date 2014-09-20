package de.mabe.roulette.util;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL2;
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

    public static void initDefaultLights(GL2 gl) {
        float[] LightPos = { 20.0f, 60.0f, 0.0f, 0.0f };
        float[] LightAmb = { 0.0f, 0.0f, 0.0f, 0.0f };
        float[] LightDif = { 0.0f, 0.0f, 0.0f, 0.0f };
        float[] LightSpc = { 1.0f, 1.0f, 1.0f, 1.0f };
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, LightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, LightAmb, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, LightDif, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, LightSpc, 0);

        float light0_pos[] = { 10.0f, 20.0f, 10.0f, 0.0f };

        float light0_color_am[] = { 1, 1, 1, 1 };
        float light0_color_diff[] = { 1, 1, 1, 1 };
        float light0_color_spec[] = { 1, 1, 1, 1 };

        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light0_pos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, light0_color_am, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, light0_color_diff, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, light0_color_spec, 0);

        gl.glColor3d(0.7, 0.0, 0.0);
        gl.glEnable(GL2.GL_NORMALIZE);

        gl.glShadeModel(GL2.GL_SMOOTH);

        // gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);
    }

    public static void initDefaultSettings(GL2 gl) {
        gl.glShadeModel(GL2.GL_SMOOTH); // Enable smooth shading.

        gl.glClearDepth(1.0f); // "Depth Buffer Setup"
        gl.glEnable(GL2.GL_DEPTH_TEST); // OK
        gl.glDepthFunc(GL2.GL_LEQUAL); // OK

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // erasing color

        // Antialising Zeug
        gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);// optional, tell it to use it's best AA algorithm

        gl.glEnable(GL2.GL_LIGHTING);

        // Texturezeugs
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_LINE_SMOOTH);
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
