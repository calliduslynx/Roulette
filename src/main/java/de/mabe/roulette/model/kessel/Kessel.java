package de.mabe.roulette.model.kessel;

import javax.media.opengl.GL2;

import de.mabe.roulette.core.VisualElement;

public class Kessel extends VisualElement implements RouletteKesselProperties {
    private double curAngle;

    private NumberField numberField;
    private LayField layField;
    private Spinner spinner;
    private FieldBetweenSpinnerAndLayField fieldBetweenSpinnerAndLayField;
    private RollField rollField;
    private OuterBoxTop outerBoxTop;
    private OuterBox outerBox;

    public Kessel() {
        numberField = new NumberField();
        layField = new LayField();
        spinner = new Spinner();
        fieldBetweenSpinnerAndLayField = new FieldBetweenSpinnerAndLayField();
        rollField = new RollField();
        outerBoxTop = new OuterBoxTop();
        outerBox = new OuterBox();
    }

    @Override
    public void applyGL(GL2 gl) {
        super.applyGL(gl);

        numberField.applyGL(gl);
        layField.applyGL(gl);
        spinner.applyGL(gl);
        fieldBetweenSpinnerAndLayField.applyGL(gl);
        rollField.applyGL(gl);
        outerBoxTop.applyGL(gl);
        outerBox.applyGL(gl);
    }

    @Override
    public void showInternal() {
        // outerBox.show();
        outerBoxTop.show();
        rollField.show();

        gl.glRotated(curAngle, 0, 1, 0);

        numberField.show();
        layField.show();
        spinner.show();
        fieldBetweenSpinnerAndLayField.show();
    }

    public void setAngle(double curAngle) {
        this.curAngle = curAngle;
    }
}
