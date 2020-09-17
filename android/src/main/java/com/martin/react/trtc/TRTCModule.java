package com.martin.react.trtc;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class TRTCModule extends ReactContextBaseJavaModule {

    public TRTCModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "RTCTencent";
    }
}
