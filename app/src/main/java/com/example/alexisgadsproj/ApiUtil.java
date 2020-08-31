package com.example.alexisgadsproj;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil() {}
    public static final String BASE_API_URL = "https://gadsapi.herokuapp.com/api/";
    public static URL buildUrl(String title) {
        String fullUrl = BASE_API_URL+ title;
        URL url=null;

        try{
            url = new URL(fullUrl);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection =(HttpURLConnection) url.openConnection();

        try{
            InputStream stream = connection.getInputStream();
            Scanner scanner= new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();
            if(hasData){
                return scanner.next();
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            Log.d("Error", e.toString());
            return  null;
        }
        finally {
            connection.disconnect();
        }
    }

    public static ArrayList<TopLearner> getTopLearnersFromJson(String json){
        final String NAME="name";
        final String HOURS="hours";
        final String COUNTRY="country";
        final String BADGE_URL="badgeUrl";

        ArrayList<TopLearner> topLearners = new ArrayList<TopLearner>();

        try{
            JSONArray arrayLearners = new JSONArray(json);
            int numberOfLearners = arrayLearners.length();
            for (int i=0; i<numberOfLearners; i++){
                JSONObject learnerJson = arrayLearners.getJSONObject(i);

                TopLearner topLearner= new TopLearner(
                        learnerJson.getString(NAME),
                        learnerJson.getInt(HOURS),
                        learnerJson.getString(COUNTRY),
                        learnerJson.getString(BADGE_URL));

                topLearners.add(topLearner);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return topLearners;

    }
}
