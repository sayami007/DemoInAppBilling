package com.c3.www.inapp.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.c3.www.inapp.R;
import com.c3.www.inapp.util.IabHelper;
import com.c3.www.inapp.util.IabResult;
import com.c3.www.inapp.util.Inventory;
import com.c3.www.inapp.util.Purchase;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.c3.www.inapp.app.Constant.ITEM_SKU;
import static com.c3.www.inapp.app.Constant.RC_REQUEST;
import static com.c3.www.inapp.app.Constant.TAG;
import static com.c3.www.inapp.app.Constant.base64EncodedPublicKey;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.buyButton)
    Button buyButton;

    IabHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        createPurchaseHelper();
    }

    private void createPurchaseHelper() {
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.v("ERROR: SEt BILLING", "" + result);
                }
                if (mHelper == null) return;
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    @OnClick(R.id.buyButton)
    void buy() {
        mHelper.launchPurchaseFlow(this, ITEM_SKU, RC_REQUEST, mPurchase, "");
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchase = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            if (mHelper == null) return;
            if (result.isFailure()) {
                Log.v("FAIL", "" + result);
                return;
            }

            Log.v(TAG, "PURCHASE SUCCESS");
            if (info != null && verify(info)) {
                updateUI(ITEM_SKU);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHelper == null) return;
        if (mHelper.handleActivityResult(requestCode, resultCode, data)) {
            Log.d(TAG, "onAtivity Reuslt");
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            if (mHelper == null) return;
            if (result.isFailure()) {
                Log.v("FAILED", "" + result);
                return;
            }
            Log.d(TAG, "QUEST INVO WAS SUCCESS");
            Purchase issuePurchase = inv.getPurchase(ITEM_SKU);
            if (issuePurchase != null && verify(issuePurchase)) {
                updateUI(ITEM_SKU);
            }
        }
    };

    private void updateUI(String itemSku) {
        Toast.makeText(this, "DONE", Toast.LENGTH_SHORT).show();
    }

    private boolean verify(Purchase issuePurchase) {
        String payload = issuePurchase.getDeveloperPayload();
        return true;
    }
}