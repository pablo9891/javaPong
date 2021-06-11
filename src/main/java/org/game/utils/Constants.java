package org.game.utils;

import java.awt.*;

public class Constants {

    private Constants() { }

    public static final String CONFIG_FILE = "application.properties";

    public static final String WINDOW_NAME = Configuration.
            getInstance().
            getStringProperty("window_name");

    public static final int WINDOW_WIDTH = Configuration.
            getInstance().
            getIntegerProperty("window_width");

    public static final int WINDOW_HEIGHT = Configuration.
            getInstance().
            getIntegerProperty("window_height");

    public static final boolean IS_VISIBLE_WINDOW = Configuration.
            getInstance().
            getBooleanProperty("is_visible_window");

    public static final boolean IS_RESIZABLE_WINDOW = Configuration.
            getInstance().
            getBooleanProperty("is_resizable_window");

    public static final boolean IS_FOCUSABLE_WINDOW = Configuration.
            getInstance().
            getBooleanProperty("is_focusable_window");

    public static final double BAR_WIDTH = Configuration.
            getInstance().
            getDoubleProperty("bar_width");

    public static final double BAR_HEIGHT = Configuration.
            getInstance().
            getDoubleProperty("bar_height");

    public static final double BALL_WIDTH = Configuration.
            getInstance().
            getDoubleProperty("ball_width");

    public static final double BALL_HEIGHT = Configuration.
            getInstance().
            getDoubleProperty("ball_height");

    public static final double LEFT_BAR_INITIAL_X = Configuration.
            getInstance().
            getDoubleProperty("left_bar_initial_x");
    public static final double LEFT_BAR_INITIAL_Y = Configuration.
            getInstance().
            getDoubleProperty("left_bar_initial_y");

    public static final double RIGHT_BAR_INITAL_X = Configuration.
            getInstance().
            getDoubleProperty("right_bar_initial_x");
    public static final double RIGHT_BAR_INITIAL_Y = Configuration.
            getInstance().
            getDoubleProperty("right_bar_initial_y");

    public static final double BALL_INITIAL_X = Configuration.
            getInstance().
            getDoubleProperty("ball_initial_x");

    public  static final double BALL_INITIAL_Y = Configuration.
            getInstance().
            getDoubleProperty("ball_initial_y");

    public static final double BAR_INITIAL_X_VEL = Configuration.
            getInstance().
            getDoubleProperty("bar_initial_vel_x");
    public static final double BAR_INITIAL_Y_VEL = Configuration.
            getInstance().
            getDoubleProperty("bar_initial_vel_y");

    public static final double BALL_INITIAL_X_VEL = Configuration.
            getInstance().
            getDoubleProperty("ball_initial_vel_x");
    public static final double BALL_INITIAL_Y_VEL = Configuration.
            getInstance().
            getDoubleProperty("ball_initial_vel_y");

    public static double TOP_BAR;

    public static double BOTTOM_BAR ;

    public static final double TOP_BAR_MARGIN=Configuration.
            getInstance().
            getDoubleProperty("top_bar_margin");

    public static final double MAX_BALL_VELOCITY = Configuration.
            getInstance().
            getDoubleProperty("max_ball_vel");

    public static final double MIN_BALL_VELOCITY = Configuration.
            getInstance().
            getDoubleProperty("min_ball_vel");

    public static final int MAX_POINTS = Configuration.
            getInstance().
            getIntegerProperty("max_points");

    public static final boolean IS_DEBUG_SET = Configuration.
            getInstance().
            getBooleanProperty("is_debug_set");

    public static final String ROOT_SOUND_FOLDER = Configuration.
            getInstance().
            getStringProperty("root_sound_folder");

    public static final int FPS = Configuration.
            getInstance().
            getIntegerProperty("fps");

    public static final double FRAME_DELAY = 1000d / Constants.FPS;

    private static final int BALL_R_COLOR = Configuration.getInstance().getIntegerProperty("ball_r_color");
    private static final int BALL_G_COLOR = Configuration.getInstance().getIntegerProperty("ball_g_color");
    private static final int BALL_B_COLOR = Configuration.getInstance().getIntegerProperty("ball_b_color");

    public static final Color BALL_COLOR = new Color(BALL_R_COLOR, BALL_G_COLOR, BALL_B_COLOR);

    private static final int BAR_R_COLOR = Configuration.getInstance().getIntegerProperty("bar_r_color");
    private static final int BAR_G_COLOR = Configuration.getInstance().getIntegerProperty("bar_g_color");
    private static final int BAR_B_COLOR = Configuration.getInstance().getIntegerProperty("bar_b_color");

    public static final Color BAR_COLOR = new Color(BAR_R_COLOR, BAR_G_COLOR, BAR_B_COLOR);

    private static final int BACKGROUND_R_COLOR = Configuration.getInstance().getIntegerProperty("background_r_color");
    private static final int BACKGROUND_G_COLOR = Configuration.getInstance().getIntegerProperty("background_g_color");
    private static final int BACKGROUND_B_COLOR = Configuration.getInstance().getIntegerProperty("background_b_color");

    public static final Color BACKGROUND_COLOR = new Color(BACKGROUND_R_COLOR, BACKGROUND_G_COLOR, BACKGROUND_B_COLOR);

    /**
     * Ball max angle in radians
     */
    public static final double MAX_ANGLE_WITH_SURFACE = Configuration.
            getInstance().
            getDoubleProperty("max_angle_in_radians");

    public static final double MAX_ANGLE_WITH_EDGES = Configuration.
            getInstance().
            getDoubleProperty("max_angle_in_radians_with_edges");
}