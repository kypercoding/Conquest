package gui.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Conquest Program:
 *
 * Main menu of the game Conquest
 *
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        VBox vBox = new VBox(new Text(System.getProperty("user.dir")));
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}