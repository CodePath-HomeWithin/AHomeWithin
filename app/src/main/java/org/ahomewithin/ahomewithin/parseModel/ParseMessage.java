package org.ahomewithin.ahomewithin.parseModel;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by xiangyang_xiao on 3/13/16.
 */
@ParseClassName("Message")
public class ParseMessage extends ParseObject {

  public static final String PARSE_NAME = "Message";

  //String usrId
  //the userId that created the message
  public static final String USER_ID_KEY = "userId";

  //String body
  //the message content
  public static final String BODY_KEY = "body";

  public ParseMessage() {
    super(PARSE_NAME);
  }

  public String getUserId() {
    return getString(USER_ID_KEY);
  }

  public String getBody() {
    return getString(BODY_KEY);
  }

  public void setUserId(String userId) {
    put(USER_ID_KEY, userId);
  }

  public void setBody(String body) {
    put(BODY_KEY, body);
  }
}