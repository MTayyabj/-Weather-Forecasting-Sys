import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.net.HttpURLConnection ;

public class App {

    //User Define methods

    //Method # 1
    public static JSONObject GET_LOCATION (String Country) {
        Country.replaceAll( " ","+");
        String URL_String = "https://geocoding-api.open-meteo.com/v1/search?name="+ Country + "&count=1&language=en&format=json";
        return
    }

    //Method # 2
    public static String READ_API_RESONSE (HttpURLConnection apiConnection) {
    StringBuilder JSON_RESULT = new StringBuilder();
    Scanner scanner = new Scanner (apiConnection.getInputStream());

    while (scanner.hasNext()){
        JSON_RESULT.append (scanner.nextLine());
        scanner.close();
        String RESULT =  JSON_RESULT.toString();
        return RESULT;
    }

    }



    public static void main(String[] args) {

    //Main code    
    System.out.println ("ENTER YOUR COUNTRY HERE :");
    Scanner CountryName= new  Scanner (System.in);
    String Country = CountryName.nextLine();
    
    }
}
