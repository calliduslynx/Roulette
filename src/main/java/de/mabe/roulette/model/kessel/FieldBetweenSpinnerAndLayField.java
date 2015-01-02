package de.mabe.roulette.model.kessel;

import static de.mabe.roulette.model.kessel.RouletteKesselProperties.angle;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_layField;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.parts;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_middleField;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_ground_height;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_thick_radius;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.core.VisualElement;
import de.mabe.roulette.model.Point3D;

public class FieldBetweenSpinnerAndLayField extends VisualElement {
    private Texture textureMetal;

    @Override
    protected void init() {
        textureMetal = VisualElement.loadTexture(gl, "gold2.png");
    }

    @Override
    public void showInternal() {
        textureMetal.bind(gl);

        for (int i = 0; i < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_thick_radius, spinner_ground_height, cos(angle * (i + 0)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 0)) * radius_middleField, height_layField, cos(angle * (i + 0)) * radius_middleField),
                    new Point3D(sin(angle * (i + 1)) * radius_middleField, height_layField, cos(angle * (i + 1)) * radius_middleField),
                    new Point3D(sin(angle * (i + 1)) * spinner_thick_radius, spinner_ground_height, cos(angle * (i + 1)) * spinner_thick_radius));
        }
    }

}
