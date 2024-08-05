package com.example.movieinfo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MovieInfoController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label directorLabel;

    @FXML
    private Label plotLabel;

    public void setMovieDetails(String movieDetails) {
        // Parse the movie details string and update the labels
        String[] detailsArray = movieDetails.split("\n");
        if (detailsArray.length >= 4) {
            titleLabel.setText(detailsArray[0]);
            yearLabel.setText(detailsArray[1]);
            directorLabel.setText(detailsArray[2]);
            plotLabel.setText(detailsArray[3]);
        } else {
            // Handle the case where movie details are incomplete or invalid
            titleLabel.setText("N/A");
            yearLabel.setText("N/A");
            directorLabel.setText("N/A");
            plotLabel.setText("N/A");
        }
    }
}
