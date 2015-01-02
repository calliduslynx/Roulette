package de.mabe.roulette.model.kessel;

import static de.mabe.roulette.model.kessel.RouletteKesselProperties.angle;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_outerField_inner;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_outerField_outer;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_rollField_upper;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.parts;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_outerField;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_rollField;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.core.VisualElement;
import de.mabe.roulette.model.Point3D;

public class OuterBoxTop extends VisualElement {
    private Texture textureWood;

    @Override
    protected void init() {
        textureWood = VisualElement.loadTexture(gl, "holz.jpg");
    }

    @Override
    public void showInternal() {
        textureWood.bind(gl);

        for (int i = 0; i < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * radius_rollField, height_rollField_upper, cos(angle * (i + 0)) * radius_rollField),
                    new Point3D(sin(angle * (i + 0)) * radius_rollField, height_outerField_inner, cos(angle * (i + 0)) * radius_rollField),
                    new Point3D(sin(angle * (i + 1)) * radius_rollField, height_outerField_inner, cos(angle * (i + 1)) * radius_rollField),
                    new Point3D(sin(angle * (i + 1)) * radius_rollField, height_rollField_upper, cos(angle * (i + 1)) * radius_rollField));
            drawQuad(new Point3D(sin(angle * (i + 0)) * radius_rollField, height_outerField_inner, cos(angle * (i + 0)) * radius_rollField),
                    new Point3D(sin(angle * (i + 0)) * radius_outerField, height_outerField_outer, cos(angle * (i + 0)) * radius_outerField),
                    new Point3D(sin(angle * (i + 1)) * radius_outerField, height_outerField_outer, cos(angle * (i + 1)) * radius_outerField),
                    new Point3D(sin(angle * (i + 1)) * radius_rollField, height_outerField_inner, cos(angle * (i + 1)) * radius_rollField));
        }
    }

}
