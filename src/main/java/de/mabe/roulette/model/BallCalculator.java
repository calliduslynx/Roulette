package de.mabe.roulette.model;

import java.text.DecimalFormat;

import de.mabe.roulette.tools.Point3D;

public class BallCalculator {
    /**/DecimalFormat f1 = new DecimalFormat("#00.000");
    /**/DecimalFormat f2 = new DecimalFormat("#0.0");
    // <editor-fold desc="calculation values">
    // *******************************************
    // ***** Vorgaben
    // *******************************************
    // achtung: folgende Werte nur nach vorheriger �berpr�fung in der Excel-Tabelle ver�ndern
    /***
     * Startgeschwindigkeit in Umdrehungen pro Millisekunde
     */
    public double startSpeed = 1.0 * 360 / 1000; // <- variabel !!
    /***
     * Geschwindigkeit ab der die Kugel beginnt nach innen zu Rollen
     */
    public double fallDownSpeed = 0.5 * 360 / 1000;
    /***
     * D�mpfung der Kugel in Speed pro Sekunde
     */
    public double roundAcc = 0.05 / 1000;
    /***
     * Beschleunigung nach innen
     */
    public double innerAcc = 10.0 / 1000000;
    /***
     * gibt die Gr^�e des Balles an
     */
    double ballSize = 1.5;
    /***
     * gibt den Wert an bei dem ...
     */
    double startKesselRotation = 1;
    // *******************************************
    // ***** Berechnete Werte
    // *******************************************
    /***
     * anf�ngliche Distanz der Kugel
     */
    public double startDistance = 40 - ballSize; // TODO distance Wert berechnen
    /***
     * Distanz bei der die Kugel zu springen beginnt
     */
    public double endDistance = 20; // TODO zu berechnen
    /***
     * H^he in der die Kugel zu rollen beginnt
     */
    public double startHeight = 15 + ballSize; // TODO berechnen
    /***
     * Hoehe auf der die Kugel liegen bleibt
     */
    public double endHeight = 5 + ballSize; // TODO berechnen
    // *******************************************
    // ***** Statuswerte
    // *******************************************
    private int currentPhase = 0;
    private Phase[] phases;
    private double startTime;

    // </editor-fold>
    public BallCalculator(long startTime) {
        this.startTime = startTime;
        phases = initPhases();
        precalculate();
    }

    public Point3D getBallPosition(double currTime, double kesselRotation) {

        currTime = currTime - startTime;
        // ***** Kugelflug verlangsamen
        // currTime /= 3.0;

        // *******************************************
        // ***** aktuelle Phase bestimmen
        // *******************************************
        while (currTime > phases[currentPhase].time.afterPhase) {
            if (currentPhase >= phases.length - 1) {
                /***
                 * TODO Position nach Ende
                 */
                return new Point3D(0, 35, 0);
            } else {
                currentPhase++;
            }
        }

        /**/String d = "-" + currentPhase + "- [" + f1.format(currTime) + "ms / ";
        currTime -= phases[currentPhase].time.beforePhase;
        /**/d += f1.format(currTime) + "ms] ";

        // *******************************************
        // ***** Berechnungen
        // *******************************************
        double rotation = phases[currentPhase].getRotation(currTime, kesselRotation)
                + phases[currentPhase].rotation.beforePhase;
        rotation %= 360;
        double distance = phases[currentPhase].getDistance(currTime);

        /**/d += "r(" + f1.format(rotation) + ") ";
        /**/d += "d(" + f2.format(distance) + ") ";

        // ***** vorhergegangene Phasen beachten

        // rotation = 180;

        Point3D p = new Point3D();

        p.x = cos(rotation) * distance;
        p.z = sin(rotation) * distance;
        p.y = (distance - startDistance) / (endDistance - startDistance) * (endHeight - startHeight) + startHeight;

        /**/d += p;
        return p;
    }

    private void precalculate() {
        double preDegrees = 0.0; // TODO = startWinkel
        double accumulatedTime = 0.0;
        double preDistance = 0.0;

        for (int i = 0; i < phases.length; i++) {
            Phase p = phases[i];

            // ***** rotation
            p.rotation.beforePhase = preDegrees;
            p.rotation.inPhase = p.calculateRotation(p.time.inPhase, 0);
            preDegrees += p.rotation.inPhase;
            p.rotation.afterPhase = preDegrees;

            // ***** distance
            // /TODO dr�ber nachdenken, wenn wach :/
            p.distance.beforePhase = preDistance;
            p.distance.inPhase = p.calculateDistance(p.time.inPhase);
            preDistance += p.distance.inPhase;
            p.distance.afterPhase = preDistance;

            // ***** time
            p.time.beforePhase = accumulatedTime;
            accumulatedTime += phases[i].time.inPhase;
            phases[i].time.afterPhase = accumulatedTime;
        }
    }

