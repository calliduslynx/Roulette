package de.mabe.roulette.core;

import java.net.URL;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import de.mabe.roulette.model.Point3D;

public abstract class VisualElement {
    protected GL2 gl;
    protected static GLUT glut = new GLUT(); // TODO check if this is really good

    protected Point3D position = new Point3D();

    private boolean initAlreadyDone;

    public void applyGL(GL2 gl) {
        this.gl = gl;

        if (!initAlreadyDone) {
            init();
            initAlreadyDone = true;
        }
    }

    protected void init() {
    };

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public final void show() {
        gl.glPushMatrix();
        gl.glTranslated(position.x, position.y, position.z);
        showInternal();
        gl.glPopMatrix();
    };

    protected abstract void showInternal();

    protected Texture loadTexture(String resource) {
        resource = "src/main/resources/images/" + resource;
        try {
            // find resource
            URL url = ClassLoader.getSystemResource(resource);
            if (url == null) {
                url = new URL("file", "localhost", resource);
            }
            String praefix = resource.substring(resource.lastIndexOf(".") + 1);
            Texture texture = TextureIO.newTexture(url, true, praefix);

            // texture = TextureIO.newTexture( new File(ressource), false);
            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            return texture;
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden der Textur! " + e.getMessage(), e);
        }
    }

    protected void drawQuad(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
        drawQuad(p1, p2, p3, p4, null);
    }

    protected void drawQuad(Point3D p1, Point3D p2, Point3D p3, Point3D p4, Point3D normale) {
        gl.glBegin(GL2.GL_QUADS); // Fange an Quadrate zu zeichnen
        gl.glNormal3d(0, 1, 0.1);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3d(p1.x, p1.y, p1.z); // links und eine Einheit nach oben (oben links)

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3d(p2.x, p2.y, p2.z); // links und eine Einheit nach oben (oben links)

        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3d(p3.x, p3.y, p3.z); // links und eine Einheit nach oben (oben links)

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3d(p4.x, p4.y, p4.z); // links und eine Einheit nach oben (oben links)

        if (normale != null) {
            gl.glNormal3d(normale.x, normale.y, normale.z);
        }

        gl.glEnd(); // Fertig mit Quadraten zeichnen
    }

    // *******************************************
    protected double sin(double angle) {
        return Math.sin(getAngle(angle));
    }

    protected double cos(double angle) {
        return Math.cos(getAngle(angle));
    }

    protected double getAngle(double angle) {
        return angle / 180 * Math.PI;
    }

    // TODO remove
    public static Texture loadTexture(GL2 gl, String resource) {
        resource = "src/main/resources/images/" + resource;
        try {
            // find resource
            URL url = ClassLoader.getSystemResource(resource);
            if (url == null) {
                url = new URL("file", "localhost", resource);
            }
            String praefix = resource.substring(resource.lastIndexOf(".") + 1);
            Texture texture = TextureIO.newTexture(url, true, praefix);

            // texture = TextureIO.newTexture( new File(ressource), false);
            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            return texture;
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden der Textur! " + e.getMessage());
        }
    }
}
