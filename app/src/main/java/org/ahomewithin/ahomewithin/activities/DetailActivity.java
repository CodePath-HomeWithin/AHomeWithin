package org.ahomewithin.ahomewithin.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Item;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 07/03/16.
 */
public class DetailActivity extends MainActivity {
    @Bind(R.id.rlVideoContainer) RelativeLayout rlVideoContainer;
    @Bind(R.id.rlCardContainer) FrameLayout rlCardContainer;

    @Bind(R.id.tvTitle) TextView tvTitle;
    @Bind(R.id.tvQuote) TextView tvQuote;
    @Bind(R.id.tvQuote_author) TextView tvQuote_author;
    @Bind(R.id.tvReflection) TextView tvReflection;
    @Bind(R.id.tvAction) TextView tvAction;

    private MediaFensterPlayerController mediaController;

    private Item mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mItem = (Item) getIntent().getSerializableExtra(Item.SERIALIZABLE_TAG);

        if (mItem.type == Item.VIDEOS) {
            rlCardContainer.setVisibility(View.GONE);

            FensterVideoView fvvVideo = (FensterVideoView) findViewById(R.id.fvvVideo);
            mediaController = (MediaFensterPlayerController)
                    findViewById(R.id.fvMediaPlayerController);

            fvvVideo.setMediaController(mediaController);
            mediaController.setMediaPlayer(fvvVideo);
            fvvVideo.setVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                    MediaFensterPlayerController.DEFAULT_VIDEO_START);
            fvvVideo.start();
            Log.d("DEBUG", "Watching: " + mItem.videoUrl);

            fvvVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaController.show();
                }
            });
        } else {
            rlVideoContainer.setVisibility(View.GONE);



        }

    }
}
