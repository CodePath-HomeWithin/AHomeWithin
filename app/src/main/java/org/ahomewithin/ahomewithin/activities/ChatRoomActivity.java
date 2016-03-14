package org.ahomewithin.ahomewithin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.User;

import java.util.ArrayList;

/**
 * Created by chezlui on 14/03/16.
 */
public class ChatRoomActivity extends AppCompatActivity {

    ListView listView ;
    ArrayList<User> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        ParseClient.newInstance(this).getAllUsers(new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(Object obj) {
                values = (ArrayList<User>) obj;
            }

            @Override
            public void onFailure(String error) {

            }
        });

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter adapter = new ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                User  user    = (User) listView.getItemAtPosition(position);

                Intent intent = new Intent(ChatRoomActivity.this, ChatActivity.class);
                intent.putExtra("otherEmail", user.email);
                startActivity(intent);
            }

        });
    }

}
