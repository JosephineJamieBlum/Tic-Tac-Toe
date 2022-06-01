package com.mycompany.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    /**
     * creates the stage
     *
     * @param stage the stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 400, 400);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Tic Tac Toe");
    }

    /**
     * sets the root
     *
     * @param fxml the fxml file
     * @throws IOException
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * loads the FXML
     *
     * @param fxml fxml file
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * main method
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}
