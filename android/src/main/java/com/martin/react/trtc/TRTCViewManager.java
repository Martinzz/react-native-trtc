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
    @Override
    public void onDropViewInstance(TRTCVideoView trtcVideoView) {
        super.onDropViewInstance(trtcVideoView);
        trtcVideoView.stopPlayView();
    }
    @ReactProp(name = "renderMode")
    public void setRenderMode(final TRTCVideoView trtcVideoView, int renderMode) {
        trtcVideoView.setRenderMode(renderMode);
    }
    @ReactProp(name = "mirrorMode")
    public void setMirrorMode(final TRTCVideoView trtcVideoView, int mirrorMode) {
        trtcVideoView.setMirrorMode(mirrorMode);
    }
    @ReactProp(name = "uid")
    public void setUid(final TRTCVideoView trtcVideoView, final String uid) {
        trtcVideoView.setUid(uid);
    }
}
