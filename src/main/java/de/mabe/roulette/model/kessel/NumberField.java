package de.mabe.roulette.model.kessel;

import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_layField;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_rollField_lower;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.parts;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_layField;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_numField;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.core.VisualElement;
import de.mabe.roulette.model.Point3D;

public class NumberField extends VisualElement {
    private Texture[] textureNumbers;
    private RouletteKesselNumbers rouletteKesselNumbers;

    @Override
    protected void init() {
        rouletteKesselNumbers = new RouletteKesselNumbers(RouletteKesselNumbers.ORIGINAL_COUNT);
        loadTextures();
    }

    private void loadTextures() {
        int numberCount = 37; // means numbers from 0 to numberCount - 1
        textureNumbers = new Texture[numberCount];

        for (int i = 0; i < numberCount; i++) {
            textureNumbers[i] = VisualElement.loadTexture(gl, "nr_" + i + ".png");
        }
    }

    @Override
    public void showInternal() {
        for (int i = 0; i < parts; i++) {
            textureNumbers[rouletteKesselNumbers.get()[i]].bind(gl);
            double angle = 15;
            drawQuad(
                    new Point3D(sin(angle * (i + 1)) * radius_layField, height_layField, cos(angle * (i + 1)) * radius_layField),
                    new Point3D(sin(angle * (i + 1)) * radius_numField, height_rollField_lower, cos(angle * (i + 1)) * radius_numField),
                    new Point3D(sin(angle * (i + 0)) * radius_numField, height_rollField_lower, cos(angle * (i + 0)) * radius_numField),
                    new Point3D(sin(angle * (i + 0)) * radius_layField, height_layField, cos(angle * (i + 0)) * radius_layField));
        }
    }

}
