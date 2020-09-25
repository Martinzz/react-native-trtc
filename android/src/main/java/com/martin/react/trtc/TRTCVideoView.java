package com.martin.react.trtc;

import android.content.Context;
import android.widget.LinearLayout;

import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;


/**
 * @Description TODO
 * @Author martin
 * @Date 2020-09-18 17:02
 */
public class TRTCVideoView extends LinearLayout {

    private TXCloudVideoView surface;

    public TRTCVideoView(Context context) {
        super(context);
        surface = new TXCloudVideoView(context);
        addView(surface);
    }
    private TRTCCloud getEngine(){
        return TRTCManager.getInstance().mTRTCCloud;
    }
    public void setUid(String userId){
        surface.setUserId(userId);
        if(!"".equals(userId)){
            getEngine().startRemoteView(userId, surface);
        }else{
            getEngine().startLocalPreview(true, surface);
        }
    }
    public void setRenderMode(int renderMode){
        String userId = surface.getUserId();
        if("".equals(userId)){
            getEngine().setLocalViewFillMode(renderMode);
        }else{
            getEngine().setRemoteViewFillMode(userId, renderMode);
        }
    }

    public void setMirrorMode(int mirrorMode){
        getEngine().setLocalViewMirror(mirrorMode);
    }

    public void stopPlayView(){
        String userId = surface.getUserId();
        if("".equals(userId)){
            getEngine().stopLocalPreview();
        }else{
            getEngine().stopRemoteView(userId);
        }
    }
}
