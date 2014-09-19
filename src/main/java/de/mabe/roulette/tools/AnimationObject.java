package de.mabe.roulette.tools;

import java.util.ArrayList;
import java.util.Calendar;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.Debugger;
import de.mabe.roulette.model.MouseHandler;

public abstract class AnimationObject implements GLEventListener {
    private Debugger debug;
    protected long lastFrame;
    protected GL gl;
    protected GLU glu;
    protected GLUT glut;
    Camera camera;
    protected int height;
    protected int width;
    ArrayList<Texture> textureSet;
    private int frameCounter;
    private long lastFrameCount;
    protected double speed;

    public AnimationObject(MouseHandler mouseHandler) {
        debug = new Debugger(this);
        camera = new Camera(mouseHandler);

    }

    protected void countFrame() {
        if (Calendar.getInstance().getTimeInMillis() - lastFrameCount > 1000) {
            debug.out("fps: " + frameCounter + " speed:" + speed);
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
        debug.out("init called");
        gl = drawable.getGL(); // OK
        glu = new GLU();

        debug.out("gl" + gl);

        // ***** Global settings.

        // Enable smooth shading.
        // gl.glShadeModel(GL.GL_SMOOTH); // OK

        gl.glClearDepth(1.0f); // "Depth Buffer Setup" //OK
        gl.glEnable(GL.GL_DEPTH_TEST); // OK
        gl.glDepthFunc(GL.GL_LEQUAL); // OK

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // erasing color //OK

        // Antialising Zeug
        // gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);// optional, tell it to use it's best AA algorithm

        /*
         * gl.glHint( GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST ); gl.glHint( GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST ); gl.glHint( GL.GL_POLYGON_SMOOTH_HINT,
         * GL.GL_NICEST );
         * 
         * gl.glEnable(GL.GL_POLYGON_SMOOTH); //turn on antialiasing gl.glEnable( GL.GL_LINE_SMOOTH );
         * 
         * gl.glEnable(GL.GL_MULTISAMPLE);
         */

        // this.initLight();
        // gl.glEnable( GL.GL_LIGHTING );

        // Texturezeugs
        gl.glEnable(GL.GL_TEXTURE_2D); // OK

        gl.glEnable(GL.GL_LINE_SMOOTH);

        this.init();

        // lastFrame setzen
        lastFrame = Calendar.getInstance().getTimeInMillis();
    }

    protected void setCamera() {
        // Change to projection matrix.
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) width / (float) height;
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(camera.getX(),
                camera.getY(),
                camera.getZ(), 0, 0, 0, 0, 1, 0);

        // Change back to model view matrix.
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /***
     * wird bei Größenänderungen des Fensters aufgerufen
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;

        gl = drawable.getGL();
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
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, LightPos, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, LightAmb, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, LightDif, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, LightSpc, 0);

        float light0_pos[] = { 10.0f, 20.0f, 10.0f, 0.0f };

        float light0_color_am[] = { 1, 1, 1, 1 };
        float light0_color_diff[] = { 1, 1, 1, 1 };
        float light0_color_spec[] = { 1, 1, 1, 1 };

        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, light0_pos, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, light0_color_am, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, light0_color_diff, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, light0_color_spec, 0);

        gl.glColor3d(0.7, 0.0, 0.0);
        gl.glEnable(GL.GL_NORMALIZE);

        gl.glShadeModel(GL.GL_SMOOTH);

        gl.glEnable(GL.GL_LIGHT0);
        // gl.glEnable( GL.GL_LIGHT1 );
        gl.glEnable(GL.GL_LIGHTING);

    }

    public abstract void init();
}
