package org.ahomewithin.ahomewithin.services;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.ahomewithin.ahomewithin.MainActivity;
import org.ahomewithin.ahomewithin.R;

/**
 * Created by barbara on 3/4/16.
 */
public class ServicesActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout flContainer = (FrameLayout) findViewById(R.id.flContent);
        View v = getLayoutInflater().inflate(R.layout.services_content, null);
        flContainer.addView(v);
    }

}
