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
    }
}