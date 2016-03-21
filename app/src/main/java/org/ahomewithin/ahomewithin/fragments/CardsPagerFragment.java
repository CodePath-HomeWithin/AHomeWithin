package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.ConversationCard;
import org.ahomewithin.ahomewithin.models.Item;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 06/03/16.
 */
public class CardsPagerFragment extends Fragment {
    public static final String FRAGMENT_TAG = CardsPagerFragment.class.getSimpleName();

    @Bind(R.id.viewpager) ViewPager viewPager;
    Item mItem;

    public static CardsPagerFragment newInstance(Item item) {
        CardsPagerFragment cardsPagerFragment = new CardsPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Item.SERIALIZABLE_TAG, item);
        cardsPagerFragment.setArguments(bundle);

        return cardsPagerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View convertView = inflater.inflate(R.layout.content_card_pager, container, false);
        ButterKnife.bind(this, convertView);

        mItem = (Item) getArguments().getSerializable(Item.SERIALIZABLE_TAG);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.setAdapter(new CardsPagerAdapter(getChildFragmentManager()));
        // Reference (or instantiate) a ViewPager instance and apply a transformer
        viewPager.setPageTransformer(true, new CubeOutTransformer());


//        if (getActivity().getIntent().hasExtra(ARG_STREAM_TAB)) {
//            viewPager.setCurrentItem(getActivity().getIntent().getIntExtra(ARG_STREAM_TAB, 0));
//        }
    }


    // Return the order of the fragments in the viewpager
    public class CardsPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = {"MIND", "BODY", "HEART", "SOUL"};

        public CardsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == ConversationCard.MIND) {
                return CardFragment.newInstance(mItem.contentCards.get(ConversationCard.MIND));
            } else if (position == ConversationCard.BODY) {
                return CardFragment.newInstance(mItem.contentCards.get(ConversationCard.BODY));
            }else if (position == ConversationCard.HEART) {
                return CardFragment.newInstance(mItem.contentCards.get(ConversationCard.HEART));
            } else if (position == ConversationCard.SOUL) {
                return CardFragment.newInstance(mItem.contentCards.get(ConversationCard.SOUL));
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
