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

    public static final int VIDEOS = 0;
    public static final int CONVERSATIONS = 1;

    public static final String LOG_TAG = Item.class.getSimpleName();
    public static final String SERIALIZABLE_TAG = "item_serializable";

    public int id;
    public int type;    // 0: videos, 1: conversation cards
    public String title;
    public String imageUrl;
    public String description;
    public boolean owned;
    public int price;
    public ArrayList<ConversationCard> contentCards;  //
    public String videoUrl;


    public static Item fromJson(JSONObject jsonObject, int type) {
        Item item = new Item();

        try {
            item.title = jsonObject.getString("title");
            item.imageUrl = jsonObject.getString("image");
            item.description = jsonObject.getString("description");
            item.id = jsonObject.getInt("id");
            item.type = type;
            if (type == VIDEOS) {
                item.videoUrl = jsonObject.getJSONObject("content").getString("videoUrl");
            } else {
                JSONObject deckCards = jsonObject.getJSONObject("content");
                item.contentCards = new ArrayList<>();
                item.contentCards.add(ConversationCard.fromJson(deckCards.getJSONObject("mind"),
                        ConversationCard.MIND));
                item.contentCards.add(ConversationCard.fromJson(deckCards.getJSONObject("body"),
                        ConversationCard.BODY));
                item.contentCards.add(ConversationCard.fromJson(deckCards.getJSONObject("heart"),
                        ConversationCard.HEART));
                item.contentCards.add(ConversationCard.fromJson(deckCards.getJSONObject("soul"),
                        ConversationCard.SOUL));
            }
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
