import {NativeModules, NativeEventEmitter} from "react-native";
import {
    VideoResolutions,
    VideoResolutionModes
} from "./Types";
const { RTCTencent } = NativeModules;
const TRTCEventEmitter = new NativeEventEmitter(RTCTencent);

export default class TRTCNative {
    constructor() {
        this._listeners = new Map();
    }

    /**
     * 初始化TRTC
     * @param options
     * @returns {TRTCNative}
     */
    static create(options){
        const _options = Object.assign({
            videoResolution: VideoResolutions.TRTC_VIDEO_RESOLUTION_640_360,
            videoResolutionMode: VideoResolutionModes.TRTC_VIDEO_RESOLUTION_MODE_PORTRAIT,
            videoFps: 15,
            videoBitrate: 1200,
        }, options)
        RTCTencent.init(_options);
        return new TRTCNative();
    }

    /**
     * 添加事件
     * @param event
     * @param listener
     * @returns {{remove: remove}}
     */
    addListener(event, listener) {
        const callback = (res) => {
            listener(res);
        };
        let map = this._listeners.get(event);
        if (map === undefined) {
            map = new Map();
            this._listeners.set(event, map);
        }
        TRTCEventEmitter.addListener(event, callback);
        map.set(listener, callback);
        return {
            remove: () => {
                this.removeListener(event, listener);
            }
        };
    }

    /**
     * 移除事件
     * @param event
     * @param listener
     */
    removeListener(event, listener) {
        const map = this._listeners.get(event);
        if (map === undefined)
            return;
        TRTCEventEmitter.removeListener(event, map.get(listener));
        map.delete(listener);
    }

    /**
     * 移除所有事件
     * @param event
     */
    removeAllListeners(event) {
        if (event === undefined) {
            this._listeners.forEach((value, key) => {
                TRTCEventEmitter.removeAllListeners(key);
            });
            this._listeners.clear();
            return;
        }
        TRTCEventEmitter.removeAllListeners(event);
        this._listeners.delete(event);
    }
    /**
     * 加入房间
     * @param options
     * @param scene
     */
    enterRoom(options, scene){
        RTCTencent.enterRoom(options, scene)
    }

    /**
     * 销毁
     */
    destroy(){
        this.removeAllListeners();
        RTCTencent.destroy(); // 退出房间并销毁
    }

    /**
     * 退出房间
     */
    exitRoom(){
        RTCTencent.exitRoom();
    }

    /**
     * 切换角色类型
     * @param role
     */
    switchRole(role){
        RTCTencent.switchRole(role)
    }

    /**
     * 开始本地视频预览
     */
    startLocalPreview(frontCamera){
        RTCTencent.startLocalPreview(frontCamera)
    }

    /**
     * 停止本地视频预览
     */
    stopLocalPreview(){
        RTCTencent.stopLocalPreview()
    }

    /**
     * 启用本地音频
     */
    startLocalAudio(){
        RTCTencent.startLocalAudio()
    }

    /**
     * 关闭本地音频
     */
    stopLocalAudio(){
        RTCTencent.stopLocalAudio()
    }


}




