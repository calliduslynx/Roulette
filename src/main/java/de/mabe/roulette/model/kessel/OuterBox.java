package de.mabe.roulette.model.kessel;

import static de.mabe.roulette.model.kessel.RouletteKesselProperties.angle;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_outerCase;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_outerField_outer;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.parts;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_outerField;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_rollField;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.core.VisualElement;
import de.mabe.roulette.model.Point3D;

public class OuterBox extends VisualElement {
    private Texture textureWood;

    @Override
    protected void init() {
        textureWood = VisualElement.loadTexture(gl, "holz.jpg");
    }

    @Override
    public void showInternal() {
        textureWood.bind(gl);

        for (int i = 0; i < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * radius_outerField, height_outerField_outer, cos(angle * (i + 0)) * radius_outerField),
                    new Point3D(sin(angle * (i + 0)) * radius_rollField, height_outerCase, cos(angle * (i + 0)) * radius_rollField),
                    new Point3D(sin(angle * (i + 1)) * radius_rollField, height_outerCase, cos(angle * (i + 1)) * radius_rollField),
                    new Point3D(sin(angle * (i + 1)) * radius_outerField, height_outerField_outer, cos(angle * (i + 1)) * radius_outerField));
            drawQuad(new Point3D(sin(angle * (i + 0)) * radius_rollField, height_outerCase, cos(angle * (i + 0)) * radius_rollField),
                    new Point3D(0, 0, 0),
                    new Point3D(0, 0, 0),
                    new Point3D(sin(angle * (i + 1)) * radius_rollField, height_outerCase, cos(angle * (i + 1)) * radius_rollField));
        }
    }

}
