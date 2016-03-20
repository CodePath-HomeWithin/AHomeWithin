package org.ahomewithin.ahomewithin;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.interceptors.ParseLogInterceptor;

import org.ahomewithin.ahomewithin.models.Item;
import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.parseModel.ParseChat;
import org.ahomewithin.ahomewithin.parseModel.ParseItem;
import org.ahomewithin.ahomewithin.parseModel.ParseMessage;
import org.ahomewithin.ahomewithin.parseModel.ParseObjectUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyang_xiao on 3/12/16.
 */
public class ParseClient {

    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private static ParseClient client;
    private static Object lock = new Object();

    private ParseObjectUser curParseObjectUser;
    private String curParseObjectUserEmailAddress;
    private Handler messageRunnableHandler;

    public ParseClient(Context context) {

        ParseObject.registerSubclass(ParseObjectUser.class);
        ParseObject.registerSubclass(ParseMessage.class);
        ParseObject.registerSubclass(ParseItem.class);
        ParseObject.registerSubclass(ParseChat.class);

        Parse.initialize(new Parse.Configuration.Builder(context)
            .applicationId("myAppId") // should correspond to APP_ID env variable
            .clientKey("myMasterKey")  // set explicitly unless clientKey is explicitly configured on Parse server
            .addNetworkInterceptor(new ParseLogInterceptor())
            .server("https://chatcodepath.herokuapp.com/parse/").build());
    }

    public void setMessageRunnableHandler(Handler handler) {
        messageRunnableHandler = handler;
    }

    public static ParseClient newInstance(Context context) {
        if (client == null) {
            synchronized (lock) {
                if (client == null) {
                    client = new ParseClient(context);
                }

            }
        }
        return client;
    }

    public ParseObjectUser getCurParseObjectUser() {
        return curParseObjectUser;
    }

