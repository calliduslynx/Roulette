package de.mabe.roulette.model;

public interface RouletteKesselParam {
    public int parts = 37;
    public double angle = 360.0 / parts;

    public double radius_middleField = 20;
    public double radius_layField = 25;
    public double radius_numField = 30;
    public double radius_rollField = 40;
    public double radius_outerField = 50;
    public double radius_outerCase = 45;

    public double height_layField = 5;
    public double height_layField_cross = 0.1;
    public double height_rollField_lower = 5;
    public double height_rollField_upper = 8;
    public double height_outerField_inner = 15;
    public double height_outerField_outer = 20;
    public double height_outerCase = 18;

    public int spinner_parts = 8;
    public double spinner_angle = 360.0 / spinner_parts;
    public double spinner_thick_radius = 3;
    public double spinner_shaft_radius = 1;
    public double spinner_crown_lower_radius = 1;
    public double spinner_crown_upper_radius = 2;
    public double spinner_ground_height = 10;
    public double spinner_bottom_height = spinner_ground_height + 3;
    public double spinner_shaft_height = spinner_bottom_height + 13;
    public double spinner_cone_height = spinner_shaft_height + 3.0;
    public double spinner_crown_height = spinner_cone_height + 2.0;

    public int spinner_arrow_count = 4;
    public double spinner_arrow_angle = 360.0 / spinner_arrow_count;
    public double spinner_arrow_height = (spinner_cone_height - spinner_shaft_height) / 2 + spinner_shaft_height;
    public double spinner_arrow_length = 20;
    public double spinner_arrow_begin_radius = 1;
    public double spinner_arrow_end_radius = 0.5;
    public double spinner_arrow_ball_middle = spinner_arrow_length + 1.5;
    public double spinner_arrow_ball_length = spinner_arrow_ball_middle + 1.5;
    public double spinner_arrow_ball_radius = 1;

    public double layfieldcross_thickness = 0.5;
}
