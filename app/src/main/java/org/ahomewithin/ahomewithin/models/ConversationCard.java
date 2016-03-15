package org.ahomewithin.ahomewithin.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chezlui on 11/03/16.
 */
public class ConversationCard implements Serializable{
    public static final int MIND = 0;
    public static final int BODY = 1;
    public static final int HEART = 2;
    public static final int SOUL = 3;

    public static final String CARD_TAG = "card_tag";


    public int type; // 0: mind,  1: body, 2: heart, 3: soul
    public String quote;
    public String quote_author;
    public String reflection;
    public String action;


    public static ConversationCard fromJson(JSONObject jsonObject, int type) {
        ConversationCard card = new ConversationCard();

        try {
            card.type = type;
            card.quote = jsonObject.getString("quote");
            card.quote_author = jsonObject.getString("quote_author");
            card.reflection = jsonObject.getString("reflection");
            card.action = jsonObject.getString("action");
        } catch (JSONException ex) {
            Log.e("ERR", ex.toString());
            return null;
        }

        return card;
    }

    public static ArrayList<ConversationCard> fromJson(JSONObject jsonObject) {
        ArrayList<ConversationCard> cards = new ArrayList<>();
        try {
            ConversationCard  mind = fromJson(jsonObject.getJSONObject("mind"), MIND);
            ConversationCard  body = fromJson(jsonObject.getJSONObject("mind"), BODY);
            ConversationCard  heart = fromJson(jsonObject.getJSONObject("mind"), HEART);
            ConversationCard  soul = fromJson(jsonObject.getJSONObject("mind"), SOUL);

            cards.add(mind);
            cards.add(body);
            cards.add(heart);
            cards.add(soul);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cards;
    }
}
