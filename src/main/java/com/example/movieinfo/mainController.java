package com.example.movieinfo;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javafx.geometry.Pos;

import java.net.URL;
import javafx.stage.Modality;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class mainController {

    private ObservableList<String> languageOptions;
    private ObservableList<String> yearOptions;

    @FXML
    private Button btn;

    @FXML
    private ComboBox<String> language;

    @FXML
    private TextField name;

    @FXML
    private Label movieInfoLabel;

    @FXML
    private ComboBox<String> year;

    @FXML
    void showDetails(ActionEvent event) {
        // Show a progress bar
        Stage progressStage = createProgressStage();
        progressStage.show();

        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                // Fetch movie details based on user input
                String selectedName = name.getText();
                String selectedYear = year.getValue();
                String selectedLanguage = language.getValue();
                return fetchMovieDetails(selectedName, selectedYear, selectedLanguage);
            }
        };

        // When the task is finished, update the UI
        task.setOnSucceeded(e -> {
            String movieDetails = task.getValue();
            progressStage.close(); // Close the progress window

            // Open a new window to display the movie information
            openMovieInfoWindow(movieDetails);
        });

        // Start the task in a background thread
        new Thread(task).start();
    }
    @FXML
    void initialize() {
        // Fetch language data from OMDb API
        List<String> predefinedLanguages = Arrays.asList("English", "Tamil", "Spanish", "French", "Italian", "Malayalam", "Hindi");

        // Populate the language ComboBox
        languageOptions = FXCollections.observableArrayList(predefinedLanguages);
        language.setItems(languageOptions);

        // Initialize year options (e.g., from 1900 to the current year)
        int currentYear = java.time.Year.now().getValue();
        yearOptions = FXCollections.observableArrayList();
        for (int year = 1900; year <= currentYear; year++) {
            yearOptions.add(Integer.toString(year));
        }
        year.setItems(yearOptions);
    }
    private Stage createProgressStage() {
        // Create a new stage for displaying the progress bar
        Stage stage = new Stage();
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        progressBar.setPrefHeight(20);

        // Add the progress bar to the scene
        VBox vbox = new VBox(progressBar);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 300, 100);

        // Set the scene for the progress stage
        stage.setScene(scene);
        stage.setTitle("Fetching Movie Information");
        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }

    private void openMovieInfoWindow(String movieDetails) {
        try {
            // Load the movie info FXML and create a new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("movieInfo.fxml"));
            Parent root = loader.load();
            MovieInfoController controller = loader.getController();
            controller.setMovieDetails(movieDetails); // Pass movie details to the controller

            Stage movieInfoStage = new Stage();
            movieInfoStage.setTitle("Movie Information");
            movieInfoStage.setScene(new Scene(root, 600, 400));
            movieInfoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add the fetchMovieDetails method here
    private String fetchMovieDetails(String movieName, String movieYear, String movieLanguage) {
        try {
            // Construct the OMDB API URL with your API key
            String apiKey = "3638b09e";
            String apiUrl = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + movieName + "&y=" + movieYear + "&plot=full";

            // Create an HTTP connection
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response data
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response
                JSONObject jsonObject = new JSONObject(response.toString());

                // Extract the movie details you need (e.g., title, year, director, plot)
                String title = jsonObject.getString("Title");
                String year = jsonObject.getString("Year");
                String director = jsonObject.getString("Director");
                String plot = jsonObject.getString("Plot");

                // Format and return the movie details
                return title + "\n " + year + "\n " + director + "\n" + plot;
            } else {
                return "Error: Unable to fetch movie details";
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return "Error: An exception occurred while fetching movie details";
        }
    }
}


//        // Implement logic to fetch movie details based on user input