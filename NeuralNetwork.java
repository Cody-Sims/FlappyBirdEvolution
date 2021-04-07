package evolution;

import java.util.Arrays;

public class NeuralNetwork {

    Bird _bird;
    double[][] _inputs;
    double _output;

    double[][] _inputWeights; //Weights applied to input
    double[][] _hiddenWeights; // Weights applied to output

    int _numberOfHiddenNodes = 3;
    double[][] _inputBias;
    double[][] _hiddenBias;


    public NeuralNetwork(Bird bird) {
        _bird = bird;
        _inputs = new double[1][2];

        _inputWeights = getRandomWeights(_inputs[0].length, _numberOfHiddenNodes); //Weights between input and hidden layer
        _hiddenWeights = getRandomWeights(_numberOfHiddenNodes, 1); // Weights between hidden layer and output layer

        _hiddenBias = getRandomBias(_numberOfHiddenNodes, 1);


    }

    private double[][] getRandomBias(int rows, int columns){
        double[][] bias = new double[rows][columns];
        for(int i = 0; i < bias.length; i++) {
            for(int j = 0; j< bias[0].length; j++) {
                bias[i][j] = (Math.random() * 0.02) - 0.01;
            }
        }

        return bias;
    }

    private double[][] addBias(double[][] array, double[][] bias){
        for(int i = 0; i < array.length; i++) {
                _hiddenWeights[i][0] = array[i][0] + bias[i][0];
                //array[i][0] = array[i][0] + bias[i][0];
            }
        return array;
    }

    private void getInput(){
        _inputs[0][0] = _bird.getxDistanceFromPipe(); // X distance from the pipe
        _inputs[0][1] =_bird.getyDistanceFromPipe(); // Y distance from the pipe
    }

    public void calculate(){
        getInput();
        forwardPropagation();
        if(_output < 0.5){
            _bird.up();
        }
    }

    public double[][] getSyn0(){
        return _inputWeights;
    }

    public void setSyn0(double[][] weights){
        _inputWeights = weights;
    }
    public double[][] getSyn1(){
        return _hiddenWeights;
    }

    public void setSyn1(double[][] weights){
        _hiddenWeights = weights;
    }

    private double[][] getRandomWeights(int rows, int columns){
        double[][] weights = new double[rows][columns];
        for(int i = 0; i < weights.length; i++) {
            for(int j = 0; j< weights[0].length; j++) {
                weights[i][j] = (Math.random() * 2 ) - 1;
            }
        }

        return weights;
    }

    private void forwardPropagation(){

        double[][] hiddenOutputs = activate(dotProduct(_inputs, _inputWeights));


        double[][] outputLayer  = activate(addBias(dotProduct(hiddenOutputs, _hiddenWeights), _hiddenBias));


        _output = outputLayer[0][0];

    }

    // This is the pre-activation function for the neural network
    private double[][] dotProduct(double[][] A, double[][] B){
        int m = A.length;
        int n = A[0].length;
        int p = B.length;
        int q = B[0].length;

        double[][] C = new double[m][q];

        if(n == p){
            for(int r = 0; r < m; r++){ // for each row in A
                for(int c = 0; c < q; c++){ // for each column in B
                    for(int i = 0; i < p; i++){
                        C[r][c] += A[r][i] * B[i][c];
                    }
                }
            }
        }
        return C;
    }

    // This is the activation function for the neural network
    private double[][] activate(double[][] array){
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j< array[0].length; j++) {
                array[i][j] = sigmoid(array[i][j]);
            }
        }
        return array;
    }

    private double sigmoid(double x){
        return 1 / (1 + Math.exp(-x));
    }
}

