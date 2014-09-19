package de.mabe.roulette.model;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.sun.org.apache.xerces.internal.parsers.XMLParser;

import de.mabe.roulette.Debugger;
import de.mabe.roulette.tools.Point3D;
import de.mabe.roulette.tools.TextureLoader;

public class RouletteKessel {
    private GL gl;
    private GLU glu;
    private GLUT glut;
    // <editor-fold desc="configuration members">
    private int parts;
    private double angle;
    // radius
    private double middleField_radius;
    private double layField_radius;
    private double numField_radius;
    private double rollField_radius;
    private double outerField_radius;
    private double outerCase_radius;
    // heights
    private double layfield_height;
    private double rollField_lower_height;
    private double rollField_upper_height;
    private double outerField_inner_height;
    private double outerField_outer_height;
    private double outerCase_height;
    // spinner
    private int spinner_parts;
    private double spinner_angle;
    private double spinner_thick_radius;
    private double spinner_shaft_radius;
    private double spinner_crown_lower_radius;
    private double spinner_crown_upper_radius;
    private double spinner_ground_height;
    private double spinner_bottom_height;
    private double spinner_shaft_height;
    private double spinner_cone_height;
    private double spinner_crown_height;
    // spinner-arrow
    private int spinner_arrow_count;
    private double spinner_arrow_angle;
    private double spinner_arrow_height;
    private double spinner_arrow_length;
    private double spinner_arrow_begin_radius;
    private double spinner_arrow_end_radius;
    private double spinner_arrow_ball_middle;
    private double spinner_arrow_ball_length;
    private double spinner_arrow_ball_radius;
    // layfield-cross
    private double layField_cross_height;
    private double layField_cross_thickness;
    // </editor-fold>
    // <editor-fold desc="texture members">
    private Texture textureWood;
    private Texture textureMetal;
    private Texture[] textureNumbers;
    private Texture textureBlack;
    private Texture textureRed;
    private Texture textureGreen;
    private Texture textureSilver;
    // </editor-fold>
    private RouletteKesselNumbers rouletteKesselNumbers;
    Debugger debug;

    // *******************************************
    public RouletteKessel(GL gl, GLU glu) {
        debug = new Debugger(this);
        debug.out("gl" + gl);

        this.gl = gl;
        this.glu = glu;

        rouletteKesselNumbers = new RouletteKesselNumbers(RouletteKesselNumbers.ORIGINAL_COUNT);

        loadTextures();
        loadValuesFromXML();
    }

