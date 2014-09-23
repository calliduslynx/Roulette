package de.mabe.roulette.scenes;

import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.media.opengl.awt.GLCanvas;

import de.mabe.roulette.model.Ball;
import de.mabe.roulette.model.Point3D;
import de.mabe.roulette.model.kessel.Kessel;
import de.mabe.roulette.util.MouseAdapter;

public class RollingBallScene extends Scene {
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
    public void internalInit() {
        kessel = new Kessel();
        ball = new Ball();

        addVisualElements(kessel, ball);

        reset();
    }

    public final void reset() {
        rouletteKesselCalculator = new RouletteKesselCalculator(System.currentTimeMillis());
        ballCalculator = new BallCalculator(System.currentTimeMillis());
    }

    @Override
    public void internalDisplay() {
        // Drehung berechnen
        long curTime = Calendar.getInstance().getTimeInMillis();
        /*
         * double diffTime = curTime - this.lastFrame; this.speed -= (double) diffTime / 3000; if (this.speed < 0) { this.speed = 0; } this.angle += ((double)
         * (diffTime) / 1000) * 360 * speed; this.angle = this.angle % 360; this.lastFrame = curTime;
         */

        angle = rouletteKesselCalculator.getRotation(curTime);
        kessel.setAngle(angle);

        Point3D p = ballCalculator.getBallPosition(curTime, -angle);
        ball.setPosition(p);
    }
}
