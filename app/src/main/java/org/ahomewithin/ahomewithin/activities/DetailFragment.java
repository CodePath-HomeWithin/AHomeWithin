package org.ahomewithin.ahomewithin.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.CardsPagerFragment;
import org.ahomewithin.ahomewithin.models.Item;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 07/03/16.
 */
public class DetailFragment extends Fragment {
    @Bind(R.id.rlVideoContainer) RelativeLayout rlVideoContainer;
    @Bind(R.id.flCardsPager) FrameLayout flCardsPager;
    @Bind(R.id.fvvVideo) FensterVideoView fvvVideo;
    @Bind(R.id.fvMediaPlayerController) MediaFensterPlayerController mediaController;


    private Item mItem;

    public static DetailFragment newInstance(Item item) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Item.SERIALIZABLE_TAG, item);
        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View convertView = inflater.inflate(R.layout.fragment_detail, container, false);

        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mItem = (Item) getArguments().getSerializable(Item.SERIALIZABLE_TAG);

        if (mItem.type == Item.ITEM_TYPE.VIDEOS) {
            flCardsPager.setVisibility(View.INVISIBLE);

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
            rlVideoContainer.setVisibility(View.INVISIBLE);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.flContent, CardsPagerFragment.newInstance(mItem))
                    .addToBackStack(null)
                    .commit();

        }
    }

}
