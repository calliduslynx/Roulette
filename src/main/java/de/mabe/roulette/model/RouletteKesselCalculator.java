package de.mabe.roulette.model;

public class RouletteKesselCalculator {
    int phasesCount = 3;
    double phaseTime1 = 1500.0;
    double phaseTime2 = 0300.0;
    double phaseTime3 = 9000.0;
    private Phase[] phases;
    double maxSpeed = 3.0 * 360 / 1000;
    double decay = 1;

    /***
     * Zeit zu der die Berechnung gestartet wurde
     */
    private long startTime;
    /***
     * gibt die aktuelle Phase an
     */
    int currentPhase;

    public RouletteKesselCalculator(long startTime) {
        this.startTime = startTime;
        currentPhase = 0;

        initPhases();

        preCalculate();
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
        double returnValue = phases[currentPhase].degreesBeforePhase
                + phases[currentPhase].getRotation(calcTime);

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
