package de.mabe.roulette.scenes;

import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.media.opengl.GL2;
import javax.media.opengl.awt.GLCanvas;

import de.mabe.roulette.model.Ball;
import de.mabe.roulette.model.BallCalculator;
import de.mabe.roulette.model.MouseAdapter;
import de.mabe.roulette.model.Point3D;
import de.mabe.roulette.model.RouletteKesselCalculator;
import de.mabe.roulette.model.kessel.Kessel;

public class RollingBallScene implements Scene {
    private double angle;

    private BallCalculator ballCalculator;
    private RouletteKesselCalculator rouletteKesselCalculator;

    private Ball ball;
    private Kessel kessel;

    public RollingBallScene(GLCanvas canvas) {
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                reset();
            }
        });

        reset();
    }

    @Override
    public void init(GL2 gl) {
        kessel = new Kessel();
        kessel.applyGL(gl);

        ball = new Ball();
        ball.applyGL(gl);

        reset();
    }

    public final void reset() {
        rouletteKesselCalculator = new RouletteKesselCalculator(System.currentTimeMillis());
        ballCalculator = new BallCalculator(System.currentTimeMillis());
    }

    @Override
    public void display(GL2 gl) {
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
    }
}
