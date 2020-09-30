package com.martin.react.trtc;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.tencent.liteav.beauty.TXBeautyManager;
import com.tencent.trtc.TRTCCloud;

/**
 * @Description 美颜
 * @Author martin
 * @Date 2020-09-30 17:50
 */
public class TRTCBeautyModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext context;
    @Override
    public String getName() {
        return "TRTCBeauty";
    }

    public TRTCBeautyModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
    }
    private TRTCCloud getEngine(){
        return context.getNativeModule(TRTCModule.class).engine();
    }
    @ReactMethod
    public void setBeautyStyle(int beautyStyle) {
        TXBeautyManager beautyManager = getEngine().getBeautyManager();
        beautyManager.setBeautyStyle(beautyStyle);
    }
    @ReactMethod
    public void setBeautyLevel(int beautyLevel) {
        TXBeautyManager beautyManager = getEngine().getBeautyManager();
        beautyManager.setBeautyLevel(beautyLevel);
    }
    @ReactMethod
    public void setWhitenessLevel(int whitenessLevel) {
        TXBeautyManager beautyManager = getEngine().getBeautyManager();
        beautyManager.setWhitenessLevel(whitenessLevel);
    }
    @ReactMethod
    public void enableSharpnessEnhancement(boolean enable) {
        TXBeautyManager beautyManager = getEngine().getBeautyManager();
        beautyManager.enableSharpnessEnhancement(enable);
    }
    @ReactMethod
    public void setRuddyLevel(int ruddyLevel) {
        TXBeautyManager beautyManager = getEngine().getBeautyManager();
        beautyManager.setRuddyLevel(ruddyLevel);
    }
}
