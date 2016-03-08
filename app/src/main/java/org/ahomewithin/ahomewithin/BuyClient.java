package org.ahomewithin.ahomewithin;

import com.loopj.android.http.AsyncHttpResponseHandler;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by barbara on 3/5/16.
 */
public class BuyClient {
    private static BuyClient client;

    private BuyClient() {

    }

    public static BuyClient getRestClient(){
        if (client == null) {
            client = new BuyClient();
        }
        return client;
    }

    // TODO always return HTTP_OK
    public void buy(AsyncHttpResponseHandler responseHandler, String name, String cardNumber, String expireDate, String cvc) {
        responseHandler.onSuccess(HttpsURLConnection.HTTP_OK, null, null);
    }



}
