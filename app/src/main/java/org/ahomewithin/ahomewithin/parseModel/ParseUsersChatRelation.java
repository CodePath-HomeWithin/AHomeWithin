package org.ahomewithin.ahomewithin.parseModel;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by xiangyang_xiao on 3/20/16.
 */
@ParseClassName("UsersChatRelation")
public class ParseUsersChatRelation extends ParseObject {

    public ParseUsersChatRelation() {
    }

    public static final String PARSE_NAME = "UsersChatRelation";

    //List<ParseObjectUser> users
    //users that are in the chat
    public static final String USERS_KEY = "users";

    //ParseChat chat
    public static final String CHAT_KEY = "chat";

    public List<ParseObjectUser> getUsers() {
        return getList(USERS_KEY);
    }

    public ParseChat getChat() {
        return (ParseChat)getParseObject(CHAT_KEY);
    }

    public void setUsers(List<ParseObjectUser> users) {
        put(USERS_KEY, users);
    }

    public void setChat(ParseChat chat) {
        put(CHAT_KEY, chat);
    }

}
