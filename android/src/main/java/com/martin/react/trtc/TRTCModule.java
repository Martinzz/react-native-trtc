package com.martin.react.trtc;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.martin.react.trtc.TRTCConst.*;

public class TRTCModule extends ReactContextBaseJavaModule {
    private TRTCCloud mTRTCCloud;
    private final String TAG = "TRTCModule";
    private Promise snapshotVideoCallback;
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
        public void onFirstAudioFrame(String userId) {
            WritableMap map = Arguments.createMap();
            map.putString("userId", userId);
            sendEvent(getReactApplicationContext(), TRTC_onFirstAudioFrame, map);
        }
        @Override
        public void onSendFirstLocalVideoFrame(int streamType) {
            WritableMap map = Arguments.createMap();
            map.putInt("streamType", streamType);
            sendEvent(getReactApplicationContext(), TRTC_onSendFirstLocalVideoFrame, map);
        }
        @Override
        public void onSendFirstLocalAudioFrame() {
            sendEvent(getReactApplicationContext(), TRTC_onSendFirstLocalAudioFrame, null);
        }
        @Override
        public void onNetworkQuality(TRTCCloudDef.TRTCQuality localQuality, ArrayList< TRTCCloudDef.TRTCQuality > remoteQuality ) {
            WritableMap map = Arguments.createMap();
            WritableMap localMap = Arguments.createMap();
            WritableMap remoteMap;
            localMap.putString("userId", localQuality.userId);
            localMap.putInt("quality", localQuality.quality);
            map.putMap("localQuality", localMap);

            WritableArray array = Arguments.createArray();
            for(TRTCCloudDef.TRTCQuality remote : remoteQuality){
                remoteMap = Arguments.createMap();
                remoteMap.putString("userId", remote.userId);
                remoteMap.putInt("quality", remote.quality);
                array.pushMap(remoteMap);
            }
            map.putArray("remoteQuality", array);
            sendEvent(getReactApplicationContext(), TRTC_onNetworkQuality, map);
        }

