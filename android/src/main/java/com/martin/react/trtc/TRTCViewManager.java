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
    }

    @ReactProp(name = "frontCamera")
    public void setFrontCamera(final TRTCVideoView trtcVideoView, boolean frontCamera) {
        trtcVideoView.setFrontCamera(frontCamera);
    }

    @ReactProp(name = "uid")
    public void setUid(final TRTCVideoView trtcVideoView, final String uid) {
        trtcVideoView.setUid(uid);
    }


}