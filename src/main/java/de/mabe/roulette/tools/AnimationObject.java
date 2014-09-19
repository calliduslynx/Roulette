package de.mabe.roulette.tools;

import java.util.ArrayList;
import java.util.Calendar;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.model.MouseHandler;

public abstract class AnimationObject implements GLEventListener {
    protected long lastFrame;
    protected GL2 gl;
    protected GLU glu;
    Camera camera;
    protected int height;
    protected int width;
    ArrayList<Texture> textureSet;
    private int frameCounter;
    private long lastFrameCount;
    protected double speed;

    public AnimationObject(MouseHandler mouseHandler) {
        camera = new Camera(mouseHandler);
    }

    protected void countFrame() {
        if (Calendar.getInstance().getTimeInMillis() - lastFrameCount > 1000) {
            frameCounter = 0;
            lastFrameCount = Calendar.getInstance().getTimeInMillis();
        } else {
            frameCounter++;
        }

    }

    /***
     * wird einmal am start aufgerufen
     * 
     * @param drawable
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        glu = new GLU();

        gl.glShadeModel(GL2.GL_SMOOTH); // Enable smooth shading.

        gl.glClearDepth(1.0f); // "Depth Buffer Setup"
        gl.glEnable(GL2.GL_DEPTH_TEST); // OK
        gl.glDepthFunc(GL2.GL_LEQUAL); // OK

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // erasing color

        // Antialising Zeug
        gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);// optional, tell it to use it's best AA algorithm

        initLight();
        gl.glEnable(GL2.GL_LIGHTING);

        // Texturezeugs
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_LINE_SMOOTH);

        this.init();

        // lastFrame setzen
        lastFrame = Calendar.getInstance().getTimeInMillis();
    }

    protected void setCamera() {
        // Change to projection matrix.
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) width / (float) height;
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(camera.getX(),
                camera.getY(),
                camera.getZ(), 0, 0, 0, 0, 1, 0);

        // Change back to model view matrix.
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /***
     * wird bei Größenänderungen des Fensters aufgerufen
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;

        gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }

    /***
     * wenn sich das Display verändert
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    private void initLight() {
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

    public abstract void init();
}
