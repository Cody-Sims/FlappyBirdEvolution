package evolution;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static evolution.Constants.*;

public class Board {

    private Pane _root;
    private Pane _boardPane;
    private Pipe[] _pipes;

    private int _highScore;
    private int _score = 0;

    private Text _highScoreText;
    private Text _scoreText;


    public Board(Pane root, int highScore){
        _root = root;
        _boardPane = new Pane();
        _root.getChildren().add(_boardPane);
        _highScore = highScore;

        setupPipes();
        setUpStats();
    }



    private void setupPipes(){
        _pipes = new Pipe[3];
        for(int i = 0; i < 3; i++){
            _pipes[i] = new Pipe(_boardPane, i);
        }
    }

    public void updatePipes(){
        for(int i = 0; i < 3; i++){
            _score += _pipes[i].update(); //This returns a 1 if the piece is moved off screen
        }
    }

    public Pipe[] getPipes() {
        return _pipes;
    }

    public void destroy(){
        _boardPane.getChildren().removeAll();
    }

    public void updateStats(){
        if(_score > _highScore){
            _highScore = _score;
            _highScoreText.setText("High Score: " + _highScore);
        }
        _scoreText.setText("Score: " + _score);

    }

    public void setUpStats(){
        VBox statsPane = new VBox();
        statsPane.setPrefSize(SCREEN_WIDTH, 60);
        statsPane.setStyle("-fx-background-color: black;");

        _highScoreText = new Text("High Score: " + _highScore);
        _highScoreText.setFill(Color.WHITE);
        _highScoreText.setFont(Font.font("ComicSans", 30));

         _scoreText = new Text("Score: " + _score);
        _scoreText.setFill(Color.WHITE);
        _scoreText.setFont(Font.font("ComicSans", 30));

        statsPane.getChildren().addAll(_highScoreText, _scoreText);
        statsPane.relocate(0, SCREEN_HEIGHT - 80);
        _root.getChildren().add(statsPane);
    }

    public int getHighScore() {
        return _highScore;
    }
}


