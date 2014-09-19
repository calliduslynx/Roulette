package de.mabe.roulette;

import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import de.mabe.roulette.model.Ball;
import de.mabe.roulette.model.BallCalculator;
import de.mabe.roulette.model.MouseClickListener;
import de.mabe.roulette.model.MouseHandler;
import de.mabe.roulette.model.RouletteKessel;
import de.mabe.roulette.model.RouletteKesselCalculator;
import de.mabe.roulette.tools.AnimationObject;
import de.mabe.roulette.tools.Point3D;

public class MyAnimation extends AnimationObject implements MouseClickListener {
    private double angle;
    private Ball ball;
    private BallCalculator ballCalculator;
    private RouletteKessel rouletteKessel;
    private RouletteKesselCalculator rouletteKesselCalculator;

    public MyAnimation(MouseHandler mouseHandler) {
        super(mouseHandler);
        mouseHandler.setMouseClickListener(this);
        reset();
    }

    @Override
    public void init() {
        rouletteKessel = new RouletteKessel(gl, glu);
        ball = new Ball(gl, glu);

        reset();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        reset();
    }

    public final void reset() {
        rouletteKesselCalculator = new RouletteKesselCalculator(System.currentTimeMillis());
        ballCalculator = new BallCalculator(System.currentTimeMillis());
    }

    /***
     * wird bei jedem Neuzeichnen aufgerufen bspw. bei vom Animator
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        countFrame(); // scounting frames in console

        gl = drawable.getGL();

        // Bildschirm cleanen
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        setCamera();

        // ***** Vorbeitung
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity(); // ??

        // ***** eigentliche Anzeige setzen
        gl.glPushMatrix();

        // Drehung berechnen
        long curTime = Calendar.getInstance().getTimeInMillis();
        /*
         * double diffTime = curTime - this.lastFrame; this.speed -= (double) diffTime / 3000; if (this.speed < 0) { this.speed = 0; } this.angle += ((double)
         * (diffTime) / 1000) * 360 * speed; this.angle = this.angle % 360; this.lastFrame = curTime;
         */

        angle = rouletteKesselCalculator.getRotation(curTime);
        // Koordinatensystem anzeigen
        // CoordSys.show( gl );

        // RouletteKessel anzeigen
        rouletteKessel.show(gl, angle);

        Point3D p = ballCalculator.getBallPosition(curTime, -angle);
        ball.show(gl, p);

        // ***** Aufräumen
        gl.glPopMatrix();
        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
