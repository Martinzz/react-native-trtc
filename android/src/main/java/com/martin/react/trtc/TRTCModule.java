package com.martin.react.trtc;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tencent.liteav.TXLiteAVCode;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudListener;

import java.util.HashMap;
import java.util.Map;

import static com.martin.react.trtc.TRTCConst.*;

public class TRTCModule extends ReactContextBaseJavaModule {
    private TRTCCloud mTRTCCloud;
    private final String TAG = "TRTCModule";
    public TRTCModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "RTCTencent";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        return constants;
    }

    private TRTCCloudListener mRtcEventHandler = new TRTCCloudListener() {
        @Override
        public void onUserVideoAvailable(String userId, boolean available) {
            WritableMap map = Arguments.createMap();
            map.putString("userId", userId);
            map.putBoolean("available", available);
            sendEvent(getReactApplicationContext(), TRTC_onUserVideoAvailable, map);
        }
        @Override
        public void onRemoteUserEnterRoom(String userId) {
            WritableMap map = Arguments.createMap();
            map.putString("userId", userId);
            sendEvent(getReactApplicationContext(), TRTC_onRemoteUserEnterRoom, map);
        }

        @Override
        public void onRemoteUserLeaveRoom(String userId, int reason) {
            WritableMap map = Arguments.createMap();
            map.putString("userId", userId);
            map.putInt("reason", reason);
            sendEvent(getReactApplicationContext(), TRTC_onRemoteUserLeaveRoom, map);
        }

        @Override
        public void onExitRoom(int reason){
            WritableMap map = Arguments.createMap();
            map.putInt("reason", reason);
            sendEvent(getReactApplicationContext(), TRTC_onExitRoom, map);
        }
        // 错误通知监听，错误通知意味着 SDK 不能继续运行
        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            WritableMap map = Arguments.createMap();
            map.putString("message", errMsg);
            map.putInt("code", errCode);
            sendEvent(getReactApplicationContext(), TRTC_onError, map);
        }
        @Override
        public void onEnterRoom(long result) {
            WritableMap map = Arguments.createMap();
            map.putDouble("result", result);
            sendEvent(getReactApplicationContext(), TRTC_onEnterRoom, map);
        }
    };

    @ReactMethod
    public void init(ReadableMap options) {
        TRTCManager.getInstance().init(getReactApplicationContext(), mRtcEventHandler, options);
        mTRTCCloud = TRTCManager.getInstance().mTRTCCloud;
    }

    @ReactMethod
    public void enterRoom(ReadableMap options, int scene) {
        TRTCManager.getInstance().enterRoom(options, scene);
    }

    @ReactMethod
    public void switchRole(int role) {
        TRTCManager.getInstance().switchRole(role);
    }

    @ReactMethod
    public void stopLocalPreview() {
        TRTCManager.getInstance().stopLocalPreview();
    }
    @ReactMethod
    public void startLocalAudio() {
        TRTCManager.getInstance().startLocalAudio();
    }
    @ReactMethod
    public void stopLocalAudio() {
        TRTCManager.getInstance().stopLocalAudio();
    }
    @ReactMethod
    public void exitRoom() {
        TRTCManager.getInstance().exitRoom();
    }

    @ReactMethod
    public void destroy() {
        TRTCManager.getInstance().destroy();
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