        @Override
        public void onFirstVideoFrame(String userId, int streamType, int width, int height) {
            WritableMap map = Arguments.createMap();
            map.putString("userId", userId);
            map.putInt("streamType", streamType);
            map.putInt("width", width);
            map.putInt("height", height);
            sendEvent(getReactApplicationContext(), TRTC_onFirstVideoFrame, map);
        }
        @Override
        public void onUserAudioAvailable(String userId, boolean available) {
            WritableMap map = Arguments.createMap();
            map.putString("userId", userId);
            map.putBoolean("available", available);
            sendEvent(getReactApplicationContext(), TRTC_onUserAudioAvailable, map);
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
        @Override
        public void onConnectionLost() {
            sendEvent(getReactApplicationContext(), TRTC_onConnectionLost, null);
        }
        @Override
        public void onTryToReconnect() {
            sendEvent(getReactApplicationContext(), TRTC_onTryToReconnect, null);
        }
        @Override
        public void onConnectionRecovery() {
            sendEvent(getReactApplicationContext(), TRTC_onConnectionRecovery, null);
        }
        @Override
        public void onSpeedTest(TRTCCloudDef.TRTCSpeedTestResult currentResult,int finishedCount, int totalCount ) {
            WritableMap map = Arguments.createMap();
            map.putInt("finishedCount", finishedCount);
            map.putInt("totalCount", totalCount);
            WritableMap result = Arguments.createMap();
            result.putString("ip", currentResult.ip);
            result.putInt("quality", currentResult.quality);
            result.putDouble("upLostRate", currentResult.upLostRate);
            result.putDouble("downLostRate", currentResult.downLostRate);
            result.putInt("rtt", currentResult.rtt);
            map.putMap("result", result);
            sendEvent(getReactApplicationContext(), TRTC_onSpeedTest, map);
        }
        @Override
        public void onCameraDidReady() {
            sendEvent(getReactApplicationContext(), TRTC_onCameraDidReady, null);
        }
        @Override
        public void onMicDidReady() {
            sendEvent(getReactApplicationContext(), TRTC_onMicDidReady, null);
        }
        @Override
        public void onAudioRouteChanged(int newRoute,
                                        int oldRoute) {
            WritableMap map = Arguments.createMap();
            map.putInt("newRoute", newRoute);
            map.putInt("oldRoute", oldRoute);
            sendEvent(getReactApplicationContext(), TRTC_onAudioRouteChanged, map);
        }

        @Override
        public void onUserVoiceVolume(ArrayList< TRTCCloudDef.TRTCVolumeInfo > 	userVolumes,
                                      int 	totalVolume ) {
            WritableMap map = Arguments.createMap();
            map.putInt("totalVolume", totalVolume);
            WritableMap remoteMap;
            WritableArray array = Arguments.createArray();
            for(TRTCCloudDef.TRTCVolumeInfo info : userVolumes){
                remoteMap = Arguments.createMap();
                remoteMap.putString("userId", info.userId);
                remoteMap.putInt("volume", info.volume);
                array.pushMap(remoteMap);
            }
            map.putArray("userVolumes", array);
            sendEvent(getReactApplicationContext(), TRTC_onUserVoiceVolume, map);
        }

        @Override
        public void onStartPublishing(int 	err,
                                      String 	errMsg) {
            WritableMap map = Arguments.createMap();
            map.putInt("err", err);
            map.putString("errMsg", errMsg);
            sendEvent(getReactApplicationContext(), TRTC_onStartPublishing, map);
        }
        @Override
        public void onStopPublishing(int 	err,
                                      String 	errMsg) {
            WritableMap map = Arguments.createMap();
            map.putInt("err", err);
            map.putString("errMsg", errMsg);
            sendEvent(getReactApplicationContext(), TRTC_onStopPublishing, map);
        }
        @Override
        public void onStartPublishCDNStream(int 	err,
                                     String 	errMsg) {
            WritableMap map = Arguments.createMap();
            map.putInt("err", err);
            map.putString("errMsg", errMsg);
            sendEvent(getReactApplicationContext(), TRTC_onStartPublishCDNStream, map);
        }
        @Override
        public void onStopPublishCDNStream(int 	err,
                                            String 	errMsg) {
            WritableMap map = Arguments.createMap();
            map.putInt("err", err);
            map.putString("errMsg", errMsg);
            sendEvent(getReactApplicationContext(), TRTC_onStopPublishCDNStream, map);
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
    public void startPublishing(final String streamId,
                                final int streamType) {
        mTRTCCloud.startPublishing(streamId, streamType);
    }
    @ReactMethod
    public void stopPublishing() {
        mTRTCCloud.stopPublishing();
    }

    @ReactMethod
    public void startSpeedTest(int 	sdkAppId,
                               String 	userId,
                               String 	userSig ) {
        mTRTCCloud.startSpeedTest(sdkAppId, userId, userSig);
    }
    @ReactMethod
    public void stopSpeedTest() {
        mTRTCCloud.stopSpeedTest();
    }
    @ReactMethod
    public void muteLocalAudio(boolean mute) {
        mTRTCCloud.muteLocalAudio(mute);
    }
    @ReactMethod
    public void setAudioRoute(int route) {
        mTRTCCloud.setAudioRoute(route);
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
    @ReactMethod
    public void muteRemoteAudio(String 	userId, boolean mute ) {
        mTRTCCloud.muteRemoteAudio(userId, mute);
    }
    @ReactMethod
    public void muteAllRemoteAudio(boolean mute) {
        mTRTCCloud.muteAllRemoteAudio(mute);
    }
    @ReactMethod
    public void setRemoteAudioVolume(String userId, int volume) {
        mTRTCCloud.setRemoteAudioVolume(userId, volume);
    }
    @ReactMethod
    public void setAudioCaptureVolume(int volume) {
        mTRTCCloud.setAudioCaptureVolume(volume);
    }
    @ReactMethod
    public void getAudioCaptureVolume(Promise promise) {
        int volume = mTRTCCloud.getAudioCaptureVolume();
        promise.resolve(volume);
    }
    @ReactMethod
    public void setAudioPlayoutVolume(int volume) {
        mTRTCCloud.setAudioPlayoutVolume(volume);
    }

    @ReactMethod
    public void getAudioPlayoutVolume(Promise promise) {
        int volume = mTRTCCloud.getAudioPlayoutVolume();
        promise.resolve(volume);
    }
    @ReactMethod
    public void enableAudioVolumeEvaluation(int intervalMs) {
        mTRTCCloud.enableAudioVolumeEvaluation(intervalMs);
    }

    @ReactMethod
    public void startAudioRecording(String filePath, Promise promise) {
        TRTCCloudDef.TRTCAudioRecordingParams params = new TRTCCloudDef.TRTCAudioRecordingParams();
        params.filePath = filePath;
        int result = mTRTCCloud.startAudioRecording(params);
        promise.resolve(result);
    }
    @ReactMethod
    public void stopAudioRecording() {
        mTRTCCloud.stopAudioRecording();
    }

    @ReactMethod
    public void switchCamera() {
        mTRTCCloud.switchCamera();
    }

    @ReactMethod
    public void setLocalViewRotation(int rotation) {
        mTRTCCloud.setLocalViewRotation(rotation);
    }
    @ReactMethod
    public void setRemoteViewRotation(String userId, int rotation) {
        mTRTCCloud.setRemoteViewRotation(userId, rotation);
    }
    @ReactMethod
    public void setVideoEncoderRotation(int rotation) {
        mTRTCCloud.setVideoEncoderRotation(rotation);
    }
    @ReactMethod
    public void setLocalViewMirror(int mirror) {
        mTRTCCloud.setLocalViewMirror(mirror);
    }
    @ReactMethod
    public void setVideoEncoderMirror(boolean mirror) {
        mTRTCCloud.setVideoEncoderMirror(mirror);
    }
    @ReactMethod
    public void setGSensorMode(int mode) {
        mTRTCCloud.setGSensorMode(mode);
    }
    @ReactMethod
    public void enableEncSmallVideoStream(boolean enable, ReadableMap options, Promise promise) {
        TRTCCloudDef.TRTCVideoEncParam param = new TRTCCloudDef.TRTCVideoEncParam();
        if (options.hasKey("resMode")) {
            param.videoResolutionMode = options.getInt("resMode");
        }
        if (options.hasKey("videoFps")) {
            param.videoFps = options.getInt("videoFps");
        }
        if (options.hasKey("videoBitrate")) {
            param.videoBitrate = options.getInt("videoBitrate");
        }
        if (options.hasKey("minVideoBitrate")) {
            param.minVideoBitrate = options.getInt("minVideoBitrate");
        }
        if (options.hasKey("enableAdjustRes")) {
            param.enableAdjustRes = options.getBoolean("enableAdjustRes");
        }
        int result = mTRTCCloud.enableEncSmallVideoStream(enable, param);
        promise.resolve(result);
    }
    @ReactMethod
    public void setRemoteVideoStreamType(String userId, int type) {
        mTRTCCloud.setRemoteVideoStreamType(userId, type);
    }
    @ReactMethod
    public void setPriorRemoteVideoStreamType(int type) {
        mTRTCCloud.setPriorRemoteVideoStreamType(type);
    }
    private TRTCCloudListener.TRTCSnapshotListener snapshotListener = new TRTCCloudListener.TRTCSnapshotListener(){
        @Override
        public void onSnapshotComplete(Bitmap bitmap){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bitmap.recycle();
            byte[] bitmapBytes = stream.toByteArray();
            snapshotVideoCallback.resolve(Base64.encodeToString(bitmapBytes, Base64.DEFAULT));
        }
    };
    @ReactMethod
    public void snapshotVideo(String userId, int type, Promise promise) {
        snapshotVideoCallback = promise;
        mTRTCCloud.snapshotVideo(userId, type, snapshotListener);
    }
    @ReactMethod
    public void setAudioQuality(int quality) {
        mTRTCCloud.setAudioQuality(quality);
    }
}
