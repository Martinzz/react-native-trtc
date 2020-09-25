package com.martin.react.trtc;

import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;


/**
 * @Description TODO
 * @Author martin
 * @Date 2020-09-18 11:38
 */
public class TRTCManager {
    public static TRTCManager sTRTCManager;
    public TRTCCloud mTRTCCloud;

    private TRTCManager() {

    }

    public static TRTCManager getInstance() {
        if (sTRTCManager == null) {
            synchronized (TRTCManager.class) {
                if (sTRTCManager == null) {
                    sTRTCManager = new TRTCManager();
                }
            }
        }
        return sTRTCManager;
    }

    /**
     * initialize rtc engine
     */
    public void init(Context context, TRTCCloudListener mRtcEventHandler, ReadableMap options) {
        try{
            // 创建 trtcCloud 实例
            this.mTRTCCloud = TRTCCloud.sharedInstance(context);
            this.mTRTCCloud.setListener(mRtcEventHandler);

            TRTCCloudDef.TRTCVideoEncParam encParam = new TRTCCloudDef.TRTCVideoEncParam();
            //设置本地视频编码参数
            if (options.hasKey("videoResolution")) {
                encParam.videoResolution = options.getInt("videoResolution");
            }
            if (options.hasKey("videoResolutionMode")) {
                encParam.videoResolutionMode = options.getInt("videoResolutionMode");
            }
            if (options.hasKey("videoFps")) {
                encParam.videoFps = options.getInt("videoFps");
            }
            if (options.hasKey("videoBitrate")) {
                encParam.videoBitrate = options.getInt("videoBitrate");
            }
            if (options.hasKey("minVideoBitrate")) {
                encParam.minVideoBitrate = options.getInt("minVideoBitrate");
            }
            if (options.hasKey("enableAdjustRes")) {
                encParam.enableAdjustRes = options.getBoolean("enableAdjustRes");
            }
           this.mTRTCCloud.setVideoEncoderParam(encParam);

        }catch (Exception e) {
            throw new RuntimeException("create trtc cloud failed\n" + Log.getStackTraceString(e));
        }
    }

    public void enterRoom(ReadableMap options, int scene) {
        TRTCCloudDef.TRTCParams trtcParams = new TRTCCloudDef.TRTCParams();
        int sdkAppId = options.hasKey("sdkAppId") ? options.getInt("sdkAppId") : null;
        String userId = options.hasKey("userId") ? options.getString("userId") : null;
        int roomId = options.hasKey("roomId") ? options.getInt("roomId") : null;
        String userSig = options.hasKey("userSig") ? options.getString("userSig") : null;
        trtcParams.sdkAppId = sdkAppId;
        trtcParams.userId = userId;
        trtcParams.roomId = roomId;
        trtcParams.userSig = userSig;

        if(options.hasKey("privateMapKey")){
            trtcParams.privateMapKey = options.getString("privateMapKey");
        }
        if(options.hasKey("role")){
            trtcParams.role = options.getInt("role");
        }
        this.mTRTCCloud.enterRoom(trtcParams, scene);
    }

    public void switchRole(int role){
        this.mTRTCCloud.switchRole(role);
    }

    public void startLocalAudio(){
        this.mTRTCCloud.startLocalAudio();
    }
    public void stopLocalAudio(){
        this.mTRTCCloud.startLocalAudio();
    }

    public void exitRoom(){
        this.mTRTCCloud.exitRoom();
    }

    public void destroy(){
        TRTCCloud.destroySharedInstance();
    }
}
