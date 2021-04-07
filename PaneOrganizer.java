package evolution;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import static evolution.Constants.*;

public class PaneOrganizer{
    private Pane _root;
    private BorderPane _buttonPane;

    /*
     * This constructor instantiates the _root and sets it's size and background color.
     */
    public PaneOrganizer(){
        _root = new Pane();
        _root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        _root.setStyle("-fx-background-color: skyblue;");

        setUpStartScreen();
    }

    /*
     * This method returns the root, so I can utilize it in other classes
     */
    public Pane getRoot(){
        return _root;
    }

    /*
     * This method adds a quit button to the bottom of the _root Pane
     */
    private void setupQuitButton(){
        Button button = new Button("Quit");
        button.setFocusTraversable(false);
        button.setOnAction(new QuitButtonHandler());

        _root.getChildren().add(button);
    }

    private void setUpStartScreen() {
        _buttonPane = new BorderPane();
        _buttonPane.setPrefSize(200,100);

        Button manualButton = new Button("Manual Flappy Bird");
        manualButton.setFocusTraversable(false);
        manualButton.setOnAction(new ManualGameModeButtonHandler());
        _buttonPane.setLeft(manualButton);

        Button smartButton = new Button("Smart Flappy Bird");
        smartButton.setFocusTraversable(false);
        smartButton.setOnAction(new SmartGameModeButtonHandler());
        _buttonPane.setRight(smartButton);

        _buttonPane.relocate(SCREEN_WIDTH / 2 - 150, SCREEN_HEIGHT / 2);

        _root.getChildren().add(_buttonPane);
    }

    /*
     * This class handles the button and if the button is pressed, it closes the window.
     */
    private class QuitButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Platform.exit();
        }
    }

    private class ManualGameModeButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            _root.getChildren().remove(_buttonPane);
            new FlappyBird(_root, 1);
            setupQuitButton();

        }
    }

    private class SmartGameModeButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            new FlappyBird(_root, 50);
            setupQuitButton();
            _root.getChildren().remove(_buttonPane);
        }
    }
}
