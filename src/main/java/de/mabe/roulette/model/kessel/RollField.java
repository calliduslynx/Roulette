package de.mabe.roulette.model.kessel;

import static de.mabe.roulette.model.kessel.RouletteKesselProperties.angle;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_rollField_lower;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_rollField_upper;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.parts;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_numField;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_rollField;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.core.VisualElement;
import de.mabe.roulette.model.Point3D;

public class RollField extends VisualElement {
    private Texture textureWood;

    @Override
    protected void init() {
        textureWood = VisualElement.loadTexture(gl, "holz.jpg");
    }

    @Override
    public void showInternal() {
        textureWood.bind(gl);

        for (int i = 0; i < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * radius_numField, height_rollField_lower, cos(angle * (i + 0)) * radius_numField),
                    new Point3D(sin(angle * (i + 0)) * radius_rollField, height_rollField_upper, cos(angle * (i + 0)) * radius_rollField),
                    new Point3D(sin(angle * (i + 1)) * radius_rollField, height_rollField_upper, cos(angle * (i + 1)) * radius_rollField),
                    new Point3D(sin(angle * (i + 1)) * radius_numField, height_rollField_lower, cos(angle * (i + 1)) * radius_numField));
        }
    }

}
