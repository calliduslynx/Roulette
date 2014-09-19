/***
 * x_TODO Texturen finden, die nach was aussehen
 * x_TODO Texturen f�r die Zahlen erstellen
 * x_TODO M�glichkeit finden Zahlen zu ordnen
 * x_TODO Texturen der Zahlen aufbringen
 * TODO Bessere Berechnung f�r die Drehung finden
 * TODO Kugel �berlegen und umsetzen
 */

package de.mabe.roulette;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.Animator;

import de.mabe.roulette.model.MouseHandler;

public class Main {
    Debugger debug;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        debug = new Debugger(this);
        debug.out("starte");

        // **********************************************
        // * Fenster erstellen und Einstellungen machen *
        // **********************************************
        // Fenster erstellen und gleichzeitig Titel setzen

        Frame frame = new Frame("Simple JOGL Application");
        // Position setzen
        frame.setLocation(0, 0);
        // Größe des Fensters setzen
        frame.setSize(800, 600);
        // Hintergrundfarbe setzen
        frame.setBackground(Color.WHITE);

        // ********************
        // * Canvas erstellen *
        // ********************
        // Canvas stellt das eigentlich angezeigte Display dar

        // Einstellungsmodul erstellen
        GLCapabilities capabilities = new GLCapabilities(null);
        // einige Einstellungen
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);

        // stellt Fullscreen Antialiasing an
        capabilities.setDoubleBuffered(true);
        capabilities.setStencilBits(1);
        capabilities.setSampleBuffers(true);
        capabilities.setNumSamples(8); // <-- für 8-fach Sampling
        // capabilities.setNumSamples(4);

        // Hardwarebeschleuning an!
        capabilities.setHardwareAccelerated(true);
        // eigentliches Canvas erstellen
        GLCanvas canvas = null;

        MouseHandler mouseHandler = new MouseHandler();
        MyAnimation myAnimation = new MyAnimation(mouseHandler);

        // canvas.addGLEventListener(myAnimation);

        // Maus initialisieren
        canvas.addMouseListener(mouseHandler);
        canvas.addMouseMotionListener(mouseHandler);

        // Canvas meinem Frame hinzufügen
        frame.add(canvas);

        /**********************
         * Animator erstellen *
         **********************/
        final Animator animator = new Animator(canvas);

        // fügt eine Routine hinzu, die dafür sorgt, dass die Animation nach dem
        // schließen des Fensters auch beendet wird
        frame.addWindowListener(
                new WindowAdapter() {
                    // new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // Run this on another thread than the AWT event queue to
                        // make sure the call to Animator.stop() completes before
                        // exiting
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                animator.stop();
                                System.exit(0);
                            }
                        }).start();
                    }
                }
                );

        // hiermit wird gestartet, dass immer wieder die display-Methode aufgerufen
        // wird wenn ein wenig Zeit zwischen ist
        animator.start();

        // Fenster anzeigen, muss NACH frame.add kommen
        frame.setVisible(true);
    }
}
