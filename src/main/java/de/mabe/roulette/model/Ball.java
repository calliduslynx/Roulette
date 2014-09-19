package de.mabe.roulette.model;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.tools.Point3D;
import de.mabe.roulette.tools.TextureLoader;

public class Ball {
    GL gl;
    Texture ballTexture;

    public Ball(GL gl, GLU glu) {
        this.gl = gl;
        ballTexture = TextureLoader.loadTexture("resources/images/silber.png");
    }

    public void show(GL gl, Point3D p) {
        this.gl = gl;

        gl.glPushMatrix();

        gl.glTranslated(p.x, p.y, p.z);

        ballTexture.bind();
        glut.glutSolidSphere(1.5, 10, 10);

        gl.glPopMatrix();
    }
}
