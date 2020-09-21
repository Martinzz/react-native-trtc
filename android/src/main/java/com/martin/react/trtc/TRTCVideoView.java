package com.martin.react.trtc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.tencent.trtc.TRTCCloudDef;


/**
 * @Description TODO
 * @Author martin
 * @Date 2020-09-18 17:02
 */
public class TRTCVideoView extends LinearLayout {

    private boolean showLocalVideo;
    private Integer remoteUid;
    private int renderMode = TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL;

    public TRTCVideoView(Context context) {
        super(context);
    }

    public TRTCVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public TRTCVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isShowLocalVideo() {
        return showLocalVideo;
    }

    public void setShowLocalVideo(boolean showLocalVideo) {
        this.showLocalVideo = showLocalVideo;
    }

    public Integer getRemoteUid() {
        return remoteUid;
    }

    public void setRemoteUid(Integer remoteUid) {
        this.remoteUid = remoteUid;
    }

    public void setRenderMode(int mode){
        this.renderMode = mode;
    }

    public int getRenderMode(){
        return this.renderMode;
    }

}