    private Phase[] initPhases() {
        int phaseCounter = 3;
        Phase[] phases = new Phase[phaseCounter];
        Phase phase = null;
        for (int i = 0; i < phaseCounter; i++) {
            switch (i) {
            // *******************************************
            case 0:
                double timeInPhase0 = (startSpeed - fallDownSpeed) / roundAcc;
                phase = new Phase(timeInPhase0) {
                    @Override
                    double calculateRotation(double timeInPhase, double kesselRotation) {
                        // debug.out("" + startSpeed + "*" + timeInPhase+ " - (" + roundAcc +"*"+timeInPhase+"*"+timeInPhase+"/2)");
                        return startSpeed * timeInPhase - (roundAcc * timeInPhase * timeInPhase / 2);
                    }

                    @Override
                    double calculateDistance(double timeInPhase) {
                        return startDistance;
                    }

                    @Override
                    double calculateHeight(double timeInPhase) {
                        return 0;
                    }
                };
                break;
            // *******************************************
            case 1:
                double timeInPhase1 = Math.sqrt(2.0 * (startDistance - endDistance) / innerAcc);
                phase = new Phase(timeInPhase1) {
                    @Override
                    double calculateRotation(double timeInPhase, double kesselRotation) {
                        return fallDownSpeed * timeInPhase - (roundAcc * timeInPhase * timeInPhase / 4);
                    }

                    @Override
                    double calculateDistance(double timeInPhase) {
                        return startDistance - innerAcc * timeInPhase * timeInPhase / 2;
                    }

                    @Override
                    double calculateHeight(double timeInPhase) {
                        return 0;
                    }
                };
                break;
            case 2:
                phase = new Phase(10000) {
                    @Override
                    double calculateRotation(double timeInPhase, double kesselRotation) {
                        if (startKesselRotation > 0 && kesselRotation != 0) {
                            startKesselRotation = kesselRotation;
                        }
                        return kesselRotation - startKesselRotation;
                    }

                    @Override
                    double calculateDistance(double timeInPhase) {
                        return endDistance;
                    }

                    @Override
                    double calculateHeight(double timeInPhase) {
                        return 0;
                    }
                };

            }
            phases[i] = phase;
        }
        return phases;
    }

    // *******************************************
    private double sin(double angle) {
        return Math.sin(getAngle(angle));
    }

    private double cos(double angle) {
        return Math.cos(getAngle(angle));
    }

    private double getAngle(double angle) {
        return angle / 180 * Math.PI;
    }
}

// ******************************************************************************
// ******************************************************************************
// ******************************************************************************
abstract class Phase {
    /**/DecimalFormat f1 = new DecimalFormat("#00.000");
    /**/DecimalFormat f2 = new DecimalFormat("#0.0");

    class PhaseMember {
        double inPhase;
        double afterPhase;
        double beforePhase;
    }

    PhaseMember time;
    PhaseMember rotation;
    PhaseMember distance;

    // *******************************************
    Phase(double timeInPhase) {
        time = new PhaseMember();
        rotation = new PhaseMember();
        distance = new PhaseMember();
        time.inPhase = timeInPhase;
    }

    /***
     * liefert den Wert der aktuellen Rotation zur�ck, mithilfe der Berechnung welche in dieser Phase vorliegt
     * 
     * @param timeInPhase
     * @return
     */
    double getRotation(double timeInPhase, double kesselRotation) {
        return calculateRotation(timeInPhase, kesselRotation);
    }

    /***
     * gibt die Distanz zum Mittelpunkt zur�ck
     * 
     * @param timeInPhase
     * @return
     */
    double getDistance(double timeInPhase) {
        return calculateDistance(timeInPhase);
    }

    /***
     * gibt die Hoehe zur�ck, die ZUS�TZLICH hinzukommt
     */
    double getHeight(double timeInPhase) {
        return calculateHeight(timeInPhase);
    }

    /***
     * abstrakte Funktion, welche nur intern aufgerufen wird
     * 
     * @param timeInPhase
     * @return
     */
    abstract double calculateRotation(double timeInPhase, double kesselRotation);

    /***
     * abstrakte Funktion, welche nur intern aufgerufen wird
     * 
     * @param timeInPhase
     * @return
     */
    abstract double calculateDistance(double timeInPhase);

    /***
     * abstrakte Funktion, welche nur intern aufgerufen wird
     * 
     * @param timeInPhase
     * @return
     */
    abstract double calculateHeight(double timeInPhase);

    @Override
    public String toString() {

        String str = " debugging Phase:\n"
                + "Time: b: " + f2.format(time.beforePhase) + " i: " + f2.format(time.inPhase) + " a: " + f2.format(time.afterPhase) + "\n"
                + "Rotation: b: " + f2.format(rotation.beforePhase) + "� i: " + f2.format(rotation.inPhase) + "� a: " + f2.format(rotation.afterPhase) + "�\n"
                + "Distance: b: " + f2.format(distance.beforePhase) + " i: " + f2.format(distance.inPhase) + " a: " + f2.format(distance.afterPhase) + "\n";

        return str;
    }
}
