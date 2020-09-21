package com.martin.react.trtc;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * @Description TODO
 * @Author martin
 * @Date 2020-09-18 17:04
 */
public class TRTCViewManager extends SimpleViewManager<TRTCVideoView> {
    public static final String REACT_CLASS = "TRTCVideoView";
    public TXCloudVideoView videoView;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected TRTCVideoView createViewInstance(ThemedReactContext reactContext) {
        return new TRTCVideoView(reactContext);
    }


    @ReactProp(name = "mode")
    public void setRenderMode(final TRTCVideoView trtcVideoView, int renderMode) {
        trtcVideoView.setRenderMode(renderMode);
        if(trtcVideoView.isShowLocalVideo()){
            TRTCManager.getInstance().setLocalViewFillMode(renderMode);
        }else{
            TRTCManager.getInstance().setRemoteViewFillMode(String.valueOf(trtcVideoView.getRemoteUid()), renderMode);
        }
    }

    @ReactProp(name = "showLocalVideo")
    public void setShowLocalVideo(final TRTCVideoView trtcVideoView, boolean showLocalVideo) {
        trtcVideoView.setShowLocalVideo(showLocalVideo);
        if (showLocalVideo) {
            videoView = TRTCManager.getInstance().setupLocalVideo();
            trtcVideoView.addView(videoView);
        }
    }

    @ReactProp(name = "remoteUid")
    public void setRemoteUid(final TRTCVideoView trtcVideoView, final int remoteUid) {
        trtcVideoView.setRemoteUid(remoteUid);
        if (remoteUid != 0) {
            videoView = TRTCManager.getInstance().setupRemoteVideo(remoteUid);
            trtcVideoView.addView(videoView);
        }
    }


}
