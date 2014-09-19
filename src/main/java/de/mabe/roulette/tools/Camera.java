package de.mabe.roulette.tools;

import de.mabe.roulette.Debugger;
import de.mabe.roulette.model.MouseHandler;

public class Camera {
    Debugger debug;

    MouseHandler mouseHandler;
    public Camera(MouseHandler mouseHandler){
        debug = new Debugger(this);

        this.mouseHandler = mouseHandler;
    }
    public double getX(){
        double v = Math.sin( getAngle(this.mouseHandler.getRotationZ()) ) * this.mouseHandler.getDistance();
        return Math.cos( getAngle(this.mouseHandler.getRotationX()) ) * v;
    }
    public double getZ(){
        
        double v = Math.cos(getAngle(this.mouseHandler.getRotationZ())) * this.mouseHandler.getDistance();
        return Math.cos( getAngle(this.mouseHandler.getRotationX()) ) * v;
    }

    //höhe
    public double getY(){
        return Math.sin( getAngle(this.mouseHandler.getRotationX()) ) * this.mouseHandler.getDistance();
    }


    private double getAngle (int angle){
        return (double) angle / 180 * Math.PI;
    }
}
