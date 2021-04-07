package evolution;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static evolution.Constants.*;

public class Bird {

    private Pane _root;
    private Pane _birdPane;
    public Circle _bird;

    private double _currentVelocity = 0;
    private int _fitness = 0;

    private double _xDistanceFromPipe;
    private double _yDistanceFromPipe;

    private boolean _isAlive = true;

    private NeuralNetwork _neuralNetwork;

    public Bird(Pane root){
        _root = root;
        _birdPane = new Pane();
        _root.getChildren().add(_birdPane);

        _neuralNetwork = new NeuralNetwork(this); //Gives this bird a neural network.

        create();

    }

    // This creates / draws the bird
    public void create(){
        _bird = new Circle(BIRD_STARTING_X,BIRD_STARTING_Y , BIRD_SIZE);
        _bird.setFill(Color.YELLOW);
        _birdPane.getChildren().add(_bird);
    }

    /*
     * This method updates the bird's Y position by applying gravity, updates its fitness, and sets up screen borders
     */
    public void update(){
        if(_isAlive) {
            _fitness++; // increases the bird's fitness each cycle
            double currentYPosition = _bird.getCenterY();

            //Sets new velocity and Position
            double updatedVelocity = _currentVelocity + GRAVITY * DURATION;
            double updatedYPosition = currentYPosition + updatedVelocity * DURATION;

            if (updatedYPosition < BIRD_SIZE) {
                updatedYPosition = BIRD_SIZE;
            }

            _bird.setCenterY(updatedYPosition);
            _currentVelocity = updatedVelocity;

            if (updatedYPosition > SCREEN_HEIGHT) {
                _isAlive = false;
            }
        }

        else{
            _root.getChildren().remove(_birdPane);
        }
    }

    // Updates the neural network by giving the current input values to the network
    public void updateNeuralNetwork(Pipe[] pipes){
        this.checkDistanceFromPipe(pipes);
        _neuralNetwork.calculate();
    }

    // Checks for collision with each pipe. Need to optimize it so it just checks with the pipe closest
    public void checkCollision(Pipe[] pipe){
        for(int i = 0; i < 3; i++)
        {
            if (pipe[i].getNode()[0].intersects(_bird.getBoundsInLocal()) ||
                    pipe[i].getNode()[1].intersects(_bird.getBoundsInLocal())){

                _isAlive = false;
            }
        }
    }

    // This calculates how far the bird is from the pipe and sets the values for both X and Y distance
    public void checkDistanceFromPipe(Pipe[] pipes) {
        double closestX = 1000;
        int index = 0;
        for(int i = 0; i < 3; i++){
            if(pipes[i].getX() < closestX && pipes[i].getX() > _bird.getCenterX()){
                index = i;
                closestX = pipes[i].getX();
            }
        }

        _xDistanceFromPipe = pipes[index].getX() - _bird.getCenterX();
        _yDistanceFromPipe = pipes[index].getY() - _bird.getCenterY();
    }

    // Moves the bird up by setting its current velocity to its rebound velocity
    public void up(){
        _currentVelocity = REBOUND_VELOCITY;
    }

    // Returns true or false depending on whether the bird is alive or not
    public boolean isAlive(){
        return _isAlive;
    }

    // Returns the bird's node / pane
    public Pane getNode(){
        return _birdPane;
    }

    // Returns the fitness of the bird
    public int getFitness(){
        return _fitness;
    }

    public double[][] getSyn0(){
        return _neuralNetwork.getSyn0();
    }

    public void setSyn0(double[][] weights){
        _neuralNetwork.setSyn0(weights);
    }
    public double[][] getSyn1(){
        return _neuralNetwork.getSyn1();
    }

    public void setSyn1(double[][] weights){
        _neuralNetwork.setSyn1(weights);
    }


    // Returns the X Distance from the pipe
    public double getxDistanceFromPipe() {
        return _xDistanceFromPipe;
    }

    // Returns how many pixels the bird is from the Top of the bottom pipe
    public double getyDistanceFromPipe() {
        return _yDistanceFromPipe;
    }
}
