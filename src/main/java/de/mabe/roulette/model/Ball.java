package de.mabe.roulette.model;

import de.mabe.roulette.core.VisualElement;

public class Ball extends VisualElement {
    public Ball() {
        setTextureResource("silber.png");
    }

    @Override
    public void show() {
        gl.glPushMatrix();

        gl.glTranslated(position.x, position.y, position.z);

        texture.bind(gl);
        glut.glutSolidSphere(1.5, 10, 10);

        gl.glPopMatrix();
    }
}
