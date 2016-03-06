package org.ahomewithin.ahomewithin.learnmore;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.ahomewithin.ahomewithin.MainActivity;
import org.ahomewithin.ahomewithin.R;

public class LearnMoreActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout flContainer = (FrameLayout) findViewById(R.id.flContent);
        View v = getLayoutInflater().inflate(R.layout.learn_more_content, null);
        flContainer.addView(v);
    }

}
