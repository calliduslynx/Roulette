package de.mabe.roulette.core;

import java.net.URL;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import de.mabe.roulette.model.Point3D;

public abstract class VisualElement {
    protected GL2 gl;
    protected GLUT glut;
    protected Texture texture;
    protected Point3D position = new Point3D();
    private String textureResource;

    protected void setTextureResource(String textureResource) {
        this.textureResource = textureResource;
    }

    public void applyGL(GL2 gl) {
        this.gl = gl;
        glut = new GLUT(); // TODO check!

        loadTextureIfNeeded(); // TODO check!
    }

    private void loadTextureIfNeeded() {
        if (texture == null && textureResource != null) {
            texture = loadTexture(textureResource);
        }
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public abstract void show();

    private Texture loadTexture(String resource) {
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
