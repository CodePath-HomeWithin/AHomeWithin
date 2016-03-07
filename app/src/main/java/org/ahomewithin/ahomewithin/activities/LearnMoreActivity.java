package org.ahomewithin.ahomewithin.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.RecommendedFragment;
import org.ahomewithin.ahomewithin.models.Recommended;
import org.json.JSONObject;

import java.util.List;

public class LearnMoreActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout flContainer = (FrameLayout) findViewById(R.id.flContent);
        View v = getLayoutInflater().inflate(R.layout.content_learn_more, null);
        flContainer.addView(v);

        if (savedInstanceState == null) {
            RecommendedFragment fragmentRecommendations = RecommendedFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flRecommended, fragmentRecommendations).commit();
        }
    }

}
