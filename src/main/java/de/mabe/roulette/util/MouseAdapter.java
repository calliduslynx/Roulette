package de.mabe.roulette.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseAdapter implements MouseMotionListener, MouseListener {
    private int cordX;
    private int cordY;
    private boolean button1;

    @Override
    public void mouseDragged(MouseEvent e) {
        int currCordX = e.getX();
        int currCordY = e.getY();

        mouseDragged(cordX - currCordX, cordY - currCordY, button1);

        cordX = currCordX;
        cordY = currCordY;
    }

    public void mouseDragged(int deltaX, int deltaY, boolean button1) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        cordX = e.getX();
        cordY = e.getY();

        button1 = (e.getButton() == MouseEvent.BUTTON1) ? true : false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
