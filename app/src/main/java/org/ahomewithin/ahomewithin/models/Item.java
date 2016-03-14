package org.ahomewithin.ahomewithin.models;

import android.util.Log;

import com.google.gson.Gson;

import org.ahomewithin.ahomewithin.parseModel.ParseItem;
import org.ahomewithin.ahomewithin.util.CardContent;
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
  public String id;
  public int type;    // 0: videos, 1: conversation cards
  public boolean owned;
  public String contentUrl; //vidoe url
  public double price;
  public String content;  //card json string, use getCardContentFromJsonString below to deJson

  public static Item fromJson(JSONObject jsonObject, int type) {
    Item item = new Item();

    try {
      item.title = jsonObject.getString("title");
      item.imageUrl = jsonObject.getString("image");
      item.description = jsonObject.getString("description");
      item.id = jsonObject.getString("id");
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

  public static Item getNewInstanceFromParseObject(ParseItem item) {
    Item newItem = new Item();
    newItem.id = item.getObjectId();
    newItem.type = item.getType();
    newItem.title = item.getTitle();
    newItem.description = item.getDesp();
    newItem.price = item.getPrice();
    newItem.imageUrl = item.getImage();
    if(newItem.type == 0) { //video
      newItem.contentUrl = item.getContent();
    } else { //cards
      newItem.content = item.getContent();
    }
    return newItem;
  }

  public static CardContent getCardContentFromJsonString(String jsonString) {
    Gson gson = new Gson();
    return gson.fromJson(jsonString, CardContent.class);
  }

  public static String getJsonStringFromCardContent(CardContent cardContent) {
    Gson gson = new Gson();
    return gson.toJson(cardContent);
  }
}
