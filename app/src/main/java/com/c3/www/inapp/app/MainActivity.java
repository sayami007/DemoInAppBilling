package com.c3.www.inapp.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.c3.www.inapp.R;
import com.c3.www.inapp.util.IabHelper;
import com.c3.www.inapp.util.IabResult;
import com.c3.www.inapp.util.Inventory;

import java.util.ArrayList;
import java.util.List;

import static com.c3.www.inapp.app.Constant.*;

public class MainActivity extends AppCompatActivity {

    IabHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> listSKU = new ArrayList<>();
        listSKU.add(GAS);
        listSKU.add(INFINITE);
        listSKU.add(PREMIUM);

        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess())
                    alert("Not Success. Error: " + result);

            }
        });



        mHelper.queryInventoryAsync(true, listSKU, queryInventoryFinishedListener);
    }

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