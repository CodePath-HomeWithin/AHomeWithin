package org.ahomewithin.ahomewithin.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chezlui on 11/03/16.
 */
public class ConversationCard {
    public static final int MIND = 0;
    public static final int BODY = 1;
    public static final int HEART = 2;
    public static final int SOUL = 3;


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
}
