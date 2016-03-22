package org.ahomewithin.ahomewithin.activities;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Item;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 07/03/16.
 */
public class VideoActivity extends AppCompatActivity {
    @Bind(R.id.rlVideoContainer) RelativeLayout rlVideoContainer;
    @Bind(R.id.fvvVideo) FensterVideoView fvvVideo;
    @Bind(R.id.fvMediaPlayerController) MediaFensterPlayerController mediaController;


    private Item mItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        mItem = (Item) getIntent().getSerializableExtra(Item.SERIALIZABLE_TAG);

        if (mItem.type == Item.ITEM_TYPE.VIDEOS) {
            fvvVideo.setMediaController(mediaController);
            mediaController.setMediaPlayer(fvvVideo);

            AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.video);
            fvvVideo.setVideo(assetFileDescriptor, MediaFensterPlayerController.DEFAULT_VIDEO_START);

//            fvvVideo.setVideo(mItem.contentUrl,
//                    MediaFensterPlayerController.DEFAULT_VIDEO_START);
//
//            fvvVideo.setVideo("https://s3-eu-west-1.amazonaws.com/chezlui.freelancer/codepath/video.mp4",
//                    MediaFensterPlayerController.DEFAULT_VIDEO_START);


            fvvVideo.start();
            Log.d("DEBUG", "Watching: " + mItem.contentUrl);

            fvvVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaController.show();
                }
            });
        }
    }
}
