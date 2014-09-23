/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.mabe.roulette.model;

import javax.media.opengl.GL2;

import de.mabe.roulette.core.VisualElement;

/**
 * 
 */
public class CoordinateCross extends VisualElement {
    @Override
    public void showInternal() {
        double CoordSysThickness = 0.2;
        double CoordSysLength = 100.0;

        boolean prevState = gl.glIsEnabled(GL2.GL_LIGHTING);
        gl.glDisable(GL2.GL_LIGHTING);

        gl.glPushMatrix();
        gl.glTranslated(position.x, position.y, position.z);

        gl.glPushMatrix(); // ***** X-Achse *****
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glScaled(CoordSysLength, CoordSysThickness, CoordSysThickness);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        gl.glPushMatrix(); // ***** Y-Achse ******
        gl.glColor3d(0.0, 1.0, 0.0);
        gl.glScaled(CoordSysThickness, CoordSysLength, CoordSysThickness);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        gl.glPushMatrix(); // ***** Z-Achse *****
        gl.glColor3d(0.0, 0.0, 1.0);
        gl.glScaled(CoordSysThickness, CoordSysThickness, CoordSysLength);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        gl.glPopMatrix();

        if (prevState) {
            gl.glEnable(GL2.GL_LIGHTING);
        }
    }
}
