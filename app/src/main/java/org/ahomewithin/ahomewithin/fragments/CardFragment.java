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
    ConversationCard mCard;

    @Bind(R.id.tvTitle) TextView tvTitle;
    @Bind(R.id.tvQuote) TextView tvQuote;
    @Bind(R.id.tvQuote_author) TextView tvQuote_author;
    @Bind(R.id.tvReflection) TextView tvReflection;
    @Bind(R.id.tvAction) TextView tvAction;

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

        tvTitle.setText(getTitle(mCard.type));
        tvQuote.setText(mCard.quote);
        tvQuote_author.setText(mCard.quote_author);
        tvReflection.setText(mCard.reflection);
        tvAction.setText(mCard.action);
    }

    private String getTitle(int type) {
        switch (type) {
            default:
            case ConversationCard.BODY: return "BODY";
            case ConversationCard.MIND: return "MIND";
            case ConversationCard.HEART: return "HEART";
            case ConversationCard.SOUL: return "SOUL";
        }
    }
}
