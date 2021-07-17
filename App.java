package evolution;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        PaneOrganizer organizer = new PaneOrganizer();

        Scene scene = new Scene(organizer.getRoot());

        stage.setScene(scene);
        stage.setTitle("Evolution");
        stage.show();
    }

    public static void main(String[] argv) {
        launch(argv);
    }
}
