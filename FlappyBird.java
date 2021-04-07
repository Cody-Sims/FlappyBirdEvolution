package evolution;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Arrays;

import static evolution.Constants.DURATION;


public class FlappyBird {
    private Pane _flappyBirdPane;
    private Pane _root;
    private Board _board;


    private Bird[] _birds;
    private Bird[] _deadBirds;
    private Bird[] _bestBirds;

    private final int _numberOfBirds;

    private int _numberOfBirdsAlive = 0;
    private int _highScore;

    private int _averageFitness;
    private int _bestFitness;



    Timeline _timeline;

    public FlappyBird(Pane root, int numberOfBirds){
        _root = root;
        _numberOfBirds = numberOfBirds;

        setupGame();
    }

    private void restartGame()
    {
        _root.getChildren().remove(_flappyBirdPane);
        setupGame();

        if(_numberOfBirds > 1){
            evolve();
        }
    }

    //Sets up the game in a method for reusability
    private void setupGame(){
        _flappyBirdPane = new Pane();
        _root.getChildren().add(_flappyBirdPane);

        _board = new Board(_flappyBirdPane, _highScore);

        _deadBirds = new Bird[_numberOfBirds];
        _birds = new Bird[_numberOfBirds];


        for(int i = 0; i < _numberOfBirds; i++) {
            _birds[i]  = new Bird(_flappyBirdPane);
        }

        setupTimeline();

        if(_numberOfBirds == 1) {
            setupMovement();
        }


    }

    private void setupTimeline(){
        KeyFrame kf = new KeyFrame(Duration.seconds(DURATION), new GameHandler());

        _timeline = new Timeline(kf);
        _timeline.setCycleCount(Animation.INDEFINITE);

        _timeline.play();
        //_timeline.setRate(50);
    }

    private void setupMovement(){
        _birds[0].getNode().addEventHandler(KeyEvent.KEY_PRESSED, new keyHandler());
        _birds[0].getNode().setFocusTraversable(true);
    }


    private void updateNeuralNetwork(){
        for(int i = 0; i < _numberOfBirds; i++) {
            if (_birds[i] != null) {
                _birds[i].updateNeuralNetwork(_board.getPipes()); // Updates the inputs
            }
        }
    }

    // Apply the previous best birds weights to this generation randomly but selecting a random number for each
    // bird and getting the weights of the _previousBestBird[rand] and applying them to _birds[i]


    // Make a function so it only evolves if the average fitness is greater than the previous generation
    private void evolve(){

        for (int i = 0; i < _numberOfBirds - 10; i++) {
            //System.out.println(Arrays.toString(_bestBirds));
            int index = (int)(Math.random() * _bestBirds.length);
            _birds[i].setSyn0(_bestBirds[index].getSyn0());
            _birds[i].setSyn1(_bestBirds[index].getSyn1());
        }

    }

    private void getBestBirds() {
        _bestBirds = new Bird[4];
        Bird[] currentGen = _deadBirds;
        for (int i = 0; i < 4; i++) {
            Bird bestBird = _birds[0];
            for(int j = 0; j < _numberOfBirds - 5; j++) {
                if(currentGen[j] != null){
                    if (currentGen[j].getFitness() > bestBird.getFitness()) {
                        bestBird = currentGen[j];
                        currentGen[j] = null;
                    }
                }

            }

            _bestBirds[i] = bestBird;
        }
        stats();
    }

    private void stats(){
        System.out.println("Average fitness: "+ Stats.averageFitness(_birds));
        System.out.println("Best fitness: "+ Stats.bestFitness(_bestBirds));

        //Stats.averageFitness(_deadBirds);
        Stats.bestFitness(_bestBirds);

    }



    private class GameHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            _board.updatePipes();
            _board.updateStats();

            for(int i = 0; i < _numberOfBirds; i++) {
                if (_birds[i] != null) {
                    _birds[i].update();
                    _birds[i].checkCollision(_board.getPipes());
                    if (_numberOfBirds > 1) { //Doesn't check distance if it is on smart mode
                        _birds[i].checkDistanceFromPipe(_board.getPipes());
                    }
                }
            }

            if(_numberOfBirds > 1) { //Doesn't check distance if it is on smart mode
                updateNeuralNetwork();
            }
            _numberOfBirdsAlive = 0;
            for(int i = 0; i < _numberOfBirds; i++) {
                if (_birds[i] != null) {
                    if (_birds[i].isAlive()) {
                        _numberOfBirdsAlive++;
                        //break;
                    } else {
                        _birds[i].update();
                        _deadBirds[i] = _birds[i];
                    }
                }
            }



            if(_numberOfBirdsAlive == 0)
            {

                _highScore = _board.getHighScore();
                _timeline.stop();
                getBestBirds();

                _deadBirds = new Bird[50];
                restartGame();
            }
        }
    }

    private class keyHandler implements EventHandler<KeyEvent> {
        public void handle(KeyEvent e) {
            switch (e.getCode()) {
                case UP:

                case SPACE:
                    _birds[0].up();
                    break;

                default:
                    break;
            }
            e.consume();
        }
    }
}
