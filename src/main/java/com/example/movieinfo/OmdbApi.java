package com.example.movieinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OmdbApi {
    private static final String API_KEY = "86819fdc-72cd-4b22-9822-af5318663030";

    public static List<String> fetchLanguages() {
        List<String> languages = new ArrayList<>();

        try {
            String apiUrl = "http://www.omdbapi.com/?apikey=" + API_KEY + "&lang=";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    languages.add(line.trim());
                }

                reader.close();
            } else {
                // Handle the error
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error
        }
        System.out.println(languages);
        return languages;
    }
}
