package org.ahomewithin.ahomewithin.home;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.ahomewithin.ahomewithin.MainActivity;
import org.ahomewithin.ahomewithin.R;

public class HomeActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout flContainer = (FrameLayout) findViewById(R.id.flContent);
        View v = getLayoutInflater().inflate(R.layout.home_content, null);
        flContainer.addView(v);
    }

}

