package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
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

        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        type = Item.ITEM_TYPE.values()[(getArguments().getInt(ARG_STREAM_TYPE))];
        showOnlyOwned = getArguments().getBoolean(ARG_OWNED_ITEMS);

        aItems = new ItemsStreamAdapter(getActivity(), new ArrayList<Item>());

        rvStream.setAdapter(aItems);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvStream.setLayoutManager(llm);

        ParseClient.newInstance(getActivity()).getPurchasableItems(new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(Object obj) {
                items = (ArrayList<Item>) obj;

                items = getSubsetOfItems(items, type, showOnlyOwned);
                // stubbed out method
                //items = Item.fromJson(AHomeWithinClient.getStreams(getActivity(), type), type);
                aItems.addAll(items);

                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
                rvStream.addItemDecoration(itemDecoration);
                rvStream.setItemAnimator(new FlipInBottomXAnimator());

//        ItemClickSupport.addTo(rvStream).setOnItemClickListener(
//                new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        Intent intent = new Intent(getActivity(), VideoActivity.class);
//                        Tweet tweet = tweets.get(position);
//                        intent.putExtra("tweet", Parcels.wrap(tweet));
//                        startActivity(intent);
//                    }
//                }
//        );
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }

    // TODO Add show only owned
    private ArrayList<Item> getSubsetOfItems(ArrayList<Item> items, Item.ITEM_TYPE type, boolean showOnlyOwned) {
        ArrayList<Item> auxItems = new ArrayList<>();
        for (Item item: items) {
            if (item.type == type) {
                if (showOnlyOwned) {
                    if(UserTools.isItemPurchased(getActivity(), item.id)) {
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