    private void loadValuesFromXML() {
        // ***** load xml file
        XMLTree settings = null;
        debug.out("loading xml");
        try {
            settings = XMLParser.parseXML(StreamHelper.getInputStream("resources/config/RouletteKessel.xml"));
        } catch (Exception e) {
            debug.out(e);
        }

        // *******************************************
        // ***** setting values
        // *******************************************
        // ***** Main
        XMLParser.printXMLNodeList(settings);
        XMLNode node = settings.get("main");
        parts = Integer.valueOf(node.getChild("parts").value);
        angle = 360.0 / parts;

        // ***** Radius
        node = settings.get("radius");
        middleField_radius = Double.valueOf(node.getChild("middleField").value);
        layField_radius = Double.valueOf(node.getChild("layField").value);
        numField_radius = Double.valueOf(node.getChild("numField").value);
        rollField_radius = Double.valueOf(node.getChild("rollField").value);
        outerField_radius = Double.valueOf(node.getChild("outerField").value);
        outerCase_radius = Double.valueOf(node.getChild("outerCase").value);

        // ***** Height
        node = settings.get("height");
        layfield_height = Double.valueOf(node.getChild("layField").value);
        rollField_lower_height = Double.valueOf(node.getChild("rollField_lower").value);
        rollField_upper_height = Double.valueOf(node.getChild("rollField_upper").value);
        outerField_inner_height = Double.valueOf(node.getChild("outerField_inner").value);
        outerField_outer_height = Double.valueOf(node.getChild("outerField_outer").value);
        outerCase_height = Double.valueOf(node.getChild("outerCase").value);
        // spinner
        node = settings.get("spinner");
        spinner_parts = Integer.valueOf(node.getChild("parts").value);
        spinner_angle = 360.0 / spinner_parts;
        spinner_thick_radius = Double.valueOf(node.getChild("thick_radius").value);
        spinner_shaft_radius = Double.valueOf(node.getChild("shaft_radius").value);
        spinner_crown_lower_radius = Double.valueOf(node.getChild("crown_lower_radius").value);
        spinner_crown_upper_radius = Double.valueOf(node.getChild("crown_upper_radius").value);
        spinner_ground_height = Double.valueOf(node.getChild("ground_height").value);
        spinner_bottom_height = spinner_ground_height + Double.valueOf(node.getChild("bottom_height").value);
        spinner_shaft_height = spinner_bottom_height + Double.valueOf(node.getChild("shaft_height").value);
        spinner_cone_height = spinner_shaft_height + Double.valueOf(node.getChild("cone_height").value);
        spinner_crown_height = spinner_cone_height + Double.valueOf(node.getChild("crown_height").value);

        // spinner-arrow
        node = settings.get("spinner-arrow");
        spinner_arrow_count = Integer.valueOf(node.getChild("count").value);
        spinner_arrow_angle = 360.0 / spinner_arrow_count;
        spinner_arrow_height = (spinner_cone_height - spinner_shaft_height) / 2 + spinner_shaft_height;
        spinner_arrow_length = Double.valueOf(node.getChild("length").value);
        spinner_arrow_begin_radius = Double.valueOf(node.getChild("begin_radius").value);
        spinner_arrow_end_radius = Double.valueOf(node.getChild("end_radius").value);
        spinner_arrow_ball_middle = spinner_arrow_length + Double.valueOf(node.getChild("ball_middle").value);
        spinner_arrow_ball_length = spinner_arrow_ball_middle + Double.valueOf(node.getChild("ball_length").value);
        spinner_arrow_ball_radius = Double.valueOf(node.getChild("ball_radius").value);

        // layfield-cross
        node = settings.get("layfield-cross");
        layField_cross_height = Double.valueOf(node.getChild("height").value);
        layField_cross_thickness = Double.valueOf(node.getChild("thickness").value);

    }

    private void loadTextures() {
        // loading textures
        debug.out("loading textures");

        textureWood = TextureLoader.loadTexture("resources/images/holz.jpg");
        textureMetal = TextureLoader.loadTexture("resources/images/gold2.png");
        textureSilver = TextureLoader.loadTexture("resources/images/silber.png");
        textureRed = TextureLoader.loadTexture("resources/images/red.png");
        textureGreen = TextureLoader.loadTexture("resources/images/green.png");
        textureBlack = TextureLoader.loadTexture("resources/images/black.png");

        int numberCount = 37; // means numbers from 0 to numberCount - 1
        textureNumbers = new Texture[numberCount];

        for (int i = 0; i < numberCount; i++) {
            textureNumbers[i] = TextureLoader.loadTexture("resources/images/nr_" + i + ".png");
        }

        // TODO Texturen f�r Zahlen neu -> ohne Rahmen
        // TODO goldene Textur f�r Spinner und Rauten
        // TODO Rauten einf�gen
    }

    // *******************************************
    public void show(GL gl, double curAngle) {
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
        textureMetal.bind();
        float mcolor[] = { 0.0f, 0.0f, 0.0f, 1.0f };
        float specReflection[] = { 0.8f, 0.8f, 0.8f, 1.0f };

        // gl.glMaterialfv( GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, mcolor, 0 );
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specReflection, 0);

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
        textureWood.bind();

