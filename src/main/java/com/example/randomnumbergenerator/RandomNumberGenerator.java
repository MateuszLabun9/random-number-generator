package com.example.randomnumbergenerator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class which is creating a new scene and stage.
 *
 * @see sceneController
 * @see generatorModel
 */
public class RandomNumberGenerator extends Application {

    /**
     *
     * @param stage main stage on which view objects will base on.
     * @throws IOException In case of any exception, throws an error.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RandomNumberGenerator.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Random Number Generator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args arguments used when launching main function.
     */
    public static void main(String[] args) {
        launch();
    }
}