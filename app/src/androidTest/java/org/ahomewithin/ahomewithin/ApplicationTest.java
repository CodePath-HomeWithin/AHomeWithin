package org.ahomewithin.ahomewithin;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

  public ApplicationTest() {
    super(Application.class);
  }

  /*
  //save a new item
  ParseClient client = ParseClient.newInstance(this);
  //client.testQuery();
  //client.test();
  Item video = new Item();
  video.title="testVideo1";
  video.description="this is a test video";
  video.imageUrl="null";
  video.type=0;
  video.contentUrl="null";
  video.price=100.00;


  Item card = new Item();
  card.title="testCard1";
  card.description="this is a test card";
  card.imageUrl="null";
  card.type=1;
  card.price=100.00;
  card.content=Item.getJsonStringFromCardContent(CardContent.getDefault());
  client.addItems(new ArrayList<Item>(Arrays.asList(video,card)),
      new

  ParseClientAsyncHandler() {
    @Override
    public void onSuccess (Object obj){
      Log.i("xxy_item", "haha");
    }

    @Override
    public void onFailure (String error){
      Log.e("xxy_item", error);
    }
  }
  */

  /*
  //get all purchasable items
          client.getPurchasableItems(new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(Object obj) {
                List<Item> items = (List<Item>)obj;
                for(Item item : items) {
                    Log.e("xxy_item", item.title);

                }

            }

            @Override
            public void onFailure(String error) {

            }
        });
   */


  /*
  //purchase an item
  ParseClient client = ParseClient.newInstance(getApplicationContext());
  //client.testQuery();
  //client.test();
  client.purchaseItem(item,new

  ParseClientAsyncHandler() {
    @Override
    public void onSuccess (Object obj){
      Log.e("xxy", "purchased");

    }

    @Override
    public void onFailure (String error){
      Log.e("xxy", "purchase_failed");

    }
  }

*/

  /*
  client.getPurchasedItems(
      new

  ParseClientAsyncHandler() {
    @Override
    public void onSuccess (Object obj){
      List<Item> items = (List<Item>) obj;
      for (Item item : items) {
        Log.i("xxy_item", item.title);
      }
    }

    @Override
    public void onFailure (String error){

    }
  }

*/

  /*
  ParseClient client = ParseClient.newInstance(this);
  client.setUserLocation(9,9,new

  ParseClientAsyncHandler() {
    @Override
    public void onSuccess (Object obj){
      Log.e("xxy_loc", "done");

    }

    @Override
    public void onFailure (String error){

    }
  }

*/

  /*
  ParseClient client = ParseClient.newInstance(this);
  client.setUserLocation(9,9,new

  ParseClientAsyncHandler() {
    @Override
    public void onSuccess (Object obj){
      Log.e("xxy_loc", "done");

    }

    @Override
    public void onFailure (String error){

    }
  }

  );

*/

  /*
  //test send message
        client.sentMessage("second message chat", "def@a.com", new ParseClientAsyncHandler() {
        @Override
        public void onSuccess(Object obj) {
          Log.e("xxy_chat", "sent");
        }

        @Override
        public void onFailure(String error) {
          Log.e("xxy_chat", error);
        }
      });
*/

}


/*

//test query for parse in ParseClient
  public void testQuery() {
    ParseQuery<ParseObject> query =
        ParseQuery.getQuery("test");
    query.whereEqualTo("_id", "eNFNPy0cJ2");
    query.findInBackground(
        new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {
            if (e == null) {
              ParseObject object = objects.get(0);
              List<String> othersList = object.getList("others");
              othersList.add("sfafssf");
              object.put("others", othersList);
              object.saveInBackground();
              Log.e("xxy grea job", object.get("name").toString());
            } else {
              Log.e("parse", e.getMessage());
            }
          }
        }
    );
  }

  public void test() {
    ParseObject testObj = new ParseObject("test");
    ParseObject anotherTestObj = new ParseObject("user");
    ParseObject thirdObj = new ParseObject("third");
    List<ParseObject> parseRefList = new ArrayList<>();
    List<String> tstString = new ArrayList<>(Arrays.asList("abc", "def"));
    parseRefList.add(anotherTestObj);
    parseRefList.add(thirdObj);
    anotherTestObj.put("email", "no@abc.com");
    testObj.put("name", "xxy");
    testObj.put("hobby", "test");
    testObj.put("date", "test");
    testObj.put("others", tstString);
    testObj.saveInBackground(
        new SaveCallback() {
          @Override
          public void done(ParseException e) {
            //System.out.println("xxy:"+e.getMessage());
          }
        }

    );
  }
 */


/*
// Retrieve a list of chat for the current user
// For each chat, get both users and last message
      client.getChatHistory(new ParseClientAsyncHandler() {
        @Override
        public void onSuccess(Object obj) {
          List<ParseChat> chats = (List<ParseChat>) obj;
          for (ParseChat chat : chats) {
            ParseRelation relation = chat.getRelation(ParseChat.USERS);
            ParseQuery query = relation.getQuery();
            query.findInBackground(
                new FindCallback() {
                  @Override
                  public void done(List objects, ParseException e) {

                  }

                  @Override
                  public void done(Object o, Throwable throwable) {

                    List<ParseObjectUser> users = (List<ParseObjectUser>)o;
                    for (ParseObjectUser user : users) {
                      try {
                        user.fetchIfNeeded();
                      } catch (Exception parseExp) {
                        Log.i("xxy_chat_usr", parseExp.getMessage());
                      }
                      Log.i("xxy_chat_usr", user.getName());
                    }
                  }
                }
            );
            chat.getLastMessage(
                new ParseClientAsyncHandler() {
                  @Override
                  public void onSuccess(Object obj) {
                    ParseMessage message = (ParseMessage) obj;
                    Log.e("xxy_message", message.getBody());
                  }

                  @Override
                  public void onFailure(String error) {
                  }
                });
          }
        }

        @Override
        public void onFailure(String error) {

        }
      });
 */