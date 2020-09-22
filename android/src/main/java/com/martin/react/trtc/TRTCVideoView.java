package com.martin.react.trtc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloudDef;


/**
 * @Description TODO
 * @Author martin
 * @Date 2020-09-18 17:02
 */
public class TRTCVideoView extends LinearLayout {

    private boolean frontCamera = true;
    private TXCloudVideoView surface;
    private int renderMode = TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL;

    public TRTCVideoView(Context context) {
        super(context);
        surface = new TXCloudVideoView(context);
        addView(surface);
    }

    public void setFrontCamera(boolean front){
        this.frontCamera = front;
        setupVideoView();
    }

    public void setUid(String uid){
        surface.setUserId(uid);
        setupVideoView();
    }

    public void setRenderMode(int renderMode){
        String userId = surface.getUserId();
        if("0".equals(userId)){
            TRTCManager.getInstance().mTRTCCloud.setLocalViewFillMode(renderMode);
        }else{
            TRTCManager.getInstance().mTRTCCloud.setRemoteViewFillMode(userId, renderMode);
        }
    }

    public void stop(){
        String userId = surface.getUserId();
        if("0".equals(userId)){
            TRTCManager.getInstance().mTRTCCloud.stopLocalPreview();
        }else{
            TRTCManager.getInstance().mTRTCCloud.stopRemoteView(userId);
        }
    }

    private void setupVideoView() {
        String userId = surface.getUserId();
        if("0".equals(userId)){
            TRTCManager.getInstance().mTRTCCloud.startLocalPreview(frontCamera, surface);
        } else {
            TRTCManager.getInstance().mTRTCCloud.startRemoteView(userId, surface);
        }
    }
}
