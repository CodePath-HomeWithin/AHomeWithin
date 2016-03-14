package org.ahomewithin.ahomewithin.mongo;

import android.util.Log;

import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

import org.bson.Document;

import java.net.URLEncoder;

/**
 * Created by xiangyang_xiao on 3/12/16.
 */

//The MongoDbClient used for writing
//due to the limitations of parse on uniqueness
//and other constraints
public class MongoDbClient {

  private static MongoDbClient mongoDbClient;
  private static Object lock = new Object();

  private MongoClient mongoClient;

  private MongoDbClient() {


    try {
      ConnectionString uri = new ConnectionString(
          String.format(
              "mongodb://%s:%s@ds011218.mlab.com:11218/?authoSource=heroku_3zlx41mm",
              "xxy",
              URLEncoder.encode("pass@word", "UTF-8")
          )
      );
      mongoClient = MongoClients.create(uri);
      MongoDatabase database = mongoClient.getDatabase("heroku_3zlx41mm");
      MongoCollection<Document> collection = database.getCollection("test");
      Block<Document> printDocumentBlock = new Block<Document>() {
        @Override
        public void apply(final Document document) {
          Log.i("mongo", document.toJson());
        }
      };
      SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
        @Override
        public void onResult(final Void result, final Throwable t) {
          Log.i("mongo", "Operation Finished!");
        }
      };

      collection.find().forEach(printDocumentBlock, callbackWhenFinished);
    } catch (Exception e) {
      Log.e("mongo", e.toString());

    }

  }

  public static MongoDbClient newInstance() {
    if(mongoDbClient == null) {
      synchronized (lock) {
        if(mongoDbClient == null) {
          mongoDbClient = new MongoDbClient();
        }
      }
    }
    return mongoDbClient;
  }
}
