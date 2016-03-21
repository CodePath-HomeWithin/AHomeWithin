package org.ahomewithin.ahomewithin.parseModel;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;

import java.util.List;

/**
 * Created by xiangyang_xiao on 3/13/16.
 */
@ParseClassName("Chat")
public class ParseChat extends ParseObject {

  public ParseChat() {
  }

  public static final String PARSE_NAME = "Chat";
  public static final String USERS = "users";
  public static final int MAX_CHAT_TO_SHOW = 20;

  //List<ParseObjectUser> users;
  //this is a relation, not properties, so not defined

  //List<ParseMessage> messages;
  public static final String MESSAGES_KEY = "messages";

  public List<ParseMessage> getMessages() {
    return getList(MESSAGES_KEY);
  }

  public void getLastMessage(final ParseClientAsyncHandler handler) {
    ParseQuery<ParseMessage> query = ParseQuery.getQuery(ParseMessage.class);
    query.setLimit(1);
    query.orderByDescending("_updated_at");
    query.findInBackground(
        new FindCallback<ParseMessage>() {
          @Override
          public void done(List<ParseMessage> objects, ParseException e) {
            if (e == null) {
              handler.onSuccess(objects.get(0));
            } else {
              handler.onFailure(e.getMessage());
            }
          }
        }
    );

  }

  public void setMessages(List<ParseMessage> messages) {
    put(MESSAGES_KEY, messages);
  }

}
