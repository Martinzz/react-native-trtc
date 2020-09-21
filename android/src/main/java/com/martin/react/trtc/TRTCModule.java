package com.martin.react.trtc;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
            if (available) {
                TRTCManager.getInstance().playRemoteVideo(userId); // 播放远程流
            } else {
                TRTCManager.getInstance().stopRemoteVideo(userId); // 停止播放远程流
            }

        }
        @Override
        public void onRemoteUserEnterRoom(String userId) {

        }

        @Override
        public void onRemoteUserLeaveRoom(String userId, int reason) {

        }
        // 错误通知监听，错误通知意味着 SDK 不能继续运行
        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            Log.d(TAG, "onError: " + errMsg + "[" + errCode+ "]");
        }
        @Override
        public void onEnterRoom(long result) {
            if (result > 0) {
//                toastTip("进房成功，总计耗时[\(result)]ms")
                sendEvent(getReactApplicationContext(), "joinSuccess", null);
            } else {
//                toastTip("进房失败，错误码[\(result)]")
            }
        }
    };

    @ReactMethod
    public void init(ReadableMap options) {
        TRTCManager.getInstance().init(getReactApplicationContext(), mRtcEventHandler, options);
        mTRTCCloud = TRTCManager.getInstance().mTRTCCloud;
    }

    @ReactMethod
    public void enterRoom(ReadableMap options) {
        TRTCManager.getInstance().enterRoom(options);
    }

    @ReactMethod
    public void switchRole(int role) {
        TRTCManager.getInstance().switchRole(role);
    }

    @ReactMethod
    public void startLocalPreview(boolean frontCamera) {
        TRTCManager.getInstance().startLocalPreview(frontCamera);
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
        TRTCCloud.destroySharedInstance();
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
