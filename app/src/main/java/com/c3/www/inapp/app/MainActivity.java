package com.c3.www.inapp.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.c3.www.inapp.R;
import com.c3.www.inapp.util.IabHelper;
import com.c3.www.inapp.util.IabResult;
import com.c3.www.inapp.util.Inventory;
import com.c3.www.inapp.util.Purchase;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.c3.www.inapp.app.Constant.*;

public class MainActivity extends AppCompatActivity {

    IabHelper mHelper;
    List<String> listSKU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        listSKU = new ArrayList<>();
        listSKU.add(GAS);
        listSKU.add(INFINITE);
        listSKU.add(PREMIUM);

        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess())
                    alert("Not Success. Error: " + result);
                mHelper.queryInventoryAsync(true, listSKU, queryInventoryFinishedListener);
            }
        });
    }

    @OnClick(R.id.buyButton)
    void onBuyButton() {
        mHelper.launchPurchaseFlow(this,
                GAS, RC_REQUEST, mPurchasedListener, "");
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchasedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            if (!result.isSuccess())
                Log.v(TAG, "Unsuccessful");
            else if (info.getSku().equals(GAS)) {
                Log.v(TAG, "Gas Purchased");
            }
        }
    };

    IabHelper.QueryInventoryFinishedListener queryInventoryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            if (result.isFailure())
                return;
            String gasPrice = inv.getSkuDetails(GAS).getPrice();
            String infinitePrice = inv.getSkuDetails(INFINITE).getPrice();
            String premiumPrice = inv.getSkuDetails(PREMIUM).getPrice();
            Log.v("PRICES", gasPrice);
            Log.v("PRICES", infinitePrice);
            Log.v("PRICES", premiumPrice);
        }
    };

    /**
     * @Param Message
     */
    private void alert(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s);
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }


}