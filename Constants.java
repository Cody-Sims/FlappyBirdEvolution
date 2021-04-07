package evolution;

public class Constants {
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 800;

    public static final int GRAVITY = 1200; // acceleration constant (UNITS: pixels/s^2)
    public static final int REBOUND_VELOCITY = -350; // initial jump velocity (UNITS: pixels/s)
    public static final double DURATION = 0.016; // KeyFrame duration (UNITS: s)

    public static final double PIPE_STARTING_X = SCREEN_WIDTH * 1.5;
    public static final int PIPE_WIDTH = SCREEN_WIDTH / 15;
    public static final int PIPE_GAP_HEIGHT = SCREEN_HEIGHT / 4;
    public static final int PIPE_SPEED = 5;

    public static final double PIPE_OFFSET_X = SCREEN_WIDTH / 1.5;


    public static final int BIRD_SIZE = SCREEN_HEIGHT / 30;
    public static final int BIRD_STARTING_X = 100 + BIRD_SIZE;
    public static final int BIRD_STARTING_Y = SCREEN_HEIGHT / 2;



}
