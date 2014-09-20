package de.mabe.roulette.core.camera;

import javax.media.opengl.awt.GLCanvas;

import de.mabe.roulette.model.MouseAdapter;

public class MouseSinglePointCamera extends Camera {
    private int rotationX;
    private int rotationZ;
    private int distance;

    public MouseSinglePointCamera(GLCanvas canvas) {
        rotationX = 30;
        rotationZ = 30;
        distance = 100;

        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mouseDragged(int deltaX, int deltaY, boolean button1) {
                if (button1) {
                    rotationX -= deltaY;
                    rotationZ -= deltaX;

                    rotationX = rotationX % 360;
                    rotationZ = rotationZ % 360;
                } else {
                    distance -= deltaY;
                    if (distance < 0) {
                        distance = -distance;
                    }
                }

                calculatePosition();
            }

        };

        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);

        calculatePosition();
    }

    private void calculatePosition() {
        double v1 = Math.sin(getAngle(rotationZ)) * distance;
        double v2 = Math.cos(getAngle(rotationZ)) * distance;

        position.x = Math.cos(getAngle(rotationX)) * v1;
        position.y = Math.sin(getAngle(rotationX)) * distance;
        position.z = Math.cos(getAngle(rotationX)) * v2;
    }

    private double getAngle(int angle) {
        return (double) angle / 180 * Math.PI;
    }
}
