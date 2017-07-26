package com.c3.www.inapp.app;

import com.c3.www.inapp.R;

/**
 * Created by Bibesh on 7/25/17.
 */

public class Constant {
    // Debug tag, for logging
    public static final String TAG = "TrivialDrive";

    static final String SKU_PREMIUM = "android.test.purchased";
    static final String SKU_GAS = "android.test.purchased";
    static final String SKU_INFINITE_GAS = "android.test.purchased";
//
//    static final String SKU_PREMIUM = "premiums";
//    static final String SKU_GAS = "gas";
//    static final String SKU_INFINITE_GAS = "infinite_gas";

    static final int RC_REQUEST = 10001;


    // Graphics for the gas gauge
    static int[] TANK_RES_IDS = {R.drawable.gas0, R.drawable.gas1, R.drawable.gas2,
            R.drawable.gas3, R.drawable.gas4};

    // How many units (1/4 tank is our unit) fill in the tank.
    static final int TANK_MAX = 4;

    static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtTG+2TM4viC8U3WBe18YKKy3kRkosWwg1YRzvQ+El0IzyYIzyqaGpC4WmyTU0F/1RQRWx40yosZicty+W/aL6eBfjq64JKWWMLPq+yeCPdRRUcmP+nSavPVL+macLkPEvoHNd2eCvP3/ufahIG137Bp7YdSAuYokVcvdviUME2eAsHju1vUz1lTsuEfxyqY8/oxZtkOUCSJt6EqT2FfTSlczPo3m4cJ/B+kn+Mid98xHV2wwieb3RsoUwNlBJ+iUdaLTNe9K2otyBQH/j2mozbkX4s4GuFneeQ1WseHDRWLqumt8muR2oR76ZK0FJcirjTX6rUzgBLShsdeR4zXChwIDAQAB";

}