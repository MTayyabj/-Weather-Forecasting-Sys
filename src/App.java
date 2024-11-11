import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;

public class App {

    // User-defined methods

    // Method #1
    public static JSONObject GET_LOCATION(String Country) {
        Country = Country.replaceAll(" ", "+");
        String URL_String = "https://geocoding-api.open-meteo.com/v1/search?name=" + Country + "&count=1&language=en&format=json";
        try {
            HttpURLConnection apiConnection = FETCH_RESPONSE(URL_String);

            if (apiConnection.getResponseCode() != 200) {
                System.out.println("Error! Could not connect to server");
                return null;
            }

            String JSONResponse = READ_API_RESPONSE(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject resultJSONObject = (JSONObject) parser.parse(JSONResponse);

            JSONArray locationData = (JSONArray) resultJSONObject.get("results");
            return (JSONObject) locationData.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method #2
    public static HttpURLConnection FETCH_RESPONSE(String URL_String) {
        try {
            URL url = new URL(URL_String);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method #3
    public static String READ_API_RESPONSE(HttpURLConnection apiConnection) throws IOException {
        StringBuilder JSON_RESULT = new StringBuilder();
        Scanner scanner = new Scanner(apiConnection.getInputStream());

        while (scanner.hasNext()) {
            JSON_RESULT.append(scanner.nextLine());
        }

        scanner.close();
        return JSON_RESULT.toString();
    }

    // Method #4
    public static void DISPLAY_WEATHER_DATA(double latitude, double longitude) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true";

            HttpURLConnection apiConnection = FETCH_RESPONSE(url);

            if (apiConnection.getResponseCode() != 200) {
                System.out.println("Error! Could not connect to server");
                return;
            }

            String JSONResponse = READ_API_RESPONSE(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject resultJSONObject = (JSONObject) parser.parse(JSONResponse);

            JSONObject weatherData = (JSONObject) resultJSONObject.get("current_weather");

            String time = (String) weatherData.get("time");
            System.out.println("Current time: " + time);

            Double temperature = (Double) weatherData.get("temperature");
            System.out.println("Current temperature: " + temperature);

            Double windSpeed = (Double) weatherData.get("windspeed");
            System.out.println("Current wind speed: " + windSpeed);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Main method
        System.out.println("ENTER YOUR COUNTRY HERE:");
        Scanner countryName = new Scanner(System.in);
        String country = countryName.nextLine();
        countryName.close();

        JSONObject locationData = GET_LOCATION(country);
        if (locationData != null) {
            double latitude = (double) locationData.get("latitude");
            double longitude = (double) locationData.get("longitude");

            DISPLAY_WEATHER_DATA(latitude, longitude);
        } else {
            System.out.println("Location not found.");
        }
    }
}
