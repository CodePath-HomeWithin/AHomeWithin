package org.ahomewithin.ahomewithin.models;

import android.location.Address;

import org.ahomewithin.ahomewithin.util.DateHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by barbara on 3/5/16.
 */
@Parcel
public class Event {

    public int id;
    public String name;
    public String url;
    public String imageUrl;
    public String summary;
    public String description;
    public Address address;
    public Date date;
    public String startTime;
    public String endTime;
    // contact:  email, phone, name (person?)

    public Event() {
    }

    public static Event fromJSON(JSONObject jsonObject) {
        Event event = new Event();
        try {
            event.id = jsonObject.getInt("id");
            event.name = jsonObject.getString("name");
            event.url = jsonObject.getString("url");
            event.imageUrl = jsonObject.getString("imageUrl");
            event.summary = jsonObject.getString("summary");
            event.description = jsonObject.getString("description");
            event.startTime = jsonObject.getString("startTime");
            event.endTime = jsonObject.getString("endTime");
            event.address = AddressHelper.addressFromJson(jsonObject.getJSONObject("address"));
            String dateStr = jsonObject.getString("date");
            if (dateStr != null) {
                event.date = DateHelper.parseDate(dateStr, "MM-dd-yyyy");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return event;
    }

    public static ArrayList<Event> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Event> events = new ArrayList<Event>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject eventJson = jsonArray.getJSONObject(i);
                Event event = Event.fromJSON(eventJson);
                if (event != null) {
                    events.add(event);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return events;
    }


}
