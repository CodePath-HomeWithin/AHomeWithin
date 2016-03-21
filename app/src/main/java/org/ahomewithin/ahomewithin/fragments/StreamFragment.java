package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.adapters.DividerItemDecoration;
import org.ahomewithin.ahomewithin.adapters.ItemsStreamAdapter;
import org.ahomewithin.ahomewithin.models.Item;
import org.ahomewithin.ahomewithin.util.UserTools;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;

/**
 * Created by chezlui on 06/03/16.
 */
public class StreamFragment extends Fragment {
    public static final String FRAGMENT_TAG = StreamFragment.class.getSimpleName();

    public static final String ARG_STREAM_TYPE = "stream_type";
    public static final String ARG_OWNED_ITEMS = "my_library";
    public Item.ITEM_TYPE type;
    public boolean showOnlyOwned;
    ArrayList<Item> items;
    ItemsStreamAdapter aItems;
    @Bind(R.id.rvStream) RecyclerView rvStream;

    public static StreamFragment newInstance(int streamType, boolean showOnlyOwned) {
        StreamFragment streamFragment = new StreamFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_STREAM_TYPE, streamType);
        args.putBoolean(ARG_OWNED_ITEMS, showOnlyOwned);
        streamFragment.setArguments(args);
        return streamFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View convertView = inflater.inflate(R.layout.fragment_stream, container, false);

        ButterKnife.bind(this, convertView);

//        convertView.setFocusableInTouchMode(true);
//        convertView.requestFocus();
//        convertView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    getChildFragmentManager().popBackStack();
//                    return false;
//                }
//                return false;
//            }
//        });

        return convertView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        type = Item.ITEM_TYPE.values()[(getArguments().getInt(ARG_STREAM_TYPE))];
        showOnlyOwned = getArguments().getBoolean(ARG_OWNED_ITEMS);

        aItems = new ItemsStreamAdapter(getActivity(), new ArrayList<Item>());

        rvStream.setAdapter(aItems);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvStream.setLayoutManager(llm);

        /* Stubbed out method, parse method commented forward */
        items = Item.fromJson(AHomeWithinClient.getStreams(getActivity(), type), type);
        items = getSubsetOfItems(items, type, showOnlyOwned);
        aItems.addAll(items);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        rvStream.addItemDecoration(itemDecoration);
        rvStream.setItemAnimator(new FlipInBottomXAnimator());

        TextView emptyView = (TextView) view.findViewById(R.id.empty_view);
        ImageView goToTools = (ImageView) view.findViewById(R.id.ivToolsAndTraining);

        if (items.isEmpty()) {
            rvStream.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            goToTools.setVisibility(View.VISIBLE);
        } else {
            rvStream.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            goToTools.setVisibility(View.GONE);
        }

        goToTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, StreamPagerFragment.newInstance(
                                StreamPagerFragment.ViewType.STORE, false))
                        .commit();
            }
        });

//        ParseClient.newInstance(getActivity()).getPurchasableItems(new ParseClientAsyncHandler() {
//            @Override
//            public void onSuccess(Object obj) {
//                items = (ArrayList<Item>) obj;
//
//                items = getSubsetOfItems(items, type, showOnlyOwned);
//                // stubbed out method
//                //items = Item.fromJson(AHomeWithinClient.getStreams(getActivity(), type), type);
//                aItems.addAll(items);
//
//                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
//                rvStream.addItemDecoration(itemDecoration);
//                rvStream.setItemAnimator(new FlipInBottomXAnimator());
//
//                TextView emptyView = (TextView) view.findViewById(R.id.empty_view);
//                ImageView goToTools = (ImageView) view.findViewById(R.id.ivToolsAndTraining);
//
//                if (items.isEmpty()) {
//                    rvStream.setVisibility(View.GONE);
//                    emptyView.setVisibility(View.VISIBLE);
//                    goToTools.setVisibility(View.VISIBLE);
//                } else {
//                    rvStream.setVisibility(View.VISIBLE);
//                    emptyView.setVisibility(View.GONE);
//                    goToTools.setVisibility(View.GONE);
//                }
//
//                goToTools.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.flContent, StreamPagerFragment.newInstance(
//                                        StreamPagerFragment.ViewType.STORE, false))
//                                .commit();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(String error) {
//
//            }
//        });

    }

    // TODO Add show only owned
    private ArrayList<Item> getSubsetOfItems(ArrayList<Item> items, Item.ITEM_TYPE type, boolean showOnlyOwned) {
        ArrayList<Item> auxItems = new ArrayList<>();
        for (Item item : items) {
            if (item.type == type) {
                if (showOnlyOwned) {
                    if (UserTools.isItemPurchased(getActivity(), item.id)) {
                        item.owned = true;
                        auxItems.add(item);
                    }
                } else {
                    auxItems.add(item);
                }
            }
        }

        return auxItems;
    }
}