    public void login(final String email, String password, final ParseClientAsyncHandler handler) {
        ParseUser.logInInBackground(email, password,
            new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        curParseObjectUserEmailAddress = email;
                        getCurrentUser(handler);
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            });
    }

    public void signup(final User user, String password, final ParseClientAsyncHandler handler) {
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(user.email);
        parseUser.setPassword(password);
        parseUser.setEmail(user.email);
        parseUser.signUpInBackground(
            new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ParseObjectUser parseObjectUser =
                            (ParseObjectUser) ParseObject.create(ParseObjectUser.PARSE_NAME);
                        parseObjectUser.updateUser(user);
                        parseObjectUser.saveInBackground(
                            new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        handler.onSuccess(null);
                                    } else {
                                        handler.onFailure(e.getMessage());
                                    }
                                }
                            }
                        );
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );
    }

    public void requestResetPassword(final String email, final ParseClientAsyncHandler handler) {
        ParseUser.requestPasswordResetInBackground(
            email, new RequestPasswordResetCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        handler.onSuccess(null);
                    } else {
                        handler.onFailure(e.getMessage());
                    }

                }
            }
        );
    }

    //different from ParseUser.getCurrentUser()
    //as the ParseObject that is retrieved here is ParseObjectUser type
    private void getCurrentUser(final ParseClientAsyncHandler handler) {
        //assume when it reaches here, the user is always logged in, which
        //should be checked before calling this method
        //as we use email as the user name in ParseObjectUser to enforce uniqueness
        ParseQuery<ParseObjectUser> query =
            ParseQuery.getQuery(ParseObjectUser.class);
        query.whereEqualTo("email", curParseObjectUserEmailAddress);
        query.findInBackground(
            new FindCallback<ParseObjectUser>() {
                @Override
                public void done(List<ParseObjectUser> users, ParseException e) {
                    if (e == null && !users.isEmpty()) {
                        curParseObjectUser = users.get(0);
                        User.setCurrentUser(curParseObjectUser);
                        handler.onSuccess(null);
                    } else if (e != null) {
                        Log.e("xxy_test", e.getMessage());
                    } else {
                        Log.e("xxy_test", "empty");
                    }
                }
            }
        );
    }

    public void logout() {
        if (isUserLoggedIn()) {
            curParseObjectUser = null;
            ParseUser.logOutInBackground();
        }
        stopHanlder();
    }

    public void stopHanlder() {
        if (messageRunnableHandler != null) {
            messageRunnableHandler.removeCallbacksAndMessages(null);
            messageRunnableHandler = null;
        }
    }

    public boolean isUserLoggedIn() {
        return curParseObjectUser != null;
    }

    public void changeCredentials(String newEmail, String newPassword, final ParseClientAsyncHandler handler) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (newEmail.equals(currentUser.getEmail()) && newPassword.equals(User.PWD_HOLDER)) {
            handler.onSuccess(null);
            return;
        }
        currentUser.setUsername(newEmail);
        if (!newPassword.equals(User.PWD_HOLDER)) {
            currentUser.setPassword(newPassword);
        }
        currentUser.setEmail(newEmail);
        currentUser.saveInBackground(
            new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        handler.onSuccess(null);
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );
    }

    public void updateUserInfo(final User newUser, String newPassword, final ParseClientAsyncHandler handler) {
        changeCredentials(newUser.email, newPassword,
            new ParseClientAsyncHandler() {
                @Override
                public void onSuccess(Object obj) {
                    curParseObjectUser.updateUserBasicInfo(newUser);
                    curParseObjectUser.saveInBackground(
                        new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    User.setCurrentUser(curParseObjectUser);
                                    handler.onSuccess(null);
                                } else {
                                    handler.onFailure(e.getMessage());
                                }
                            }
                        }
                    );
                }

                @Override
                public void onFailure(String error) {
                    handler.onFailure(error);
                }
            });

    }

    public void getPurchasableItems(final ParseClientAsyncHandler handler) {
        ParseQuery<ParseItem> query =
            ParseQuery.getQuery(ParseItem.class);
        query.findInBackground(
            new FindCallback<ParseItem>() {
                @Override
                public void done(List<ParseItem> items, ParseException e) {
                    if (e == null) {
                        List<Item> normalItems = new ArrayList<Item>();
                        for (ParseItem parseItem : items) {
                            normalItems.add(Item.getNewInstanceFromParseObject(parseItem));
                        }
                        handler.onSuccess(normalItems);
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );
    }

    public void getAllUsers(final ParseClientAsyncHandler handler) {
        ParseQuery<ParseObjectUser> query =
            ParseQuery.getQuery(ParseObjectUser.class);
        query.findInBackground(
            new FindCallback<ParseObjectUser>() {
                @Override
                public void done(List<ParseObjectUser> users, ParseException e) {
                    if (e == null) {
                        List<User> normalUsers = new ArrayList<>();
                        for (ParseObjectUser oneUser : users) {
                            normalUsers.add(User.getNewInstanceFromParseObject(oneUser));
                        }
                        handler.onSuccess(normalUsers);
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );
    }

    public void addItem(Item item, final ParseClientAsyncHandler handler) {
        ParseItem parseItem = (ParseItem) ParseObject.create(ParseItem.PARSE_NAME);
        parseItem.update(item);
        parseItem.saveInBackground(
            new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        handler.onSuccess(null);
                    } else {
                        handler.onFailure(null);
                    }
                }
            }
        );
    }

    public void addItems(List<Item> items, ParseClientAsyncHandler handler) {
        for (Item item : items) {
            addItem(item, handler);
        }
    }

    public void purchaseItem(final String id, final ParseClientAsyncHandler handler) {
        if (curParseObjectUser == null) {
            handler.onFailure("the current user info is not available; Please try again later");
            return;
        }

        getParseObjectsFromId(id, ParseItem.PARSE_NAME,
            new ParseClientAsyncHandler() {
                @Override
                public void onSuccess(Object obj) {
                    ParseItem parseItem = (ParseItem) obj;
                    List<ParseItem> curItems = curParseObjectUser.getItems();
                    curItems.add(parseItem);
                    curParseObjectUser.setItems(curItems);
                    curParseObjectUser.saveInBackground(
                        new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    handler.onSuccess(null);
                                } else {
                                    handler.onFailure(e.getMessage());
                                }
                            }
                        }
                    );
                }

                @Override
                public void onFailure(String error) {

                }
            });
    }

    public void getPurchasedItems(final ParseClientAsyncHandler handler) {
        if (curParseObjectUser == null) {
            handler.onFailure("the current user info is not available; Please try again later");
            return;
        }
        List<Item> items = new ArrayList<>();

        try {
            for (ParseItem parseItem : curParseObjectUser.getItems()) {
                parseItem.fetchIfNeeded();
                items.add(Item.getNewInstanceFromParseObject(parseItem));
            }
        } catch (Exception e) {
            handler.onFailure(e.getMessage());
        }
        handler.onSuccess(items);
    }

    private void getParseObjectsFromId(final String id, String collectionName, final ParseClientAsyncHandler handler) {
        ParseQuery<ParseObject> query =
            ParseQuery.getQuery(collectionName);
        query.whereEqualTo("_id", id);
        query.findInBackground(
            new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (!objects.isEmpty()) {
                            handler.onSuccess(objects.get(0));
                        } else {
                            handler.onFailure(
                                String.format(
                                    "the object with id %s does not exist",
                                    id
                                )
                            );
                        }
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );

    }


    public void setUserLocation(long lon, long lat, final ParseClientAsyncHandler handler) {
        ParseGeoPoint newLocation = new ParseGeoPoint(lat, lon);
        curParseObjectUser.setGeo(newLocation);
        curParseObjectUser.saveInBackground(
            new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        handler.onSuccess(null);
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );
    }

    public void getChatHistory(final ParseClientAsyncHandler handler) {
        ParseQuery<ParseChat> query = ParseQuery.getQuery(ParseChat.class);

        query.whereEqualTo(ParseChat.USERS, curParseObjectUser);
        query.setLimit(ParseChat.MAX_CHAT_TO_SHOW);
        query.orderByDescending("_updated_at");
        query.findInBackground(
            new FindCallback<ParseChat>() {
                @Override
                public void done(List<ParseChat> objects, ParseException e) {
                    if (e == null) {
                        handler.onSuccess(objects);
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );
    }

    public void startChat(ParseObjectUser other, final ParseClientAsyncHandler handler) {
        final ParseChat newChat = (ParseChat) ParseObject.create(ParseChat.PARSE_NAME);
        newChat.setMessages(new ArrayList<ParseMessage>());

        ParseRelation<ParseObjectUser> usersInChat = newChat.getRelation(ParseChat.USERS);
        usersInChat.add(curParseObjectUser);
        usersInChat.add(other);
        newChat.saveInBackground(
            new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        handler.onSuccess(newChat);
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );
    }

    private void addMessageToChat(String messageContent, final ParseChat chat, final ParseClientAsyncHandler handler) {
        final ParseMessage message = (ParseMessage) ParseObject.create(ParseMessage.PARSE_NAME);
        message.setUserId(curParseObjectUser.getObjectId());
        message.setBody(messageContent);
        message.saveInBackground(
            new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        List<ParseMessage> parseMessages = chat.getMessages();
                        parseMessages.add(message);
                        chat.setMessages(parseMessages);
                        chat.saveInBackground(
                            new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        handler.onSuccess(null);
                                    } else {
                                        handler.onFailure(e.getMessage());
                                    }
                                }
                            }
                        );
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );
    }

    public void getParseObjectUserFromEmail(final String email, final ParseClientAsyncHandler handler) {
        ParseQuery<ParseObjectUser> query =
            ParseQuery.getQuery(ParseObjectUser.PARSE_NAME);
        query.whereEqualTo("email", email);
        query.findInBackground(
            new FindCallback<ParseObjectUser>() {
                @Override
                public void done(List<ParseObjectUser> objects, ParseException e) {
                    if (e == null) {
                        if (!objects.isEmpty()) {
                            handler.onSuccess(objects.get(0));
                        } else {
                            handler.onFailure(
                                String.format(
                                    "the user with email %s does not exist",
                                    email
                                )
                            );
                        }
                    } else {
                        handler.onFailure(e.getMessage());
                    }
                }
            }
        );

    }

    public void sentMessage(final String messageContent, final String otherEmail, final ParseClientAsyncHandler handler) {
        getParseObjectUserFromEmail(otherEmail, new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(final Object obj) {
                ParseQuery<ParseChat> query = ParseQuery.getQuery(ParseChat.class);
                query.whereEqualTo(ParseChat.USERS, curParseObjectUser);
                query.whereEqualTo(ParseChat.USERS, (ParseObjectUser) obj);
                query.findInBackground(
                    new FindCallback<ParseChat>() {
                        @Override
                        public void done(List<ParseChat> objects, ParseException e) {
                            if (e == null) {
                                if (objects.isEmpty()) {
                                    startChat((ParseObjectUser) obj, new ParseClientAsyncHandler() {
                                        @Override
                                        public void onSuccess(Object obj) {
                                            ParseChat newChat = (ParseChat) obj;
                                            addMessageToChat(messageContent, newChat, handler);
                                        }

                                        @Override
                                        public void onFailure(String error) {
                                            handler.onFailure(error);
                                        }
                                    });
                                } else {
                                    addMessageToChat(messageContent, objects.get(0), handler);
                                }

                            } else {
                                handler.onFailure(e.getMessage());
                            }
                        }
                    }

                );
            }

            @Override
            public void onFailure(String error) {
                handler.onFailure(error);
            }
        });

    }

    public void getPastMessages(final String otherEmail, final ParseClientAsyncHandler handler) {
        getParseObjectUserFromEmail(otherEmail, new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(final Object obj) {
                ParseQuery<ParseChat> query = ParseQuery.getQuery(ParseChat.class);
                query.whereEqualTo(ParseChat.USERS, curParseObjectUser);
                query.whereEqualTo(ParseChat.USERS, (ParseObjectUser) obj);
                // Configure limit and sort order
                query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
                query.orderByAscending("createdAt");
                query.findInBackground(
                    new FindCallback<ParseChat>() {
                        @Override
                        public void done(List<ParseChat> objects, ParseException e) {
                            if (e == null) {
                                if (objects.isEmpty()) {
                                    handler.onSuccess(new ArrayList<ParseMessage>());
                                } else {
                                    handler.onSuccess(objects.get(0).getMessages());
                                }

                            } else {
                                handler.onFailure(e.getMessage());
                            }
                        }
                    }

                );
            }

            @Override
            public void onFailure(String error) {
                handler.onFailure(error);
            }
        });

    }


}
