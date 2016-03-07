package org.ahomewithin.ahomewithin.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Recommended;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LearnMoreActivity extends MainActivity {
    @Bind(R.id.flContent) FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        View v = getLayoutInflater().inflate(R.layout.content_learn_more, null);
        flContainer.addView(v);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View v = super.onCreateView(parent, name, context, attrs);
        loadRecommendations();
        return v;
    }
    
    private void loadRecommendations() {
        try {
            JSONObject jsonObj = AHomeWithinClient.getRecommendations(this);
            List<Recommended> recommendations =
                    Recommended.fromJSONArray(jsonObj.getJSONArray("recommendations"));
            System.out.println(recommendations.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
