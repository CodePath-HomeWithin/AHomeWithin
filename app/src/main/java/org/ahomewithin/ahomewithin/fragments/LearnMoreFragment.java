package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ahomewithin.ahomewithin.R;

public class LearnMoreFragment extends Fragment {

    public static LearnMoreFragment newInstance() {
        LearnMoreFragment learnMoreFragment = new LearnMoreFragment();
        return learnMoreFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.content_learn_more, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.recommended);
        if (savedInstanceState == null) {
            RecommendedFragment fragmentRecommendations = RecommendedFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.flRecommended, fragmentRecommendations).commit();
        }

        return convertView;
    }

}
