package com.martin.react.trtc;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.facebook.react.bridge.ReadableMap;
import com.tencent.rtmp.ui.TXCloudVideoView;
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

    private Context context;
    private int mLocalUid = 0;
    private int mScene = TRTCCloudDef.TRTC_APP_SCENE_LIVE; // 应用场景
    private SparseArray<TXCloudVideoView> mSurfaceViews;

    private TRTCManager() {
        mSurfaceViews = new SparseArray<TXCloudVideoView>();
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
            this.context = context;
            // 创建 trtcCloud 实例
            this.mTRTCCloud = TRTCCloud.sharedInstance(context);
            this.mTRTCCloud.setListener(mRtcEventHandler);

            TRTCCloudDef.TRTCVideoEncParam encParam = new TRTCCloudDef.TRTCVideoEncParam();
            //设置本地视频编码参数
            if (options.hasKey("videoResolution")) {
                encParam.videoResolution = options.getInt("videoResolution");
            }else {
                encParam.videoResolution = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_640_360;
            }
            if (options.hasKey("videoResolutionMode")) {
                encParam.videoResolutionMode = options.getInt("videoResolutionMode");
            }else {
                encParam.videoResolutionMode = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_MODE_PORTRAIT;
            }
            if (options.hasKey("videoFps")) {
                encParam.videoFps = options.getInt("videoFps");
            }else {
                encParam.videoFps = 15;
            }
            if (options.hasKey("videoBitrate")) {
                encParam.videoBitrate = options.getInt("videoBitrate");
            }else {
                encParam.videoBitrate = 1200;
            }
            if (options.hasKey("minVideoBitrate")) {
                encParam.minVideoBitrate = options.getInt("minVideoBitrate");
            }
            if (options.hasKey("enableAdjustRes")) {
                encParam.enableAdjustRes = options.getBoolean("enableAdjustRes");
            }

            if (options.hasKey("scene")) {
                this.mScene = options.getInt("scene");
            }
            if (options.hasKey("role")) {
                this.mTRTCCloud.switchRole(options.getInt("role"));
            }else{
                this.mTRTCCloud.switchRole(TRTCCloudDef.TRTCRoleAudience);
            }
           this.mTRTCCloud.setVideoEncoderParam(encParam);

        }catch (Exception e) {
            throw new RuntimeException("create trtc cloud failed\n" + Log.getStackTraceString(e));
        }
    }

    public void enterRoom(ReadableMap options) {
        TRTCCloudDef.TRTCParams trtcParams = new TRTCCloudDef.TRTCParams();
        int sdkAppId = options.hasKey("appId") ? options.getInt("appId") : null;
        String userId = options.hasKey("userId") ? options.getString("userId") : null;
        int roomId = options.hasKey("roomId") ? options.getInt("roomId") : null;
        String userSig = options.hasKey("userSig") ? options.getString("userSig") : null;
        trtcParams.sdkAppId = sdkAppId;
        trtcParams.userId = userId;
        trtcParams.roomId = roomId;
        trtcParams.userSig = userSig;
        this.mLocalUid = Integer.parseInt(userId);
        this.mTRTCCloud.enterRoom(trtcParams, mScene);
    }

    public TXCloudVideoView setupLocalVideo() {
        TXCloudVideoView videoView = new TXCloudVideoView(context);
        mSurfaceViews.put(mLocalUid, videoView);
        return videoView;
    }
    public TXCloudVideoView getLocalView() {
        return mSurfaceViews.get(mLocalUid);
    }

    public TXCloudVideoView setupRemoteVideo(final int uid) {
        TXCloudVideoView videoView = new TXCloudVideoView(context);
        mSurfaceViews.put(uid, videoView);
        return videoView;
    }
    public TXCloudVideoView getView(int uid) {
        return mSurfaceViews.get(uid);
    }
    public void switchRole(int role){
        this.mTRTCCloud.switchRole(role);
    }

    public void startLocalPreview(boolean frontCamera){
        TXCloudVideoView videoView = getLocalView();
        if(videoView != null){

            this.mTRTCCloud.startLocalPreview(frontCamera, videoView);
        }
    }

    public void stopLocalPreview(){
        this.mTRTCCloud.stopLocalPreview();
    }

    public void startLocalAudio(){
        this.mTRTCCloud.startLocalAudio();
    }
    public void stopLocalAudio(){
        this.mTRTCCloud.startLocalAudio();
    }

    public void exitRoom(){
        this.mTRTCCloud.stopLocalAudio();
        this.mTRTCCloud.stopLocalPreview();
        this.mTRTCCloud.exitRoom();
    }

    public void playRemoteVideo(String userId){
        TXCloudVideoView mVideoView = getView(Integer.parseInt(userId));
        if(mVideoView != null){
            mVideoView.setVisibility(View.VISIBLE);
            this.mTRTCCloud.startRemoteView(userId, mVideoView);
        }
    }
    public void stopRemoteVideo(String userId){
        TXCloudVideoView mVideoView = getView(Integer.parseInt(userId));
        if(mVideoView != null){
            /// 关闭用户userId的视频画面
            this.mTRTCCloud.stopRemoteView(userId);
            mVideoView.setVisibility(View.GONE);
        }
    }
    public void setLocalViewFillMode(int mode){
        this.mTRTCCloud.setLocalViewFillMode(mode);
    }
    public void setRemoteViewFillMode(String userId, int mode){
        this.mTRTCCloud.setRemoteViewFillMode(userId, mode);
    }
}
