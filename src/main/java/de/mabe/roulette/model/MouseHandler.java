package de.mabe.roulette.model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.mabe.roulette.Debugger;

public class MouseHandler implements MouseMotionListener, MouseListener {

    Debugger debug;
    MouseClickListener clickListener = null;
    private int cordX;
    private int cordY;
    private int rotationX;
    private int rotationZ;
    private int distance;
    private boolean button1;

    public MouseHandler() {
        debug = new Debugger(this);

        rotationX = 30;
        rotationZ = 30;
        distance = 100;
    }

    public void setMouseClickListener(MouseClickListener listener) {
        clickListener = listener;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int currCordX = e.getX();
        int currCordY = e.getY();

        if (button1) {
            rotationX -= cordY - currCordY;
            rotationZ -= cordX - currCordX;

            rotationX = rotationX % 360;
            rotationZ = rotationZ % 360;
        } else {
            distance -= cordY - currCordY;
            if (distance < 0) {
                distance = -distance;
            }
        }

        cordX = currCordX;
        cordY = currCordY;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // debug.out("mouse moved");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        debug.out("mouse clicked");
        if (clickListener != null) {
            clickListener.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        debug.out("mouse pressed");
        cordX = e.getX();
        cordY = e.getY();

        button1 = (e.getButton() == MouseEvent.BUTTON1) ? true : false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        debug.out("mouse released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        debug.out("mouse entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        debug.out("mouse exited");
    }

    /**
     * rotation nach oben
     */
    public int getRotationX() {
        return rotationX;
    }

    /**
     * drehung zur seite
     * 
     * @return
     */
    public int getRotationZ() {
        return rotationZ;
    }

    /**
     * gibt die entfernung zum mittelpunkt zurück
     */
    public int getDistance() {
        return distance;
    }
}