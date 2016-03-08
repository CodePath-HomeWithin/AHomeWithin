package org.ahomewithin.ahomewithin.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chezlui on 06/03/16.
 */
// TODO Use parceler
public class Item implements Serializable {
    public static final String LOG_TAG = Item.class.getSimpleName();
    public static final String SERIALIZABLE_TAG = "item_serializable";

    public String title;
    public String description;
    public String imageUrl;
    public int id;
    public int type;
    public boolean owned;
    public String contentUrl;


    public static Item fromJson(JSONObject jsonObject, int type) {
        Item item = new Item();

        try {
            item.title = jsonObject.getString("title");
            item.imageUrl = jsonObject.getString("image");
            item.description = jsonObject.getString("description");
            item.id = jsonObject.getInt("id");
            item.contentUrl = jsonObject.getString("content_url");
            item.type = type;
            item.owned = false;
        } catch (JSONException ex) {
            Log.e("ERR", ex.toString());
            return null;
        }

        return item;
    }


    public static ArrayList<Item> fromJson(JSONArray jsonArray, int type) {
        ArrayList<Item> itemArrayList = new ArrayList<Item>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweet = jsonArray.getJSONObject(i);
                itemArrayList.add(fromJson(tweet, type));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, e.toString());
            }
        }
        return itemArrayList;
    }
}
