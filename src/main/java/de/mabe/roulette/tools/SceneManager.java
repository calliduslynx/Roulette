package de.mabe.roulette.tools;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

import de.mabe.roulette.core.camera.Camera;
import de.mabe.roulette.scenes.Scene;
import de.mabe.roulette.util.GLUtil;

public class SceneManager implements GLEventListener {
    private GL2 gl;
    private GLU glu;
    private GLUT glut;

    private Camera camera;
    private Scene scene;

    protected int height;
    protected int width;

    public SceneManager(Scene scene, Camera camera) {
        this.scene = scene;
        this.camera = camera;
    }

    /***
     * wird einmal am start aufgerufen
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        glu = new GLU();
        glut = new GLUT();

        GLUtil.initDefaultSettings(gl);
        GLUtil.initDefaultLights(gl);

        setCamera();

        scene.init(gl);
    }

    private void setCamera() {
        // Change to projection matrix.
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) width / (float) height;
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(
                camera.getPosition().x, camera.getPosition().y, camera.getPosition().z,
                camera.getTarget().x, camera.getTarget().y, camera.getTarget().z,
                camera.getUp().x, camera.getUp().y, camera.getUp().z);

        // Change back to model view matrix.
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();

        // Bildschirm cleanen
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        setCamera();

        scene.display(gl);

        gl.glFlush();
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

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

}
