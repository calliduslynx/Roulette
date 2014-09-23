package de.mabe.roulette.model;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.core.VisualElement;

public class Ball extends VisualElement {
    private Texture texture;

    @Override
    protected void init() {
        texture = loadTexture("silber.png");
    }

    @Override
    public void showInternal() {
        texture.bind(gl);
        glut.glutSolidSphere(1.5, 10, 10);
    }
}
