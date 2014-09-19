/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.mabe.roulette.tools;

import java.io.FileNotFoundException;
import java.net.URL;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureLoader {

    public static Texture loadTexture(GL2 gl, String resource) {
        resource = "src/main/" + resource;
        Texture texture = null;
        try {

            // find resource
            URL url = ClassLoader.getSystemResource(resource);
            if (url == null) {
                url = new URL("file", "localhost", resource);
                if (url == null) {
                    throw new FileNotFoundException("Datei " + resource + " konnte nicht gefunden werden!");
                }
            }
            String praefix = resource.substring(resource.lastIndexOf(".") + 1);
            texture = TextureIO.newTexture(url, true, praefix);

            // texture = TextureIO.newTexture( new File(ressource), false);
            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
            texture.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden der Textur! " + e.getMessage());
        }
        return texture;
    }
}
