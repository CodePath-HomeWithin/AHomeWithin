package org.ahomewithin.ahomewithin.models;

import android.os.Message;

import java.util.ArrayList;

/**
 * Created by chezlui on 10/03/16.
 */
public class Conversation {
    public String parseId; // TODO delete if not needed Xiangyang
    public String emmisor;  // User ID according to PARSE
    public String receptor; // User ID according to PARSE
    public ArrayList<Message> messages;
}
