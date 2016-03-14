package org.ahomewithin.ahomewithin.parseModel;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.ahomewithin.ahomewithin.models.Item;

import java.io.Serializable;

/**
 * Created by xiangyang_xiao on 3/13/16.
 */
@ParseClassName("Item")
public class ParseItem extends ParseObject
    implements Serializable {

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


  public ParseItem() {
    super(PARSE_NAME);
  }

  public int getType() {
    return getInt(TYPE_KEY);
  }

  public String getTitle() {
    return getString(TITLE_KEY);
  }

  public String getDesp() {
    return getString(DESP_KEY);
  }

  public double getPrice() {
    return getDouble(PRICE_KEY);
  }

  public String getImage() {
    return getString(IMAGE_KEY);
  }

  public String getContent() {
    return getString(CONTENT_KEY);
  }

  public void setType(int type) {
    put(TYPE_KEY, type);
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
    String content = item.type==0? item.contentUrl : item.content;
    setContent(content);
  }
}
