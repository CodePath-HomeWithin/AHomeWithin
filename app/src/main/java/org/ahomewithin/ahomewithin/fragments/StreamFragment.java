package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.adapters.DividerItemDecoration;
import org.ahomewithin.ahomewithin.adapters.ItemsStreamAdapter;
import org.ahomewithin.ahomewithin.models.Item;
import org.ahomewithin.ahomewithin.util.BuyDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;

/**
 * Created by chezlui on 06/03/16.
 */
public class StreamFragment extends Fragment implements ItemsStreamAdapter.OnItemInteraction {
    public static final String ARG_STREAM_TYPE = "stream_type";
    public int type;
    ArrayList<Item> items;
    ItemsStreamAdapter aItems;
    @Bind(R.id.rvStream) RecyclerView rvStream;

    public static StreamFragment newInstance(int streamType) {
        StreamFragment streamFragment = new StreamFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_STREAM_TYPE, streamType);
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

        type = getArguments().getInt(ARG_STREAM_TYPE);
        aItems = new ItemsStreamAdapter(getActivity(), new ArrayList<Item>(), this);

        rvStream.setAdapter(aItems);

        items = Item.fromJson(AHomeWithinClient.getStreams(getActivity(), type), type);
        aItems.addAll(items);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        rvStream.addItemDecoration(itemDecoration);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvStream.setLayoutManager(llm);
        rvStream.setItemAnimator(new FlipInBottomXAnimator());

//        ItemClickSupport.addTo(rvStream).setOnItemClickListener(
//                new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        Intent intent = new Intent(getActivity(), DetailActivity.class);
//                        Tweet tweet = tweets.get(position);
//                        intent.putExtra("tweet", Parcels.wrap(tweet));
//                        startActivity(intent);
//                    }
//                }
//        );
    }

    @Override
    public void onBuy(final int position) {
        FragmentManager fm = getFragmentManager();
        BuyDialog buyDialog = BuyDialog.newInstance(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getActivity(), "Now you can watch it", Toast.LENGTH_SHORT).show();
                items.get(position).owned = true;
                aItems.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        buyDialog.show(fm, "");
    }
}
