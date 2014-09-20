package de.mabe.roulette.core.camera;

import de.mabe.roulette.model.Point3D;

public class Camera {
    protected Point3D position;
    protected Point3D target;
    protected Point3D up;

    public Camera() {
        position = new Point3D(10, 10, 10);
        target = new Point3D(0, 0, 0);
        up = new Point3D(0, 1, 0);
    }

    public Point3D getPosition() {
        return position;
    }

    public Point3D getTarget() {
        return target;
    }

    public Point3D getUp() {
        return up;
    }
}
