package de.mabe.roulette.model;

import java.text.DecimalFormat;

import de.mabe.roulette.Debugger;

public class RouletteKesselCalculator {

    Debugger debug;
    // <editor-fold desc="constants">
    int phasesCount = 3;
    double phaseTime1 = 1500.0;
    double phaseTime2 = 0300.0;
    double phaseTime3 = 9000.0;
    private Phase[] phases;
    double maxSpeed = 3.0 * 360 / 1000;
    double decay = 1;
    // </editor-fold>
    /***
     * Zeit zu der die Berechnung gestartet wurde
     */
    private long startTime;
    /***
     * gibt die aktuelle Phase an
     */
    int currentPhase;

    public RouletteKesselCalculator(long startTime) {
        debug = new Debugger(this);
        this.startTime = startTime;
        currentPhase = 0;

        initPhases();

        preCalculate();

        debug();

    }

    public double getRotation(double currTime) {
        currTime -= startTime;
        while (currTime > phases[currentPhase].timeAfterPhase) {
            if (currentPhase >= phasesCount - 1) {
                return phases[phasesCount - 1].degreesAfterPhase;
            } else {
                currentPhase++;
            }
        }

        double calcTime = currTime - phases[currentPhase].timeBeforePhase;
        debug.out("calcTime:" + calcTime + Debugger.NNL);
        double returnValue = phases[currentPhase].degreesBeforePhase
                + phases[currentPhase].getRotation(calcTime);

        DecimalFormat f = new DecimalFormat("#00000");
        DecimalFormat f2 = new DecimalFormat("#0.0");

        debug.out("[" + f.format(currTime) + "] "
                + f.format(returnValue)
                + " (" + f2.format(returnValue / 360 * 100) + "%)      (#" + currentPhase + ")");

        returnValue = returnValue % 360;

        return returnValue;
    }

    private void initPhases() {
        phases = new Phase[phasesCount];

        for (int i = 0; i < phasesCount; i++) {
            switch (i) {
            case 0:// ******** Phase 1 ********
                phases[i] = new Phase(phaseTime1) {

                    @Override
                    double calculateRotation(double currentTimeInPhase) {
                        double rotation = (currentTimeInPhase * maxSpeed) / timeInPhase;
                        rotation = rotation * currentTimeInPhase / 2.0;
                        return rotation;
                    }
                };
                break;

            case 1:// ******** Phase 2 ********
                phases[i] = new Phase(phaseTime2) {

                    @Override
                    double calculateRotation(double currentTimeInPhase) {
                        double rotation = currentTimeInPhase * maxSpeed;
                        return rotation;
                    }
                };
                break;

            case 2:// ******** Phase 3 ********
                phases[i] = new Phase(phaseTime3) {

                    @Override
                    double calculateRotation(double currentTimeInPhase) {
                        double rotation = (maxSpeed * currentTimeInPhase *
                                (2 + decay - 2 * Math.pow(currentTimeInPhase / timeInPhase, decay / 2)))
                                / (2 + decay);

                        return rotation;
                    }
                };
                break;

            }

        }
    }

    public void debug() {
        debug.out("debugging");
        debug.out("phases counter: " + phasesCount);
        for (int i = 0; i < phasesCount; i++) {
            Phase p = phases[i];
            debug.out("phase #" + (i + 1));
            debug.out("  timeBeforePhase: " + p.timeBeforePhase);
            debug.out("  timeInPhase: " + p.timeInPhase);
            debug.out("  timeAfterPhase: " + p.timeAfterPhase);
            debug.out("  degreesBeforePhase: " + p.degreesBeforePhase);
            debug.out("  degreesInPhase: " + p.degreesInPhase);
            debug.out("  degreesAfterPhase: " + p.degreesAfterPhase);

        }
    }

    private void preCalculate() {
        double preDegrees = 0.0;
        double accumulatedTime = 0.0;

        for (int i = 0; i < phasesCount; i++) {
            Phase p = phases[i];

            p.degreesBeforePhase = preDegrees;
            p.degreesInPhase = p.calculateRotation(p.timeInPhase);
            preDegrees += p.degreesInPhase;
            p.degreesAfterPhase = preDegrees;

            phases[i].timeBeforePhase = accumulatedTime;
            accumulatedTime += phases[i].timeInPhase;
            phases[i].timeAfterPhase = accumulatedTime;
        }
    }

    // ******************************************************************************
    // ******************************************************************************
    // ******************************************************************************
    abstract class Phase {

        Phase(double time) {
            timeInPhase = time;
        }

        double timeInPhase;
        double degreesAfterPhase;
        double degreesBeforePhase;
        double degreesInPhase;
        double timeAfterPhase;
        double timeBeforePhase;

        /***
         * liefert den Wert der aktuellen Rotation zur�ck, mithilfe der Berechnung welche in dieser Phase vorliegt
         * 
         * @param timeInPhase
         * @return
         */
        double getRotation(double timeInPhase) {
            return calculateRotation(timeInPhase);
        }

        /***
         * abstrakte Funktion, welche nur intern aufgerufen wird
         * 
         * @param timeInPhase
         * @return
         */
        abstract double calculateRotation(double timeInPhase);
    }
}