        for (int i = 0; i
        < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * outerField_radius, outerField_outer_height, cos(angle * (i + 0)) * outerField_radius),
                    new Point3D(sin(angle * (i + 0)) * rollField_radius, outerCase_height, cos(angle * (i + 0)) * rollField_radius),
                    new Point3D(sin(angle * (i + 1)) * rollField_radius, outerCase_height, cos(angle * (i + 1)) * rollField_radius),
                    new Point3D(sin(angle * (i + 1)) * outerField_radius, outerField_outer_height, cos(angle * (i + 1)) * outerField_radius));
            drawQuad(new Point3D(sin(angle * (i + 0)) * rollField_radius, outerCase_height, cos(angle * (i + 0)) * rollField_radius),
                    new Point3D(0, 0, 0),
                    new Point3D(0, 0, 0),
                    new Point3D(sin(angle * (i + 1)) * rollField_radius, outerCase_height, cos(angle * (i + 1)) * rollField_radius));
        }
    }

    private void outerField() {
        textureWood.bind();

        for (int i = 0; i < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * rollField_radius, rollField_upper_height, cos(angle * (i + 0)) * rollField_radius),
                    new Point3D(sin(angle * (i + 0)) * rollField_radius, outerField_inner_height, cos(angle * (i + 0)) * rollField_radius),
                    new Point3D(sin(angle * (i + 1)) * rollField_radius, outerField_inner_height, cos(angle * (i + 1)) * rollField_radius),
                    new Point3D(sin(angle * (i + 1)) * rollField_radius, rollField_upper_height, cos(angle * (i + 1)) * rollField_radius));
            drawQuad(new Point3D(sin(angle * (i + 0)) * rollField_radius, outerField_inner_height, cos(angle * (i + 0)) * rollField_radius),
                    new Point3D(sin(angle * (i + 0)) * outerField_radius, outerField_outer_height, cos(angle * (i + 0)) * outerField_radius),
                    new Point3D(sin(angle * (i + 1)) * outerField_radius, outerField_outer_height, cos(angle * (i + 1)) * outerField_radius),
                    new Point3D(sin(angle * (i + 1)) * rollField_radius, outerField_inner_height, cos(angle * (i + 1)) * rollField_radius));
        }
    }

    private void rollField() {
        textureWood.bind();

        for (int i = 0; i < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * numField_radius, rollField_lower_height, cos(angle * (i + 0)) * numField_radius),
                    new Point3D(sin(angle * (i + 0)) * rollField_radius, rollField_upper_height, cos(angle * (i + 0)) * rollField_radius),
                    new Point3D(sin(angle * (i + 1)) * rollField_radius, rollField_upper_height, cos(angle * (i + 1)) * rollField_radius),
                    new Point3D(sin(angle * (i + 1)) * numField_radius, rollField_lower_height, cos(angle * (i + 1)) * numField_radius));
        }
    }

    private void numField() {
        for (int i = 0; i < parts; i++) {
            textureNumbers[rouletteKesselNumbers.get()[i]].bind();

            drawQuad(
                    new Point3D(sin(angle * (i + 0)) * layField_radius, layfield_height, cos(angle * (i + 0)) * layField_radius),
                    new Point3D(sin(angle * (i + 0)) * numField_radius, rollField_lower_height, cos(angle * (i + 0)) * numField_radius),
                    new Point3D(sin(angle * (i + 1)) * numField_radius, rollField_lower_height, cos(angle * (i + 1)) * numField_radius),
                    new Point3D(sin(angle * (i + 1)) * layField_radius, layfield_height, cos(angle * (i + 1)) * layField_radius));
        }
    }

    private void layField() {
        // TODO Layfield absenken
        // TODO silbernes Gitter herum
        for (int i = 0; i < parts; i++) {
            if (i == 0) {
                textureGreen.bind();
            } else {
                if (i % 2 != 0) {
                    textureBlack.bind();
                } else {
                    textureRed.bind();
                }
            }

            drawQuad(new Point3D(sin(angle * (i + 0)) * middleField_radius, layfield_height, cos(angle * (i + 0)) * middleField_radius),
                    new Point3D(sin(angle * (i + 0)) * layField_radius, layfield_height, cos(angle * (i + 0)) * layField_radius),
                    new Point3D(sin(angle * (i + 1)) * layField_radius, layfield_height, cos(angle * (i + 1)) * layField_radius),
                    new Point3D(sin(angle * (i + 1)) * middleField_radius, layfield_height, cos(angle * (i + 1)) * middleField_radius));

            // ***** Kleine Gitter um Layfield
            textureSilver.bind();

            double thickness = layField_cross_thickness;
            drawQuad(new Point3D(sin(angle * i + thickness) * middleField_radius, layfield_height + layField_cross_height, cos(angle * i + thickness)
                    * middleField_radius),
                    new Point3D(sin(angle * i + thickness) * layField_radius, layfield_height + layField_cross_height, cos(angle * i + thickness)
                            * layField_radius),
                    new Point3D(sin(angle * i - thickness) * layField_radius, layfield_height + layField_cross_height, cos(angle * i - thickness)
                            * layField_radius),
                    new Point3D(sin(angle * i - thickness) * middleField_radius, layfield_height + layField_cross_height, cos(angle * i - thickness)
                            * middleField_radius));

            thickness /= 4;
            drawQuad(new Point3D(sin(angle * (i + 0)) * (middleField_radius - thickness), layfield_height + layField_cross_height, cos(angle * (i + 0))
                    * (middleField_radius - thickness)),
                    new Point3D(sin(angle * (i + 0)) * (middleField_radius + thickness), layfield_height + layField_cross_height, cos(angle * (i + 0))
                            * (middleField_radius + thickness)),
                    new Point3D(sin(angle * (i + 1)) * (middleField_radius + thickness), layfield_height + layField_cross_height, cos(angle * (i + 1))
                            * (middleField_radius + thickness)),
                    new Point3D(sin(angle * (i + 1)) * (middleField_radius - thickness), layfield_height + layField_cross_height, cos(angle * (i + 1))
                            * (middleField_radius - thickness)));

            thickness /= 1.5;
            drawQuad(new Point3D(sin(angle * (i + 0)) * (layField_radius - thickness), layfield_height + layField_cross_height, cos(angle * (i + 0))
                    * (layField_radius - thickness)),
                    new Point3D(sin(angle * (i + 0)) * (layField_radius + thickness), layfield_height + layField_cross_height, cos(angle * (i + 0))
                            * (layField_radius + thickness)),
                    new Point3D(sin(angle * (i + 1)) * (layField_radius + thickness), layfield_height + layField_cross_height, cos(angle * (i + 1))
                            * (layField_radius + thickness)),
                    new Point3D(sin(angle * (i + 1)) * (layField_radius - thickness), layfield_height + layField_cross_height, cos(angle * (i + 1))
                            * (layField_radius - thickness)));
        }
    }

    private void middleField() {
        textureMetal.bind();

        for (int i = 0; i < parts; i++) {
            drawQuad(new Point3D(sin(angle * (i + 0)) * spinner_thick_radius, spinner_ground_height, cos(angle * (i + 0)) * spinner_thick_radius),
                    new Point3D(sin(angle * (i + 0)) * middleField_radius, layfield_height, cos(angle * (i + 0)) * middleField_radius),
                    new Point3D(sin(angle * (i + 1)) * middleField_radius, layfield_height, cos(angle * (i + 1)) * middleField_radius),
                    new Point3D(sin(angle * (i + 1)) * spinner_thick_radius, spinner_ground_height, cos(angle * (i + 1)) * spinner_thick_radius));
        }
    }

    // *******************************************
    private void drawQuad(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
        drawQuad(p1, p2, p3, p4, null);
    }

    private void drawQuad(Point3D p1, Point3D p2, Point3D p3, Point3D p4, Point3D normale) {
        gl.glBegin(GL.GL_QUADS); // Fange an Quadrate zu zeichnen
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
}
