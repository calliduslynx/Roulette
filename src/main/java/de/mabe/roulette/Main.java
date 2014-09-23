/***
 * x_TODO Texturen finden, die nach was aussehen
 * x_TODO Texturen f�r die Zahlen erstellen
 * x_TODO M�glichkeit finden Zahlen zu ordnen
 * x_TODO Texturen der Zahlen aufbringen
 * TODO Bessere Berechnung f�r die Drehung finden
 * TODO Kugel �berlegen und umsetzen
 */

package de.mabe.roulette;

import javax.media.opengl.awt.GLCanvas;

import de.mabe.roulette.core.SceneManager;
import de.mabe.roulette.core.camera.Camera;
import de.mabe.roulette.core.camera.MouseSinglePointCamera;
import de.mabe.roulette.scenes.RollingBallScene;
import de.mabe.roulette.scenes.Scene;
import de.mabe.roulette.util.GLUtil;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        GLCanvas canvas = GLUtil.getCanvasInDefaultFrame("Roulette");

        Camera camera = new MouseSinglePointCamera(canvas);
        Scene scene = new RollingBallScene(canvas);

        new SceneManager(scene, camera, canvas);
    }
}
