package de.mabe.roulette.model;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.tools.Point3D;
import de.mabe.roulette.tools.TextureLoader;

public class Ball {
    GL2 gl;
    Texture ballTexture;

    public Ball(GL2 gl, GLU glu) {
        this.gl = gl;
        ballTexture = TextureLoader.loadTexture(gl, "resources/images/silber.png");
    }

    public void show(GL2 gl, Point3D p) {
        this.gl = gl;
        GLUT glut = new GLUT();

        gl.glPushMatrix();

        gl.glTranslated(p.x, p.y, p.z);

        ballTexture.bind(gl);
        glut.glutSolidSphere(1.5, 10, 10);

        gl.glPopMatrix();
    }
}
