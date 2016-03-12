package org.ahomewithin.ahomewithin.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Item;

/**
 * Created by chezlui on 07/03/16.
 */
public class DetailActivity extends MainActivity {

    private MediaFensterPlayerController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Item item = (Item) getIntent().getSerializableExtra(Item.SERIALIZABLE_TAG);

        if (item.type == 0) {
            FensterVideoView fvvVideo = (FensterVideoView) findViewById(R.id.fvvVideo);
            mediaController = (MediaFensterPlayerController)
                    findViewById(R.id.fvMediaPlayerController);

            fvvVideo.setMediaController(mediaController);
            mediaController.setMediaPlayer(fvvVideo);
            fvvVideo.setVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                    MediaFensterPlayerController.DEFAULT_VIDEO_START);
            fvvVideo.start();
            Log.d("DEBUG", "Watching: " + item.contentUrl);

            fvvVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaController.show();
                }
            });
        } else {
            //myWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + item.contentUrl);
            Log.d("DEBUG", "Watching: " + item.contentUrl);

        }

    }
}
