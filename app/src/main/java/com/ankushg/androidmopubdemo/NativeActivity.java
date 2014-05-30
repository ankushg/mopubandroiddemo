package com.ankushg.androidmopubdemo;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.mopub.nativeads.AdapterHelper;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.NativeResponse;
import com.mopub.nativeads.RequestParameters;
import com.mopub.nativeads.ViewBinder;

public class NativeActivity extends ActionBarActivity {
    private Context mContext;
    private String mAdUnitId = "c2734d914fe2421b9b53e9f65feb2691";

    private MoPubNative.MoPubNativeListener moPubNativeListener;
    private ViewBinder viewBinder;
    private AdapterHelper mAdapterHelper;

    private LinearLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        mRootView = (LinearLayout) findViewById(R.id.native_root);

        viewBinder = new ViewBinder.Builder(R.layout.native_ad_layout)
                .mainImageId(R.id.native_main_image)
                .iconImageId(R.id.native_icon_image)
                .titleId(R.id.native_title)
                .textId(R.id.native_text)
                .build();

        moPubNativeListener = new MoPubNative.MoPubNativeListener() {
            @Override
            public void onNativeLoad(NativeResponse nativeResponse) {
                mAdapterHelper = new AdapterHelper(mContext, 0, 60);
                mRootView.addView(mAdapterHelper.getAdView(null, mRootView, nativeResponse, viewBinder, this));
            }

            @Override
            public void onNativeFail(NativeErrorCode errorCode) {
                // ...
            }

            @Override
            public void onNativeImpression(View view) {
                // ...
            }

            @Override
            public void onNativeClick(View view) {
                // ...
            };
        };

        MoPubNative moPubNative = new MoPubNative(mContext, mAdUnitId, moPubNativeListener);

        Location exampleLocation = new Location("example_location");
        exampleLocation.setLatitude(23.1);
        exampleLocation.setLongitude(42.1);
        exampleLocation.setAccuracy(100);

        RequestParameters requestParameters = new RequestParameters.Builder()
                .keywords("gender:m,age:27")
                .location(exampleLocation)
                .build();
        moPubNative.makeRequest(requestParameters);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
