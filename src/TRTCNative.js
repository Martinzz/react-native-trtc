import {NativeModules, NativeEventEmitter} from "react-native";
import {
    VideoResolution,
} from "./Types";
const { RTCTencent, TRTCBeauty} = NativeModules;
const TRTCEventEmitter = new NativeEventEmitter(RTCTencent);
/**
 * @ignore
 */
let engine;
export default class TRTCNative {
    constructor() {
        this._listeners = new Map();
    }
    static instance() {
        if (engine) {
            return engine;
        }
        else {
            throw new Error('please create RtcEngine first');
        }
    }
    /**
     * 初始化TRTC
     * @param options
     * @returns {TRTCNative}
     */
    static create(options){
        if (engine)
            return engine;
        RTCTencent.init(Object.assign({}, options));
        engine = new TRTCNative();
        return engine;
    }

    /**
     * 设置 Log 输出级别
     * @param level
     */
    static setLogLevel(level){
        RTCTencent.setLogLevel(level);
    }

    /**
     * 启用或禁用控制台日志打印
     * @param enable 指定是否启用，默认为禁止状态

     */
    static setConsoleEnabled(enable){
        RTCTencent.setConsoleEnabled(enable);
    }

    /**
     * 启用或禁用 Log 的本地压缩。
     * @param enable 指定是否启用，默认为启动状态
     */
    static setLogCompressEnabled(enable){
        RTCTencent.setLogCompressEnabled(enable);
    }

    /**
     * 修改日志保存路径
     * @param path
     * ios 日志文件默认保存在 sandbox Documents/log 下，如需修改，必须在所有方法前调用。
     * android 日志文件默认保存在 /app私有目录/files/log/tencent/liteav/ 下，如需修改, 必须在所有方法前调用，并且保证目录存在及应用有目录的读写权限。
     */
    static setLogDirPath(path){
        RTCTencent.setLogDirPath(path);
    }

    /**
     * 获取 SDK 版本信息
     */
    static getSDKVersion(){
        return RTCTencent.getSDKVersion();
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
     * 设置本地图像的顺时针旋转角度
     * @param rotation
     */
    setLocalViewRotation(rotation){
        RTCTencent.setLocalViewRotation(rotation);
    }

    /**
     * 设置远端图像的顺时针旋转角度
     * @param userId
     * @param rotation
     */
    setRemoteViewRotation(userId, rotation){
        RTCTencent.setRemoteViewRotation(userId, rotation);
    }

    /**
     * 设置视频编码输出的画面方向，即设置远端用户观看到的和服务器录制的画面方向
     * @param rotation
     */
    setVideoEncoderRotation(rotation){
        RTCTencent.setVideoEncoderRotation(rotation);
    }

    /**
     * 设置本地摄像头预览画面的镜像模式
     * @param mirror
     */
    setLocalViewMirror(mirror){
        RTCTencent.setLocalViewMirror(mirror);
    }

    /**
     * 设置编码器输出的画面镜像模式
     * 该接口不改变本地摄像头的预览画面，但会改变另一端用户看到的（以及服务器录制的）画面效果
     * @param mirror 是否开启远端镜像，YES：开启远端画面镜像；NO：关闭远端画面镜像，默认值：NO。
     */
    setVideoEncoderMirror(mirror){
        RTCTencent.setVideoEncoderMirror(mirror);
    }

    /**
     * 设置重力感应的适应模式
     * @param mode 默认值：TRTCGSensorMode_UIAutoLayout
     */
    setGSensorMode(mode){
        RTCTencent.setGSensorMode(mode);
    }

    /**
     * 开启大小画面双路编码模式
     * @param enable 是否开启小画面编码，默认值：NO
     * @param smallVideoEncParam 小流的视频参数
     * @return Promise 0：成功；-1：大画面已经是最低画质
     */
    enableEncSmallVideoStream(enable, smallVideoEncParam){
        return RTCTencent.enableEncSmallVideoStream(enable, smallVideoEncParam);
    }

    /**
     * 选定观看指定 uid 的大画面或小画面
     * @param userId
     * @param type 视频流类型，即选择看大画面或小画面，默认为大画面
     */
    setRemoteVideoStreamType(userId, type){
        RTCTencent.setRemoteVideoStreamType(userId, type);
    }

    /**
     * 设定观看方优先选择的视频质量
     * 低端设备推荐优先选择低清晰度的小画面。 如果对方没有开启双路视频模式，则此操作无效。
     * @param type
     */
    setPriorRemoteVideoStreamType(type){
        RTCTencent.setPriorRemoteVideoStreamType(type);
    }

    /**
     * 截取本地、远程主路和远端辅流的视频画面
     * @param userId
     * @param type
     * @return Promise
     */
    snapshotVideo(userId, type){
        return RTCTencent.snapshotVideo(userId, type);
    }

    /**
     * 设置音频质量
     * @param quality AudioQuality
     */
    setAudioQuality(quality){
        RTCTencent.setAudioQuality(quality);
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
     * 开始向友商云的直播 CDN 转推
     * @param appId
     * @param bizId
     * @param url
     */
    startPublishCDNStream(appId, bizId, url){
        RTCTencent.startPublishCDNStream(appId, bizId, url);
    }

    /**
     * 停止向非腾讯云地址转推
     */
    stopPublishCDNStream(){
        RTCTencent.stopPublishCDNStream();
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
        engine = undefined;
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
     * @returns Promise
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
     * @returns Promise
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
    enableAudioVolumeEvaluation(intervalMs = 300){
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

    /**
     * 设置美颜类型
     * @param beautyStyle 三种美颜风格：0 ：光滑 1：自然 2：朦胧
     */
    setBeautyStyle(beautyStyle){
        TRTCBeauty.setBeautyStyle(beautyStyle)
    }

    /**
     * 设置美颜级别
     * @param beautyLevel 美颜级别，取值范围0 - 9； 0表示关闭，1 - 9值越大，效果越明显。
     */
    setBeautyLevel(beautyLevel){
        TRTCBeauty.setBeautyLevel(beautyLevel)
    }

    /**
     * 设置美白级别
     * @param whitenessLevel 美白级别，取值范围0 - 9； 0表示关闭，1 - 9值越大，效果越明显。
     */
    setWhitenessLevel(whitenessLevel){
        TRTCBeauty.setWhitenessLevel(whitenessLevel)
    }

    /**
     * 开启清晰度增强
     * @param enable
     */
    enableSharpnessEnhancement(enable){
        TRTCBeauty.enableSharpnessEnhancement(enable)
    }

    /**
     * 设置红润级别
     * @param ruddyLevel 取值范围0 - 9； 0表示关闭，1 - 9值越大，效果越明显。
     */
    setRuddyLevel(ruddyLevel){
        TRTCBeauty.setRuddyLevel(ruddyLevel)
    }
}




