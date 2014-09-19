/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.mabe.roulette.tools;

/**
 *
 * @author drcox
 */
public class Point3D {
    public double x;
    public double y;
    public double z;
    public Point3D( double x, double y, double z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point3D() {
        this( 0, 0, 0 );
    }
    @Override
    public String toString() {
        return "Point3D [ " + this.x + " | " + this.y + " | " + this.z + " ]";
    }
}
