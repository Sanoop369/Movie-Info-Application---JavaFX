package com.example.movieinfo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {a

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml")); // Make sure to adjust the path accordingly

        // Set the stage title
        primaryStage.setTitle("Movie Information App");

        // Create a new scene with the loaded FXML content
        Scene scene = new Scene(root, 800, 600); // Adjust the width and height as needed

        // Set the scene for the primary stage
        primaryStage.setScene(scene);

        // Show the primary stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
