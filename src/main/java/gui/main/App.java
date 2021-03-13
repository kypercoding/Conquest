package gui.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Conquest Program:
 *
 * Main menu of the game Conquest
 *
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new VBox());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}