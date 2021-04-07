package evolution;

import javafx.scene.layout.Pane;

public class SmartBird extends Bird {
    private int _fitness = 0;

    private double _xDistanceFromPipe;
    private double _yDistanceFromPipe;

    private boolean _isAlive = true;

    private NeuralNetwork _neuralNetwork;

    public SmartBird(Pane root){
        super(root);

        _neuralNetwork = new NeuralNetwork(this); //Gives this bird a neural network.

        create();
    }

    // Updates the neural network by giving the current input values to the network
    public void updateNeuralNetwork(Pipe[] pipes){
        this.checkDistanceFromPipe(pipes);
        _neuralNetwork.calculate();
    }

    // This calculates how far the bird is from the pipe and sets the values for both X and Y distance
    public void checkDistanceFromPipe(Pipe pipes[]) {
        double closestX = 1000;
        int index = 0;
        for(int i = 0; i < 3; i++){
            if(pipes[i].getX() < closestX && pipes[i].getX() > super._bird.getCenterX()){
                index = i;
                closestX = pipes[i].getX();
            }
        }

        _xDistanceFromPipe = pipes[index].getX() - _bird.getCenterX();
        _yDistanceFromPipe = pipes[index].getY() - _bird.getCenterY();
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

