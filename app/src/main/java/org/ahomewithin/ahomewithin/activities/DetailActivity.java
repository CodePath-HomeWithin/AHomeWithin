package org.ahomewithin.ahomewithin.activities;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Item;

import butterknife.ButterKnife;

/**
 * Created by chezlui on 07/03/16.
 */
public class DetailActivity extends MainActivity {

   // @Bind(R.id.webview) WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Item item = (Item) getIntent().getSerializableExtra(Item.SERIALIZABLE_TAG);
        WebView myWebView = (WebView) findViewById(R.id.webview);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=http://www.stagecoachbus.com/PdfUploads/Timetable_28768_5.pdf");

        if (myWebView != null) {
        } else {
            if (item.type == 0) {
                myWebView.loadUrl(item.contentUrl);
                Log.d("DEBUG", "Watching: " + item.contentUrl);
            } else {
                myWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + item.contentUrl);
                Log.d("DEBUG", "Watching: " + item.contentUrl);

            }
            Log.d("DEBUG", "webview was null");
        }
    }
}
