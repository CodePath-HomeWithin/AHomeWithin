package org.ahomewithin.ahomewithin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.ahomewithin.ahomewithin.R;

public class HomeActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout flContainer = (FrameLayout) findViewById(R.id.flContent);
        View v = getLayoutInflater().inflate(R.layout.content_home, null);
        flContainer.addView(v);
    }

}

