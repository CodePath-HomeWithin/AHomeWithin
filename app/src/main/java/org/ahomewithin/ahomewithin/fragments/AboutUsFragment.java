package org.ahomewithin.ahomewithin.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.util.CircularFragReveal;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by barbara on 3/13/16.
 */
// TODO this animation will be nice if time https://guides.codepath.com/android/Circular-Reveal-Animation
public class AboutUsFragment extends Fragment {

    public static final String FRAGMENT_TAG = AboutUsFragment.class.getSimpleName();

    @Bind(R.id.ivAboutUs1) ImageView ivAboutUs1;
    @Bind(R.id.ivAboutUs2) ImageView ivAboutUs2;
    @Bind(R.id.ivAboutUs3) ImageView ivAboutUs3;

    public static AboutUsFragment newInstance() {
        AboutUsFragment aboutUsFragment = new AboutUsFragment();
        return aboutUsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.content_about_us, container, false);
        ButterKnife.bind(this, convertView);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.about_us);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int size = metrics.widthPixels/2;

        Glide.with(getActivity())
                .load(R.drawable.aboutus_1)
                .asBitmap()
                .centerCrop()
                .override(size, size)
                .into(new BitmapImageViewTarget(ivAboutUs1) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                ivAboutUs1.setImageDrawable(circularBitmapDrawable);
            }
        });

        Glide.with(getActivity())
                .load(R.drawable.aboutus_2)
                .asBitmap()
                .centerCrop()
                .override(size, size)
                .into(new BitmapImageViewTarget(ivAboutUs2) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivAboutUs2.setImageDrawable(circularBitmapDrawable);
                    }
                });

        Glide.with(getActivity())
                .load(R.drawable.aboutus_3)
                .asBitmap()
                .centerCrop()
                .override(size, size)
                .into(new BitmapImageViewTarget(ivAboutUs3) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ivAboutUs3.setImageDrawable(circularBitmapDrawable);
                    }
                });

        CircularFragReveal.Builder builder = new CircularFragReveal.Builder(view);
        builder.setRevealTime(2000);
        CircularFragReveal circularFragReveal = builder.build();

        View image = getView().findViewById(R.id.ivAboutUs1);
        int location[] = new int[2];
        image.getLocationOnScreen(location);
        circularFragReveal.startReveal(location[0], location[1], new CircularFragReveal.OnCircularReveal() {
            @Override
            public void onFragCircRevealStart() {

            }

            @Override
            public void onFragCircRevealEnded() {

            }

            @Override
            public void onFragCircUnRevealStart() {

            }

            @Override
            public void onFragCircUnRevealEnded() {

            }
        });
    }
}
