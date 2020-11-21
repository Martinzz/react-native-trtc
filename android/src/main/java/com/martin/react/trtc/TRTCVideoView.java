package com.martin.react.trtc;

import android.content.Context;
import android.widget.FrameLayout;

import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.core.ReactChoreographer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;


/**
 * @Description TODO
 * @Author martin
 * @Date 2020-09-18 17:02
 */
public class TRTCVideoView extends FrameLayout {

    private boolean mLayoutEnqueued = false;
    private TXCloudVideoView surface;
    private Boolean isSub =false;
    public TRTCVideoView(Context context) {
        super(context);
        surface = new TXCloudVideoView(context);
        addView(surface);
    }
    private final ChoreographerCompat.FrameCallback mLayoutCallback = new ChoreographerCompat.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            mLayoutEnqueued = false;
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };
    @Override
    public void requestLayout() {
        super.requestLayout();

        if (!mLayoutEnqueued && mLayoutCallback != null) {
            mLayoutEnqueued = true;
            // we use NATIVE_ANIMATED_MODULE choreographer queue because it allows us to catch the current
            // looper loop instead of enqueueing the update in the next loop causing a one frame delay.
            ReactChoreographer.getInstance().postFrameCallback(
                    ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE,
                    mLayoutCallback);
        }
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

    public void setSubUid(String subUid){
        isSub=true
        surface.setUserId(subUid);
        getEngine().setRemoteSubStreamViewRotation(subUid,surface);
    }

    public void setRenderMode(int renderMode){
        String userId = surface.getUserId();
        if("".equals(userId)){
            getEngine().setLocalViewFillMode(renderMode);
        }else{
            if(isSub){
                getEngine().setRemoteAudioVolume(userId,renderMode);
            }else{
                getEngine().setRemoteViewFillMode(userId, renderMode);
            }

        }
    }

    public void setMirrorMode(int mirrorMode){
        getEngine().setLocalViewMirror(mirrorMode);
    }

    public void stopPlayView(){
        String userId = surface.getUserId();
        if("".equals(userId)){
            getEngine().stopLocalPreview();
        }
    }
}
