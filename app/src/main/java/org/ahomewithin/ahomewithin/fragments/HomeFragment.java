package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaviofaria.kenburnsview.KenBurnsView;

import org.ahomewithin.ahomewithin.R;

public class HomeFragment extends Fragment {

    public static final String FRAGMENT_TAG = HomeFragment.class.getSimpleName();

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.content_home, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home);

        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final KenBurnsView kbvNearYou = (KenBurnsView) view.findViewById(R.id.ivMenuImageMap);
        final KenBurnsView kbvLibrary = (KenBurnsView) view.findViewById(R.id.ivMenuImageLibrary);
        final KenBurnsView kbvTools = (KenBurnsView) view.findViewById(R.id.ivMenuImageTools);


        Handler pauser = new Handler();
        pauser.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 10 seconds
                kbvLibrary.pause();
                kbvTools.pause();
            }
        }, 250);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 10 seconds
                kbvNearYou.pause();
                kbvLibrary.resume();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        kbvLibrary.pause();
                        kbvTools.resume();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // Actions to do after 10 seconds
                                kbvTools.pause();
                                kbvNearYou.resume();
                            }
                        }, 10000);
                    }
                }, 10000);
            }
        }, 10000);



//        kbvNearYou.restart();
//        kbvLibrary.restart();
//        kbvTools.restart();
//        kbvLibrary.pause();
//        kbvTools.pause();
////
//        kbvNearYou.resume();
//        kbvNearYou.restart();
//
//        kbvNearYou.setTransitionListener(new MyKenBurnsView.TransitionListener() {
//            @Override
//            public void onTransitionStart(Transition transition) {
//                kbvTools.pause();
//            }
//
//            @Override
//            public void onTransitionEnd(Transition transition) {
//                kbvLibrary.resume();
//            }
//        });
//
//        kbvLibrary.setTransitionListener(new MyKenBurnsView.TransitionListener() {
//            @Override
//            public void onTransitionStart(Transition transition) {
//                kbvNearYou.pause();
//            }
//
//            @Override
//            public void onTransitionEnd(Transition transition) {
//                kbvTools.resume();
//            }
//        });
//
//        kbvTools.setTransitionListener(new MyKenBurnsView.TransitionListener() {
//            @Override
//            public void onTransitionStart(Transition transition) {
//                kbvLibrary.pause();
//            }
//
//            @Override
//            public void onTransitionEnd(Transition transition) {
//                kbvNearYou.resume();
//            }
//        });
    }
}

