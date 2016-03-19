package org.ahomewithin.ahomewithin.models;

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
public class Recommended {
    public enum RecommendedType {
        BOOK, PODCAST, BLOG, ARTICLE, OTHER
    }
    public String id;
    public RecommendedType type;
    public String title;
    public String summary;
    public String description;
    public String sourceUrl;
    public String thumbnailUrl;
    public String pictureUrl;
    public Date publishDate;

    public Recommended() {
    }

    public static Recommended fromJSON(JSONObject jsonObject) {
        Recommended item = new Recommended();
        try {
            if (!jsonObject.isNull("id")) {
                item.id = jsonObject.getString("id");
            }
            if (!jsonObject.isNull("type")) {
                item.type = mapToType(jsonObject.getString("type"));
            }
            if (!jsonObject.isNull("title")) {
                item.title = jsonObject.getString("title");
            }
            if (!jsonObject.isNull("summary")) {
                item.summary = jsonObject.getString("summary");
            }
            if (!jsonObject.isNull("description")) {
                item.description = jsonObject.getString("description");
            }
            if (!jsonObject.isNull("sourceUrl")) {
                item.sourceUrl = jsonObject.getString("sourceUrl");
            }
            if (!jsonObject.isNull("thumbnailUrl")) {
                item.thumbnailUrl = jsonObject.getString("thumbnailUrl");
            }
            if (!jsonObject.isNull("pictureUrl")) {
                item.pictureUrl = jsonObject.getString("pictureUrl");
            }
            if (!jsonObject.isNull("publishDate")) {
                String publishDateStr = jsonObject.getString("publishDate");
                if (publishDateStr != null) {
                    item.publishDate = DateHelper.parseDate(publishDateStr, "MM-dd-yyyy");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static ArrayList<Recommended> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Recommended> recomendations = new ArrayList<Recommended>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject itemJson = jsonArray.getJSONObject(i);
                Recommended item = Recommended.fromJSON(itemJson);
                if (item != null) {
                    recomendations.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return recomendations;
    }

    public static RecommendedType mapToType(String type) {
        RecommendedType rt = RecommendedType.OTHER;
        switch(type) {
            case "book":
                rt = RecommendedType.BOOK;
                break;
            case "podcast":
                rt = RecommendedType.PODCAST;
                break;
            case "blog":
                rt = RecommendedType.BLOG;
                break;
            case "article":
                rt = RecommendedType.ARTICLE;
                break;
        }
        return rt;
    }

    public boolean isMoreRecent(Recommended other) {
        return publishDate.before(other.publishDate);
    }

    public String getId() {
        return id;
    }

    public RecommendedType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getFormattedPublishDate() {
        return DateHelper.dateToString(publishDate, DateHelper.FORMAT_MMDDYYYY);
    }

    /*
    // to wrap
    Recommended item = new Recommended();
    Recommended intent = new Intent(this, MyActivity.class);
    intent.putExtra("recommendation", Parcels.wrap(item));
    startActivity(intent);
    // to unwrap
    Recommended user = (Recommended) Parcels.unwrap(getIntent().getParcelableExtra("recommendation"));
*/
}
