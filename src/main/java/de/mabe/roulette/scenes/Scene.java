package de.mabe.roulette.scenes;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import de.mabe.roulette.core.VisualElement;

public abstract class Scene {
    private GL2 gl;
    private List<VisualElement> visualElements = new ArrayList<>();

    public void display(GL2 gl) {
        this.gl = gl;
        gl.glPushMatrix();
        internalDisplay();

        for (VisualElement visualElement : visualElements) {
            // visualElement.applyGL(gl);
            visualElement.show();
        }

        gl.glPopMatrix();
    }

    public void init(GL2 gl) {
        this.gl = gl;
        internalInit();
    }

    protected void addVisualElements(VisualElement... newVisualElements) {
        for (VisualElement newVisualElement : newVisualElements) {
            newVisualElement.applyGL(gl);
            visualElements.add(newVisualElement);
        }
    }

    protected abstract void internalDisplay();

    protected abstract void internalInit();
}
