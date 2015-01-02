package de.mabe.roulette.model.kessel;

import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_angle;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_arrow_angle;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_arrow_ball_length;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_arrow_ball_middle;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_arrow_ball_radius;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_arrow_begin_radius;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_arrow_count;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_arrow_end_radius;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_arrow_height;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_arrow_length;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_bottom_height;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_cone_height;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_crown_height;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_crown_lower_radius;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_crown_upper_radius;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_ground_height;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_parts;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_shaft_height;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_shaft_radius;
import static de.mabe.roulette.model.kessel.RouletteKesselProperties.spinner_thick_radius;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.core.VisualElement;
import de.mabe.roulette.model.Point3D;

public class Spinner extends VisualElement {
    private Texture textureMetal;

    @Override
    protected void init() {
        textureMetal = VisualElement.loadTexture(gl, "gold2.png");
    }

    @Override
    public void showInternal() {
        textureMetal.bind(gl);
        float mcolor[] = { 0.0f, 0.0f, 0.0f, 1.0f };
        float specReflection[] = { 0.8f, 0.8f, 0.8f, 1.0f };

        // gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, mcolor, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specReflection, 0);

        double angle = spinner_angle;
        for (int i = 0; i < spinner_parts; i++) {
            // ***** untere Verdickung
            drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_thick_radius, spinner_ground_height - 3, cos(angle * (i + 0)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 0)) * spinner_thick_radius, spinner_bottom_height, cos(angle * (i + 0)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_thick_radius, spinner_bottom_height, cos(angle * (i + 1)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_thick_radius, spinner_ground_height - 3, cos(angle * (i + 1)) * spinner_thick_radius));

            // ***** dünnerer Schaft
            drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_thick_radius, spinner_bottom_height, cos(angle * (i + 0)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 0)) * spinner_shaft_radius, spinner_shaft_height, cos(angle * (i + 0)) * spinner_shaft_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_shaft_radius, spinner_shaft_height, cos(angle * (i + 1)) * spinner_shaft_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_thick_radius, spinner_bottom_height, cos(angle * (i + 1)) * spinner_thick_radius));

            // ***** obere Verdickung
            drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_thick_radius, spinner_shaft_height, cos(angle * (i + 0)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 0)) * spinner_thick_radius, spinner_cone_height, cos(angle * (i + 0)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_thick_radius, spinner_cone_height, cos(angle * (i + 1)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_thick_radius, spinner_shaft_height, cos(angle * (i + 1)) * spinner_thick_radius));

            // ***** Übergang Verdickung zur Krone
            drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_crown_lower_radius, spinner_cone_height, cos(angle * (i + 0)) * spinner_crown_lower_radius),
                    new Point3D(sin(angle * (i + 0)) * spinner_thick_radius, spinner_cone_height, cos(angle * (i + 0)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_thick_radius, spinner_cone_height, cos(angle * (i + 1)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_crown_lower_radius, spinner_cone_height, cos(angle * (i + 1)) * spinner_crown_lower_radius));

            // ***** Übergang Verdickung zur Krone
            drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_crown_lower_radius, spinner_cone_height, cos(angle * (i + 0)) * spinner_crown_lower_radius),
                    new Point3D(sin(angle * (i + 0)) * spinner_crown_upper_radius, spinner_crown_height, cos(angle * (i + 0)) * spinner_crown_upper_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_crown_upper_radius, spinner_crown_height, cos(angle * (i + 1)) * spinner_crown_upper_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_crown_lower_radius, spinner_cone_height, cos(angle * (i + 1)) * spinner_crown_lower_radius));

            // ***** Deckel auf Krone
            drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_crown_upper_radius, spinner_crown_height, cos(angle * (i + 0)) * spinner_crown_upper_radius),
                    new Point3D(0, spinner_crown_height, 0),
                    new Point3D(0, spinner_crown_height, 0),
                    new Point3D(sin(angle * (i + 1)) * spinner_crown_upper_radius, spinner_crown_height, cos(angle * (i + 1)) * spinner_crown_upper_radius));

            // ***** Flügel anzeichnen
            for (int j = 0; j < spinner_arrow_count; j++) {
                gl.glPushMatrix();
                // ausrichten
                gl.glTranslated(0, spinner_arrow_height, 0);
                gl.glRotated(90, 1, 0, 0);
                gl.glRotated(spinner_arrow_angle * j, 0, 0, 1);

                // Stab
                drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_arrow_begin_radius, 0, cos(angle * (i + 0)) * spinner_arrow_begin_radius),
                        new Point3D(sin(angle * (i + 0)) * spinner_arrow_end_radius, spinner_arrow_length, cos(angle * (i + 0)) * spinner_arrow_end_radius),
                        new Point3D(sin(angle * (i + 1)) * spinner_arrow_end_radius, spinner_arrow_length, cos(angle * (i + 1)) * spinner_arrow_end_radius),
                        new Point3D(sin(angle * (i + 1)) * spinner_arrow_begin_radius, 0, cos(angle * (i + 1)) * spinner_arrow_begin_radius),
                        new Point3D(1, 0, 0));

                // Kugel auf
                drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_arrow_end_radius, spinner_arrow_length, cos(angle * (i + 0)) * spinner_arrow_end_radius),
                        new Point3D(sin(angle * (i + 0)) * spinner_arrow_ball_radius, spinner_arrow_ball_middle, cos(angle * (i + 0))
                                * spinner_arrow_ball_radius),
                        new Point3D(sin(angle * (i + 1)) * spinner_arrow_ball_radius, spinner_arrow_ball_middle, cos(angle * (i + 1))
                                * spinner_arrow_ball_radius),
                        new Point3D(sin(angle * (i + 1)) * spinner_arrow_end_radius, spinner_arrow_length, cos(angle * (i + 1)) * spinner_arrow_end_radius));

                // Kugel zu
                drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_arrow_ball_radius, spinner_arrow_ball_middle, cos(angle * (i + 0))
                        * spinner_arrow_ball_radius),
                        new Point3D(0, spinner_arrow_ball_length, 0),
                        new Point3D(0, spinner_arrow_ball_length, 0),
                        new Point3D(sin(angle * (i + 1)) * spinner_arrow_ball_radius, spinner_arrow_ball_middle, cos(angle * (i + 1))
                                * spinner_arrow_ball_radius));

                gl.glPopMatrix();
            }
        }
    }

}
