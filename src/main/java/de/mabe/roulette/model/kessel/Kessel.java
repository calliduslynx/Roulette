package de.mabe.roulette.model.kessel;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

import de.mabe.roulette.core.VisualElement;
import de.mabe.roulette.model.Point3D;
import de.mabe.roulette.model.RouletteKesselNumbers;
import de.mabe.roulette.model.RouletteKesselParam;

public class Kessel extends VisualElement implements RouletteKesselParam {
    private Texture textureWood;
    private Texture textureMetal;
    private Texture[] textureNumbers;
    private Texture textureBlack;
    private Texture textureRed;
    private Texture textureGreen;
    private Texture textureSilver;
    private RouletteKesselNumbers rouletteKesselNumbers;

    // *******************************************
    public Kessel() {
        rouletteKesselNumbers = new RouletteKesselNumbers(RouletteKesselNumbers.ORIGINAL_COUNT);
    }

    @Override
    public void applyGL(GL2 gl) {
        super.applyGL(gl);
        loadTextures();
    }

    private void loadTextures() {
        textureWood = VisualElement.loadTexture(gl, "holz.jpg");
        textureMetal = VisualElement.loadTexture(gl, "gold2.png");
        textureSilver = VisualElement.loadTexture(gl, "silber.png");
        textureRed = VisualElement.loadTexture(gl, "red.png");
        textureGreen = VisualElement.loadTexture(gl, "green.png");
        textureBlack = VisualElement.loadTexture(gl, "black.png");

        int numberCount = 37; // means numbers from 0 to numberCount - 1
        textureNumbers = new Texture[numberCount];

        for (int i = 0; i < numberCount; i++) {
            textureNumbers[i] = VisualElement.loadTexture(gl, "nr_" + i + ".png");
        }

        // TODO Texturen f�r Zahlen neu -> ohne Rahmen
        // TODO goldene Textur f�r Spinner und Rauten
        // TODO Rauten einf�gen
    }

    // *******************************************
    public void show(GL2 gl, double curAngle) {
        this.gl = gl;
        gl.glPushMatrix();
        caseField();
        outerField();
        rollField();

        gl.glRotated(curAngle, 0, 1, 0);

        numField();
        layField();
        middleField();
        spinner();
        gl.glPopMatrix();
    }

    // *******************************************
    private void spinner() {
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

            if (false) {
                continue;
            }

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

    private void caseField() {
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

    private void outerField() {
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

    private void rollField() {
        textureWood.bind(gl);

        for (int i = 0; i < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * radius_numField, height_rollField_lower, cos(angle * (i + 0)) * radius_numField),
                    new Point3D(sin(angle * (i + 0)) * radius_rollField, height_rollField_upper, cos(angle * (i + 0)) * radius_rollField),
                    new Point3D(sin(angle * (i + 1)) * radius_rollField, height_rollField_upper, cos(angle * (i + 1)) * radius_rollField),
                    new Point3D(sin(angle * (i + 1)) * radius_numField, height_rollField_lower, cos(angle * (i + 1)) * radius_numField));
        }
    }

    private void numField() {
        for (int i = 0; i < parts; i++) {
            textureNumbers[rouletteKesselNumbers.get()[i]].bind(gl);

            drawQuad(
                    new Point3D(sin(angle * (i + 1)) * radius_layField, height_layField, cos(angle * (i + 1)) * radius_layField),
                    new Point3D(sin(angle * (i + 1)) * radius_numField, height_rollField_lower, cos(angle * (i + 1)) * radius_numField),
                    new Point3D(sin(angle * (i + 0)) * radius_numField, height_rollField_lower, cos(angle * (i + 0)) * radius_numField),
                    new Point3D(sin(angle * (i + 0)) * radius_layField, height_layField, cos(angle * (i + 0)) * radius_layField));
        }
    }

    private void layField() {
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

    private void middleField() {
        textureMetal.bind(gl);

        for (int i = 0; i < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_thick_radius, spinner_ground_height, cos(angle * (i + 0)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 0)) * radius_middleField, height_layField, cos(angle * (i + 0)) * radius_middleField),
                    new Point3D(sin(angle * (i + 1)) * radius_middleField, height_layField, cos(angle * (i + 1)) * radius_middleField),
                    new Point3D(sin(angle * (i + 1)) * spinner_thick_radius, spinner_ground_height, cos(angle * (i + 1)) * spinner_thick_radius));
        }
    }

    // *******************************************
    private void drawQuad(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
        drawQuad(p1, p2, p3, p4, null);
    }

    private void drawQuad(Point3D p1, Point3D p2, Point3D p3, Point3D p4, Point3D normale) {
        gl.glBegin(GL2.GL_QUADS); // Fange an Quadrate zu zeichnen
        gl.glNormal3d(0, 1, 0.1);

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3d(p1.x, p1.y, p1.z); // links und eine Einheit nach oben (oben links)

        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3d(p2.x, p2.y, p2.z); // links und eine Einheit nach oben (oben links)

        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3d(p3.x, p3.y, p3.z); // links und eine Einheit nach oben (oben links)

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3d(p4.x, p4.y, p4.z); // links und eine Einheit nach oben (oben links)

        if (normale != null) {
            gl.glNormal3d(normale.x, normale.y, normale.z);
        }

        gl.glEnd(); // Fertig mit Quadraten zeichnen
    }

    // *******************************************
    private double sin(double angle) {
        return Math.sin(getAngle(angle));
    }

    private double cos(double angle) {
        return Math.cos(getAngle(angle));
    }

    private double getAngle(double angle) {
        return angle / 180 * Math.PI;
    }

    @Override
    public void show() {
    }
}
