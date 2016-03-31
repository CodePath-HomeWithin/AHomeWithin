package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.ConversationCard;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 06/03/16.
 */
public class CardFragment extends Fragment {
    public static final String FRAGMENT_TAG = CardFragment.class.getSimpleName();

    ConversationCard mCard;
    @Bind(R.id.tvQuote) TextView tvQuote;
    @Bind(R.id.tvQuote_author) TextView tvQuote_author;
    @Bind(R.id.tvReflection) TextView tvReflection;
    @Bind(R.id.tvAction) TextView tvAction;
    @Bind(R.id.tvMind) TextView tvMind;
    @Bind(R.id.tvBody) TextView tvBody;
    @Bind(R.id.tvHeart) TextView tvHeart;
    @Bind(R.id.tvSoul) TextView tvSoul;

    public static CardFragment newInstance(ConversationCard card) {
        CardFragment cardFragment = new CardFragment();

        Bundle args = new Bundle();
        args.putSerializable(ConversationCard.CARD_TAG, card);
        cardFragment.setArguments(args);
        return cardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View convertView = inflater.inflate(R.layout.fragment_conversation_card, container, false);
        ButterKnife.bind(this, convertView);

        mCard = (ConversationCard) getArguments().getSerializable(ConversationCard.CARD_TAG);

        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitleColour(mCard.type);
        tvQuote.setText(mCard.quote);
        tvQuote_author.setText(mCard.quote_author);
        tvReflection.setText(mCard.reflection);
        tvAction.setText(mCard.action);
    }

    private void setTitleColour(int type) {
        TextView tvToColour;
        switch (type) {
            default:
            case ConversationCard.BODY:
                tvToColour = tvBody; break;
            case ConversationCard.MIND:
                tvToColour = tvMind; break;
            case ConversationCard.HEART:
                tvToColour = tvHeart; break;
            case ConversationCard.SOUL:
                tvToColour = tvSoul; break;
        }

        tvToColour.setTextColor(getResources().getColor(R.color.accent));
    }
}
