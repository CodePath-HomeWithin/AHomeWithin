package org.ahomewithin.ahomewithin.models;

import android.location.Address;
import android.text.TextUtils;

import org.ahomewithin.ahomewithin.util.DateHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by barbara on 3/5/16.
 */
@Parcel
public class Event {

    public int id;
    public String groupName;
    public String groupDescription;
    public String eventName;
    public String eventDescription;
    public String url;
    public String imageUrl;
    public String locationName;
    public Address address;
    public Date date;
    public String startTime;
    public String endTime;
    // contact:  email, phone, name (user?)

    public Event() {
    }

    public static Event fromJSON(JSONObject jsonObject) {
        Event event = new Event();
        try {
            if (!jsonObject.isNull("id")) {
                event.id = jsonObject.getInt("id");
            }
            if (!jsonObject.isNull("groupName")) {
                event.groupName = jsonObject.getString("groupName");
            }
            if (!jsonObject.isNull("groupDescription")) {
                event.groupDescription = jsonObject.getString("groupDescription");
            }
            if (!jsonObject.isNull("eventName")) {
                event.eventName = jsonObject.getString("eventName");
            }
            if (!jsonObject.isNull("eventDescription")) {
                event.eventDescription = jsonObject.getString("eventDescription");
            }
            if (!jsonObject.isNull("url")) {
                event.url = jsonObject.getString("url");
            }
            if (!jsonObject.isNull("imageUrl")) {
                event.imageUrl = jsonObject.getString("imageUrl");
            }

            if (!jsonObject.isNull("startTime")) {
                event.startTime = jsonObject.getString("startTime");
            }
            if (!jsonObject.isNull("endTime")) {
                event.endTime = jsonObject.getString("endTime");
            }
            if (!jsonObject.isNull("location")) {
                JSONObject location = jsonObject.getJSONObject("location");
                if (!location.isNull("name")) {
                    event.locationName = location.getString("name");
                }
                event.address = AddressHelper.addressFromJson(jsonObject.getJSONObject("location"));
            }
            if (!jsonObject.isNull("date")) {
                String dateStr = jsonObject.getString("date");
                if (dateStr != null) {
                    event.date = DateHelper.parseDate(dateStr, "MM-dd-yyyy");
                }
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

    public boolean isMoreRecent(Event other) {
        return date.before(other.date);
    }

    public String getLocation() {
        return AddressHelper.getLocation(address);
    }

    public String getUrl() {
        return url;
    }
    public String getDateTime() {
        List<String> tokens = new ArrayList<String>();
        if (date != null) {
            tokens.add(DateHelper.dateToString(date, DateHelper.FORMAT_EEEMMMdd));
        }
        if (startTime != null) {
            tokens.add(startTime);
        }
        return(TextUtils.join(" ", tokens));
    }


}
