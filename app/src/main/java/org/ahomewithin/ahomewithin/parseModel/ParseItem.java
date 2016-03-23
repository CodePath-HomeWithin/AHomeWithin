package org.ahomewithin.ahomewithin.parseModel;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.ahomewithin.ahomewithin.models.Item;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by xiangyang_xiao on 3/13/16.
 */
@ParseClassName("Item")
public class ParseItem extends ParseObject
        implements Serializable {

    public ParseItem() {
    }

    public static final String PARSE_NAME = "Item";
    //0 for video and 1 for card
    //int type;
    public static final String TYPE_KEY = "type";

    //String title;
    public static final String TITLE_KEY = "title";

    //String desp;
    public static final String DESP_KEY = "desp";

    //double price;
    public static final String PRICE_KEY = "price";

    //String imageUrl;
    public static final String IMAGE_KEY = "image_url";

    //only available for video resource
    //JSONObject content;
    public static final String CONTENT_KEY = "content";

    public Item.ITEM_TYPE getType() {
        switch (getInt(TYPE_KEY)) {
            default:
            case 0:
                return Item.ITEM_TYPE.VIDEOS;
            case 1:
                return Item.ITEM_TYPE.CONVERSATIONS;
        }
    }

    public String getTitle() {
        return getString(TITLE_KEY);
    }

    public String getDesp() {
        return getString(DESP_KEY);
    }

    public int getPrice() {
        return getInt(PRICE_KEY);
    }

    public String getImage() {
        return getString(IMAGE_KEY);
    }

    public String getContent() {
        return getString(CONTENT_KEY);
    }

    public JSONObject getContentJson() {
        return getJSONObject(CONTENT_KEY);
    }

    public void setType(Item.ITEM_TYPE type) {
        switch (type) {
            default:
            case VIDEOS:
                put(TYPE_KEY, 0);
                break;
            case CONVERSATIONS:
                put(TYPE_KEY, 1);
                break;
        }

    }

    public void setTitle(String title) {
        put(TITLE_KEY, title);
    }

    public void setDesp(String desp) {
        put(DESP_KEY, desp);
    }

    public void setPrice(double price) {
        put(PRICE_KEY, price);
    }

    public void setImage(String imageUrl) {
        put(IMAGE_KEY, imageUrl);
    }

    public void setContent(String content) {
        put(CONTENT_KEY, content);
    }

    public void update(Item item) {
        setType(item.type);
        setTitle(item.title);
        setDesp(item.description);
        setPrice(item.price);
        setImage(item.imageUrl);
        String content = (item.type == Item.ITEM_TYPE.VIDEOS) ? item.contentUrl : item.content;
        setContent(content);
    }
}
