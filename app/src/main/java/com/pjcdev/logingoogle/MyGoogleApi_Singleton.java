package com.pjcdev.logingoogle;

import com.google.android.gms.common.api.GoogleApiClient;

class MyGoogleApi_Singleton {
    private static final String TAG = "GoogleApiClient";
    private static MyGoogleApi_Singleton instance = null;

    private static GoogleApiClient mGoogleApiClient = null;

    protected MyGoogleApi_Singleton() {

    }

    public static MyGoogleApi_Singleton getInstance(GoogleApiClient aGoogleApiClient) {

        if(instance == null) {
            instance = new MyGoogleApi_Singleton();

            if (mGoogleApiClient == null)
                mGoogleApiClient = aGoogleApiClient;
        }

        return instance;
    }

    public GoogleApiClient get_GoogleApiClient(){
        return mGoogleApiClient;
    }

}