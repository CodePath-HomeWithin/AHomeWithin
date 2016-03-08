package org.ahomewithin.ahomewithin;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by barbara on 3/5/16.
 */
public class AHomeWithinClient {
    public static final int VIDEOS = 0;
    public static final int CONVERSATIONS = 1;

    // TODO:  this is totally stubbed out -- make real async http call
    public static JSONObject getRecommendations(Context context) {
        JSONObject jsonObj = null;
        try {
            String response = loadJSONFromAsset(context, "recommendations.json");
            jsonObj = new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(jsonObj);
    }

    // TODO:  this is totally stubbed out -- make real async http call
    public static JSONObject getProviders(Context context) {
        JSONObject jsonObj = null;
        try {
            String response = loadJSONFromAsset(context, "providers.json");
            jsonObj = new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(jsonObj);
    }

    // TODO:  this is totally stubbed out -- make real async http call
    public static JSONObject getEvents(Context context) {
        JSONObject jsonObj = null;
        try {
            String response = loadJSONFromAsset(context, "events.json");
            jsonObj = new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(jsonObj);
    }


    // TODO:  this is totally stubbed out -- make real async http call
    public static JSONArray getStreams(Context context, int type) {
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        try {
            String response = loadJSONFromAsset(context, "streams.json");
            jsonObject = new JSONObject(response);

            switch (type) {
                case VIDEOS:
                    jsonArray = jsonObject.getJSONArray("videos");
                    break;
                case CONVERSATIONS:
                    jsonArray = jsonObject.getJSONArray("conversations");
                    break;
                default:
                    jsonArray = jsonObject.getJSONArray("videos");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(jsonArray);
    }

    private static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("json/" + filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
