package com.c3.www.inapp.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.c3.www.inapp.R;
import com.c3.www.inapp.util.IabHelper;
import com.c3.www.inapp.util.IabResult;
import com.c3.www.inapp.util.Inventory;
import com.c3.www.inapp.util.Purchase;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.c3.www.inapp.app.Constant.ITEM_SKU;
import static com.c3.www.inapp.app.Constant.TAG;
import static com.c3.www.inapp.app.Constant.base64EncodedPublicKey;

public class MainActivity extends AppCompatActivity {

    IabHelper mHelper;

    @InjectView(R.id.clickButton)
    Button clickButton;

    @InjectView(R.id.buyButton)
    Button buyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        clickButton.setEnabled(false);
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess())
                    Log.v(TAG, "Failed");
                else
                    Log.v(TAG, "SUCCESS");
            }
        });

    }

    public void buyClick(View view) {
        mHelper.launchPurchaseFlow(this, ITEM_SKU, 10001, mPurchaseFinishedListener, "mypurchasetoken");
    }

    public void buttonClick(View view) {
        clickButton.setEnabled(false);
        buyButton.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            if (result.isFailure()) {
                return;
            } else if (info.getSku().equals(ITEM_SKU)) {
                consumeItem();
                buyButton.setEnabled(false);
            }
        }
    };

    private void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            if (result.isFailure()) {
            } else {
                mHelper.consumeAsync(inv.getPurchase(ITEM_SKU), mConsumedFinishListener);
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumedFinishListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            if (result.isSuccess()) {
                clickButton.setEnabled(true);
            } else {

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }
}
