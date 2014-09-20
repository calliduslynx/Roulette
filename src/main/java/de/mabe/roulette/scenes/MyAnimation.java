package de.mabe.roulette.scenes;

import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import de.mabe.roulette.model.Ball;
import de.mabe.roulette.model.BallCalculator;
import de.mabe.roulette.model.MouseClickListener;
import de.mabe.roulette.model.MouseHandler;
import de.mabe.roulette.model.Point3D;
import de.mabe.roulette.model.RouletteKesselCalculator;
import de.mabe.roulette.model.kessel.Kessel;
import de.mabe.roulette.tools.AnimationObject;

public class MyAnimation extends AnimationObject implements MouseClickListener {
    private double angle;
    private Ball ball;
    private BallCalculator ballCalculator;
    private Kessel kessel;
    private RouletteKesselCalculator rouletteKesselCalculator;

    public MyAnimation(MouseHandler mouseHandler) {
        super(mouseHandler);
        mouseHandler.setMouseClickListener(this);
        reset();
    }

    @Override
    public void init() {
        GLU glu = new GLU();
        kessel = new Kessel(gl, glu);
        ball = new Ball();
        ball.applyGL(gl);

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

        gl = drawable.getGL().getGL2();

        // Bildschirm cleanen
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        setCamera();

        // ***** Vorbeitung
        gl.glMatrixMode(GL2.GL_MODELVIEW);
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
        kessel.show(gl, angle);

        Point3D p = ballCalculator.getBallPosition(curTime, -angle);
        ball.show(p);

        // ***** Aufräumen
        gl.glPopMatrix();
        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
