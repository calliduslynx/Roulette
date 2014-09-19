package de.mabe.roulette.tools;

import de.mabe.roulette.model.MouseHandler;

public class Camera {
    MouseHandler mouseHandler;

    public Camera(MouseHandler mouseHandler) {
        this.mouseHandler = mouseHandler;
    }

    public double getX() {
        double v = Math.sin(getAngle(mouseHandler.getRotationZ())) * mouseHandler.getDistance();
        return Math.cos(getAngle(mouseHandler.getRotationX())) * v;
    }

    public double getZ() {
        double v = Math.cos(getAngle(mouseHandler.getRotationZ())) * mouseHandler.getDistance();
        return Math.cos(getAngle(mouseHandler.getRotationX())) * v;
    }

    // höhe
    public double getY() {
        return Math.sin(getAngle(mouseHandler.getRotationX())) * mouseHandler.getDistance();
    }

    private double getAngle(int angle) {
        return (double) angle / 180 * Math.PI;
    }
}
