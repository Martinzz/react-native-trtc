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
     * 开始进行网络测速（视频通话期间请勿测试，以免影响通话质量）
     * @param sdkAppId
     * @param userId
     * @param userSig
     */
    startSpeedTest(sdkAppId, userId, userSig){
        RTCTencent.startSpeedTest(sdkAppId, userId, userSig);
    }

    /**
     * 停止服务器测速
     */
    stopSpeedTest(){
        RTCTencent.stopSpeedTest();
    }

    /**
     * 开始向腾讯云的直播 CDN 推流
     * @param streamId
     * @param streamType
     */
    startPublishing(streamId, streamType){
        RTCTencent.startPublishing(streamId, streamType);
    }

    /**
     * 停止向腾讯云的直播 CDN 推流
     */
    stopPublishing(){
        RTCTencent.stopPublishing();
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

    /**
     * 静音/取消静音本地的音频
     * @param mute
     */
    muteLocalAudio(mute){
        RTCTencent.muteLocalAudio(mute)
    }

    /**
     * 设置音频路由
     * @param route
     */
    setAudioRoute(route){
        RTCTencent.setAudioRoute(route)
    }

    /**
     * 静音/取消静音指定的远端用户的声音
     * @param userId
     * @param mute
     */
    muteRemoteAudio(userId, mute){
        RTCTencent.muteRemoteAudio(userId, mute)
    }

    /**
     * 静音/取消静音所有用户的声音
     */
    muteAllRemoteAudio(mute){
        RTCTencent.muteAllRemoteAudio(mute)
    }

    /**
     * 设置某个远程用户的播放音量
     * @param userId
     * @param volume 音量大小，取值0 - 100
     */
    muteAllRemoteAudio(userId, volume){
        RTCTencent.setRemoteAudioVolume(userId, volume)
    }

    /**
     * 设置 SDK 采集音量
     * @param volume 音量大小，取值0 - 100，默认值为100
     */
    setAudioCaptureVolume(volume){
        RTCTencent.setAudioCaptureVolume(volume)
    }

    /**
     * 获取 SDK 采集音量
     * @returns {*}
     */
    getAudioCaptureVolume(){
        return RTCTencent.getAudioCaptureVolume()
    }

    /**
     * 设置 SDK 播放音量
     * @param volume 音量大小，取值0 - 100，默认值为100
     */
    setAudioPlayoutVolume(volume){
        RTCTencent.setAudioPlayoutVolume(volume)
    }

    /**
     * 获取 SDK 播放音量
     * @returns {*}
     */
    getAudioPlayoutVolume(){
        return RTCTencent.getAudioPlayoutVolume()
    }

    /**
     * 启用音量大小提示
     * 开启后会在 onUserVoiceVolume 中获取到 SDK 对音量大小值的评估。如需打开此功能，请在 startLocalAudio() 之前调用。
     * @param intervalMs 决定了 onUserVoiceVolume 回调的触发间隔，单位为ms，最小间隔为100ms，
     * 如果小于等于0则会关闭回调，建议设置为300ms；详细的回调规则请参考 onUserVoiceVolume 的注释说明
     */
    enableAudioVolumeEvaluation(intervalMs){
        RTCTencent.enableAudioVolumeEvaluation(intervalMs)
    }

    /**
     * 开始录音
     * @param filePath 录音文件保存路径
     * @returns {Promise<void>} 0：成功；-1：录音已开始；-2：文件或目录创建失败；-3：后缀指定的音频格式不支持
     */
    startAudioRecording(filePath){
        return RTCTencent.startAudioRecording(filePath);
    }

    /**
     * 停止录音
     */
    stopAudioRecording(){
        RTCTencent.stopAudioRecording()
    }

    /**
     * 切换摄像头
     */
    switchCamera(){
        RTCTencent.switchCamera()
    }
}




