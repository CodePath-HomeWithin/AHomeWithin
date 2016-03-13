package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ahomewithin.ahomewithin.R;

import butterknife.Bind;

/**
 * Created by barbara on 3/13/16.
 */
public class AboutUsFragment extends Fragment {
    @Bind(R.id.toolbar) Toolbar toolbar;

    public static AboutUsFragment newInstance() {
        AboutUsFragment aboutUsFragment = new AboutUsFragment();
        return aboutUsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.content_about_us, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.about_us);
        return convertView;
    }

}
