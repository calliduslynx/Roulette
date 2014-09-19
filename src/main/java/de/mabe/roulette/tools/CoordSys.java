/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.mabe.roulette.tools;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;

/**
 *
 * @author drcox
 */
public class CoordSys{

    public static void show( GL gl ){
        GLUT glut = new GLUT();
        double CoordSysThickness = 0.2;
        double CoordSysLength = 100.0;

        boolean prevState = gl.glIsEnabled( gl.GL_LIGHTING );
        gl.glDisable( gl.GL_LIGHTING );


        gl.glPushMatrix();       //***** X-Achse *****
        gl.glColor3d( 1.0, 0.0, 0.0 );
        gl.glScaled( CoordSysLength, CoordSysThickness, CoordSysThickness );
        glut.glutSolidCube( 1 );
        gl.glPopMatrix();

        gl.glPushMatrix();       //***** Y-Achse ******
        gl.glColor3d( 0.0, 1.0, 0.0 );
        gl.glScaled( CoordSysThickness, CoordSysLength, CoordSysThickness );
        glut.glutSolidCube( 1 );
        gl.glPopMatrix();

        gl.glPushMatrix();       //***** Z-Achse *****
        gl.glColor3d( 0.0, 0.0, 1.0 );
        gl.glScaled( CoordSysThickness, CoordSysThickness, CoordSysLength );
        glut.glutSolidCube( 1 );
        gl.glPopMatrix();

        if ( prevState )
            gl.glEnable( GL.GL_LIGHTING );
    }

    public static void show( GL gl, double x, double y, double z ){
        gl.glPushMatrix();
        gl.glTranslated( x, y, z );
        CoordSys.show( gl );
        gl.glPopMatrix();
    }
}