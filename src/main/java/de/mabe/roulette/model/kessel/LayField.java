package de.mabe.roulette.model.kessel;

import static de.mabe.roulette.model.kessel.RouletteKesselProperties.angle;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_layField;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.height_layField_cross;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.layfieldcross_thickness;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.parts;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_layField;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.radius_middleField;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.core.VisualElement;
import de.mabe.roulette.model.Point3D;

public class LayField extends VisualElement {
    private Texture textureBlack;
    private Texture textureRed;
    private Texture textureGreen;
    private Texture textureSilver;

    @Override
    protected void init() {
        textureSilver = loadTexture("silber.png");
        textureRed = loadTexture("red.png");
        textureGreen = loadTexture("green.png");
        textureBlack = loadTexture("black.png");
    }

    @Override
    public void showInternal() {
        // TODO Layfield absenken
        // TODO silbernes Gitter herum
        for (int i = 0; i < parts; i++) {
            if (i == 0) {
                textureGreen.bind(gl);
            } else {
                if (i % 2 != 0) {
                    textureBlack.bind(gl);
                } else {
                    textureRed.bind(gl);
                }
            }

            drawQuad(new Point3D(sin(angle * (i + 0)) * radius_middleField, height_layField, cos(angle * (i + 0)) * radius_middleField),
                    new Point3D(sin(angle * (i + 0)) * radius_layField, height_layField, cos(angle * (i + 0)) * radius_layField),
                    new Point3D(sin(angle * (i + 1)) * radius_layField, height_layField, cos(angle * (i + 1)) * radius_layField),
                    new Point3D(sin(angle * (i + 1)) * radius_middleField, height_layField, cos(angle * (i + 1)) * radius_middleField));

            // ***** Kleine Gitter um Layfield
            textureSilver.bind(gl);

            double thickness = layfieldcross_thickness;
            drawQuad(new Point3D(sin(angle * i + thickness) * radius_middleField, height_layField + height_layField_cross, cos(angle * i + thickness)
                    * radius_middleField),
                    new Point3D(sin(angle * i + thickness) * radius_layField, height_layField + height_layField_cross, cos(angle * i + thickness)
                            * radius_layField),
                    new Point3D(sin(angle * i - thickness) * radius_layField, height_layField + height_layField_cross, cos(angle * i - thickness)
                            * radius_layField),
                    new Point3D(sin(angle * i - thickness) * radius_middleField, height_layField + height_layField_cross, cos(angle * i - thickness)
                            * radius_middleField));

            thickness /= 4;
            drawQuad(new Point3D(sin(angle * (i + 0)) * (radius_middleField - thickness), height_layField + height_layField_cross, cos(angle * (i + 0))
                    * (radius_middleField - thickness)),
                    new Point3D(sin(angle * (i + 0)) * (radius_middleField + thickness), height_layField + height_layField_cross, cos(angle * (i + 0))
                            * (radius_middleField + thickness)),
                    new Point3D(sin(angle * (i + 1)) * (radius_middleField + thickness), height_layField + height_layField_cross, cos(angle * (i + 1))
                            * (radius_middleField + thickness)),
                    new Point3D(sin(angle * (i + 1)) * (radius_middleField - thickness), height_layField + height_layField_cross, cos(angle * (i + 1))
                            * (radius_middleField - thickness)));

            thickness /= 1.5;
            drawQuad(new Point3D(sin(angle * (i + 0)) * (radius_layField - thickness), height_layField + height_layField_cross, cos(angle * (i + 0))
                    * (radius_layField - thickness)),
                    new Point3D(sin(angle * (i + 0)) * (radius_layField + thickness), height_layField + height_layField_cross, cos(angle * (i + 0))
                            * (radius_layField + thickness)),
                    new Point3D(sin(angle * (i + 1)) * (radius_layField + thickness), height_layField + height_layField_cross, cos(angle * (i + 1))
                            * (radius_layField + thickness)),
                    new Point3D(sin(angle * (i + 1)) * (radius_layField - thickness), height_layField + height_layField_cross, cos(angle * (i + 1))
                            * (radius_layField - thickness)));
        }
    }

}
