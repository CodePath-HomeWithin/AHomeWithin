package org.ahomewithin.ahomewithin.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.ahomewithin.ahomewithin.BuyClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Item;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

/**
 * Created by chezlui on 07/03/16.
 */
public class BuyDialog extends SupportBlurDialogFragment {
    AsyncHttpResponseHandler responseHandler;
    Item mItem;

    public static final String LOG_TAG = BuyDialog.class.getSimpleName();
    @Bind(R.id.etBuyerName) EditText etBuyerName;
    @Bind(R.id.etBuyerCardNumber) EditText etBuyerCardNumber;
    @Bind(R.id.etBuyerDate) EditText etBuyerDate;
    @Bind(R.id.etBuyerCVC) EditText etBuyerCVC;
    @Bind(R.id.btBuy) Button btBuy;
    @Bind(R.id.tvTitlePurchase) TextView tvTitlePurchase;

    public BuyDialog() {
    }

    public static BuyDialog newInstance(AsyncHttpResponseHandler responseHandler, Item item) {

        BuyDialog buyDialog = new BuyDialog();
        buyDialog.responseHandler = responseHandler;
        buyDialog.mItem = item;
        return buyDialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Translucent);
        View view = inflater.inflate(R.layout.dialog_buy, container);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setListeners();
        btBuy.setText("Pay $" + mItem.price);
        tvTitlePurchase.setText(mItem.title);
    }

    // For making full screen
    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void setListeners() {

        btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy();
            }
        });

        etBuyerCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((s.length() % 4 == 0) && (s.length() < 15)) {
                    s.append(" ");
                }
            }
        });

    }

    private void buy() {

        BuyClient client = BuyClient.getRestClient();
        client.buy(responseHandler,
                etBuyerName.getText().toString(),
                etBuyerCardNumber.getText().toString(),
                etBuyerDate.getText().toString(),
                etBuyerCVC.getText().toString()
        );

        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    @Override
    protected boolean isActionBarBlurred() {
        // Enable or disable the blur effect on the action bar.
        // Disabled by default.
        return true;
    }

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 5;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 7;
    }
}
