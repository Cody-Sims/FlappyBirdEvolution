package evolution;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static evolution.Constants.*;

public class Pipe {
    private Pane _root;
    private Pane _pipePane;
    private Rectangle[] _pipe;
    private int _pipeNumber;

    private int _score = 0;

    public Pipe(Pane root, int pipeNumber){
        _root = root;
        _pipePane = new Pane();
        _pipePane.setFocusTraversable(false);

        _root.getChildren().add(_pipePane);
        _pipeNumber = pipeNumber;

        double startingX = PIPE_STARTING_X + (PIPE_OFFSET_X * _pipeNumber);
        create(startingX);
    }

    /*
     * This class updates the pipes each time it is called. Pipes move every time it is called. If the pipe is off
     * screen it will be deleted and added to the back. It returns a 1 to add to the score if it moves the piece
     */
    public int update(){
        left();

        if (_pipe[0].getX() < -PIPE_WIDTH) {
            delete();
            create(1100);
            return 1;
        }
        return 0;
    }

    public double getX(){
        return _pipe[0].getX();
    }

    public double getY(){
        return _pipe[1].getY();
    }

    public Rectangle[] getNode() {
        return _pipe;
    }

    /*
     * Creates a pipe by generating two rectangles with the same width at the same X with a gap in between.
     */
    private void create(double x){
        // Generates a random Y that will be used to create a gap in the pipe
        int randomY = (int) ((Math.random()*400) + 50);
        //System.out.println(randomY);
        _pipe = new Rectangle[2];

        _pipe[0] = new Rectangle(x, 0, PIPE_WIDTH, randomY); // Upper Pipe
        _pipe[1] = new Rectangle(x, randomY + PIPE_GAP_HEIGHT, PIPE_WIDTH,
                SCREEN_HEIGHT - randomY - PIPE_GAP_HEIGHT); // Lower Pipe


        _pipe[0].setFill(Color.GREEN);
        _pipe[1].setFill(Color.GREEN);

        _pipe[0].setStrokeWidth(10);
        _pipe[1].setStrokeWidth(10);
        _pipe[0].setStroke(Color.DARKGREEN);
        _pipe[1].setStroke(Color.DARKGREEN);

        _pipePane.getChildren().addAll(_pipe);
        //System.out.println(_pipePane.getChildren());
    }

    //This moves the pipes left creating the effect of the bird moving
    private void left(){
        // I wasn't sure if using a for loop or just calling each individually would be more efficient coding.
        _pipe[0].setX(_pipe[0].getX() - PIPE_SPEED); // Changes the upper pipe's X
        _pipe[1].setX(_pipe[1].getX() - PIPE_SPEED); // Changes the lower pipe's X

    }

    //This deletes the pipe logically from the pane
    private void delete(){
        _root.getChildren().removeAll(_pipe[0],_pipe[1]);
    }



}
