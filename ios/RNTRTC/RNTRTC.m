//
//  RNTRTC.m
//  RNTRTC
//
//  Created by martin on 2020/9/22.
//  Copyright © 2020 com.yk.martin. All rights reserved.
//

#import "RNTRTC.h"

@implementation RNTRTC
{
    BOOL hasListeners;
}

RCT_EXPORT_MODULE(RTCTencent);

- (NSArray<NSString *> *)supportedEvents {
    return [TRTCConst supportEvents];
}

- (void) startObserving {
    hasListeners = YES;
}

- (void) stopObserving {
    hasListeners = NO;
}
- (void) sendEvent:(NSString *)evtName params:(NSDictionary *)params {
  if (hasListeners) {
    [self sendEventWithName:evtName body:params];
  }
}
/**
 初始化
 */
RCT_EXPORT_METHOD(init:(NSDictionary *)options){
    self.rtcEngine = [TRTCCloud sharedInstance];
    self.rtcEngine.delegate = self;
    [TRTCConst share].rtcEngine = self.rtcEngine;
    TRTCVideoEncParam *param = [[TRTCVideoEncParam alloc] init];
    if ([options objectForKey:@"videoResolution"]) {
        param.videoResolution = [options[@"videoResolution"] intValue];
    }
    if ([options objectForKey:@"videoResolutionMode"]) {
        param.resMode = [options[@"videoResolutionMode"] intValue];
    }
    if ([options objectForKey:@"videoFps"]) {
        param.videoFps = [options[@"videoFps"] intValue];
    }
    if ([options objectForKey:@"videoBitrate"]) {
        param.videoBitrate = [options[@"videoBitrate"] intValue];
    }
    if ([options objectForKey:@"minVideoBitrate"]) {
        param.minVideoBitrate = [options[@"minVideoBitrate"] intValue];
    }
    if ([options objectForKey:@"enableAdjustRes"]) {
        param.enableAdjustRes = [options[@"enableAdjustRes"] boolValue];
    }
    [self.rtcEngine setVideoEncoderParam:param];
}

/**
 加入房间
 */
RCT_EXPORT_METHOD(enterRoom:(NSDictionary *)options scene:(int) scene){
    TRTCParams *param = [[TRTCParams alloc] init];
    param.sdkAppId = [options[@"sdkAppId"] intValue];
    param.userId = options[@"userId"];
    param.roomId = [options[@"roomId"] intValue];
    param.userSig = options[@"userSig"];
    if ([options objectForKey:@"privateMapKey"]) {
        param.privateMapKey = options[@"privateMapKey"];
    }
    if ([options objectForKey:@"role"]) {
        param.role = [options[@"role"] intValue];
    }
    [self.rtcEngine enterRoom:param appScene:scene];
}
RCT_EXPORT_METHOD(setLocalViewRotation:(int )rotation){
    [self.rtcEngine setLocalViewRotation:rotation];
}
RCT_EXPORT_METHOD(setRemoteViewRotation:(NSString *)userId rotation:(int) rotation){
    [self.rtcEngine setRemoteViewRotation:userId rotation:rotation];
}
RCT_EXPORT_METHOD(setVideoEncoderRotation:(int )rotation){
    [self.rtcEngine setVideoEncoderRotation:rotation];
}
RCT_EXPORT_METHOD(setLocalViewMirror:(int )mirror){
    [self.rtcEngine setLocalViewMirror:mirror];
}
RCT_EXPORT_METHOD(setVideoEncoderMirror:(BOOL)mirror){
    [self.rtcEngine setVideoEncoderMirror:mirror];
}
RCT_EXPORT_METHOD(setGSensorMode:(int)mode){
    [self.rtcEngine setGSensorMode:mode];
}
RCT_EXPORT_METHOD(enableEncSmallVideoStream:(BOOL)enable options:(NSDictionary *)options resolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
    TRTCVideoEncParam *params = [TRTCVideoEncParam new];
    if ([options objectForKey:@"resMode"]) {
        params.resMode = [options[@"resMode"] intValue];
    }
    if ([options objectForKey:@"videoFps"]) {
        params.videoFps = [options[@"videoFps"] intValue];
    }
    if ([options objectForKey:@"videoBitrate"]) {
        params.videoBitrate = [options[@"videoBitrate"] intValue];
    }
    if ([options objectForKey:@"minVideoBitrate"]) {
        params.minVideoBitrate = [options[@"minVideoBitrate"] intValue];
    }
    if ([options objectForKey:@"enableAdjustRes"]) {
        params.enableAdjustRes = [options[@"enableAdjustRes"] boolValue];
    }
    int result = [self.rtcEngine enableEncSmallVideoStream:enable withQuality:params];
    resolve(@(result));
}
RCT_EXPORT_METHOD(setRemoteVideoStreamType:(NSString*)userId type:(int) type){
    [self.rtcEngine setRemoteVideoStreamType:userId type:type];
}
RCT_EXPORT_METHOD(setPriorRemoteVideoStreamType:(int) type){
    [self.rtcEngine setPriorRemoteVideoStreamType:type];
}
RCT_EXPORT_METHOD(snapshotVideo:(NSString*)userId type:(int)type resolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
    [self.rtcEngine snapshotVideo:userId type:type completionBlock:^(UIImage *image) {
        NSData *imageData = UIImagePNGRepresentation(image);
        NSString *base64 = [imageData base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength];
        resolve(base64);
    }];
}
RCT_EXPORT_METHOD(setAudioQuality:(int) quality){
    [self.rtcEngine setAudioQuality:quality];
}
/**
 切换角色类型
 */
RCT_EXPORT_METHOD(switchRole:(int )role){
    [self.rtcEngine switchRole:role];
}

/**
 启用本地音频
 */
RCT_EXPORT_METHOD(startLocalAudio){
    [self.rtcEngine startLocalAudio];
}

/**
 启用本地音频
 */
RCT_EXPORT_METHOD(stopLocalAudio){
    [self.rtcEngine stopLocalAudio];
}
/**
 退出房间
 */
RCT_EXPORT_METHOD(exitRoom){
    [self.rtcEngine exitRoom];
}
/**
 开始向腾讯云的直播 CDN 推流
 */
RCT_EXPORT_METHOD(startPublishing:(NSString *)streamId streamType:(int) streamType){
    [self.rtcEngine startPublishing:streamId type:streamType];
}

/**
 停止向腾讯云的直播 CDN 推流
 */
RCT_EXPORT_METHOD(stopPublishing){
    [self.rtcEngine stopPublishing];
}

RCT_EXPORT_METHOD(startPublishCDNStream:(int)appId bizId:(int) bizId url:(NSString*)url){
    TRTCPublishCDNParam *params = [TRTCPublishCDNParam new];
    params.appId = appId;
    params.bizId = bizId;
    params.url = url;
    [self.rtcEngine startPublishCDNStream:params];
}
RCT_EXPORT_METHOD(stopPublishCDNStream){
    [self.rtcEngine stopPublishCDNStream];
}
/**
 开始进行网络测速（视频通话期间请勿测试，以免影响通话质量）
*/
RCT_EXPORT_METHOD(startSpeedTest:(int)sdkAppId userId:(NSString *) userId userSig:(NSString *) userSig){
    [self.rtcEngine startSpeedTest:sdkAppId userId:userId userSig:userSig completion:^(TRTCSpeedTestResult *result, NSInteger completedCount, NSInteger totalCount) {
        [self sendEvent:TRTC_onSpeedTest params:@{
            @"finishedCount": @(completedCount),
            @"totalCount": @(totalCount),
            @"result": @{
                @"ip": result.ip,
                @"quality": @(result.quality),
                @"upLostRate": @(result.upLostRate),
                @"downLostRate": @(result.downLostRate),
                @"rtt": @(result.rtt)
            }
        }];
    }];
}

/**
 停止服务器测速
 */
RCT_EXPORT_METHOD(stopSpeedTest){
    [self.rtcEngine stopSpeedTest];
}

/**
 静音/取消静音本地的音频
 */
RCT_EXPORT_METHOD(muteLocalAudio:(BOOL) mute){
    [self.rtcEngine muteLocalAudio: mute];
}

/**
 设置音频路由
 */
RCT_EXPORT_METHOD(setAudioRoute:(int) route){
    [self.rtcEngine setAudioRoute: route];
}

/**
 销毁
 */
RCT_EXPORT_METHOD(destroy){
    [TRTCCloud destroySharedIntance];
}
/**
 静音/取消静音指定的远端用户的声音
 */
RCT_EXPORT_METHOD(muteRemoteAudio:(NSString *)userId mute:(BOOL) mute){
    [self.rtcEngine muteRemoteAudio: userId mute:mute];
}
/**
 静音/取消静音所有用户的声音
 */
RCT_EXPORT_METHOD(muteAllRemoteAudio:(BOOL) mute){
    [self.rtcEngine muteAllRemoteAudio:mute];
}
/**
 设置某个远程用户的播放音量
 */
RCT_EXPORT_METHOD(setRemoteAudioVolume:(NSString *)userId volume:(int) volume){
    [self.rtcEngine setRemoteAudioVolume: userId volume:volume];
}
/**
 设置 SDK 采集音量
 */
RCT_EXPORT_METHOD(setAudioCaptureVolume:(int) volume){
    [self.rtcEngine setAudioCaptureVolume:volume];
}

RCT_EXPORT_METHOD(getAudioCaptureVolume:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSInteger volume = [self.rtcEngine getAudioCaptureVolume];
    resolve(@(volume));
}
RCT_EXPORT_METHOD(setAudioPlayoutVolume:(int) volume){
    [self.rtcEngine setAudioPlayoutVolume:volume];
}
RCT_EXPORT_METHOD(getAudioPlayoutVolume:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSInteger volume = [self.rtcEngine getAudioPlayoutVolume];
    resolve(@(volume));
}
RCT_EXPORT_METHOD(enableAudioVolumeEvaluation:(int) intervalMs){
    [self.rtcEngine enableAudioVolumeEvaluation:intervalMs];
}
RCT_EXPORT_METHOD(startAudioRecording:(NSString *)filePath resolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    TRTCAudioRecordingParams *params = [[TRTCAudioRecordingParams alloc] init];
    params.filePath = filePath;
    int volume = [self.rtcEngine startAudioRecording: params];
    resolve(@(volume));
}
RCT_EXPORT_METHOD(stopAudioRecording){
    [self.rtcEngine stopAudioRecording];
}
RCT_EXPORT_METHOD(switchCamera){
    [self.rtcEngine switchCamera];
}

#pragma mark - TRTCCloudDelegate
- (void)onError:(TXLiteAVError)errCode errMsg:(nullable NSString *)errMsg extInfo:(nullable NSDictionary*)extInfo{
    [self sendEvent:TRTC_onError params:@{
        @"message": errMsg,
        @"code": @(errCode)
    }];
}
- (void)onEnterRoom:(NSInteger)result{
    [self sendEvent:TRTC_onEnterRoom params:@{
        @"result": @(result)
    }];
}
- (void)onExitRoom:(NSInteger)reason{
    [self sendEvent:TRTC_onExitRoom params:@{
        @"reason": @(reason)
    }];
}
- (void)onRemoteUserEnterRoom:(NSString *)userId{
    [self sendEvent:TRTC_onRemoteUserEnterRoom params:@{
        @"userId": userId
    }];
}
- (void)onRemoteUserLeaveRoom:(NSString *)userId reason:(NSInteger)reason{
    [self sendEvent:TRTC_onRemoteUserLeaveRoom params:@{
        @"userId": userId,
        @"reason": @(reason)
    }];
}
- (void)onUserVideoAvailable:(NSString *)userId available:(BOOL)available{
    [self sendEvent:TRTC_onUserVideoAvailable params:@{
        @"userId": userId,
        @"available": @(available)
    }];
}
- (void)onUserAudioAvailable:(NSString *)userId available:(BOOL)available{
    [self sendEvent:TRTC_onUserAudioAvailable params:@{
        @"userId": userId,
        @"available": @(available)
    }];
}
- (void)onFirstVideoFrame:(NSString*)userId streamType:(TRTCVideoStreamType)streamType width:(int)width height:(int)height{
    [self sendEvent:TRTC_onFirstVideoFrame params:@{
        @"userId": userId == nil ? [NSNull null] : userId,
        @"streamType": @(streamType),
        @"width": @(width),
        @"height": @(height)
    }];
}
- (void)onFirstAudioFrame:(NSString*)userId{
    [self sendEvent:TRTC_onFirstAudioFrame params:@{
        @"userId": userId
    }];
}
- (void)onSendFirstLocalVideoFrame: (TRTCVideoStreamType)streamType{
    [self sendEvent:TRTC_onSendFirstLocalVideoFrame params:@{
        @"streamType": @(streamType)
    }];
}
- (void)onSendFirstLocalAudioFrame{
    [self sendEvent:TRTC_onSendFirstLocalAudioFrame params: nil];
}
- (void)onNetworkQuality: (TRTCQualityInfo*)localQuality remoteQuality:(NSArray<TRTCQualityInfo*>*)remoteQuality{
    NSMutableArray *array = [NSMutableArray new];
    for (TRTCQualityInfo* info in remoteQuality) {
        [array addObject:@{
            @"userId": info.userId,
            @"quality": @(info.quality)
        }];
    }
    [self sendEvent:TRTC_onNetworkQuality params:@{
        @"localQuality": @{
            @"userId": localQuality.userId == nil ? [NSNull null] : localQuality.userId,
            @"quality": @(localQuality.quality)
        },
        @"remoteQuality": array
     }];
}
- (void)onConnectionLost{
    [self sendEvent:TRTC_onConnectionLost params: nil];
}
- (void)onTryToReconnect{
    [self sendEvent:TRTC_onTryToReconnect params: nil];
}
- (void)onConnectionRecovery{
    [self sendEvent:TRTC_onConnectionRecovery params: nil];
}
- (void)onCameraDidReady{
    [self sendEvent:TRTC_onCameraDidReady params: nil];
}
- (void)onMicDidReady{
    [self sendEvent:TRTC_onMicDidReady params: nil];
}
- (void)onAudioRouteChanged:(TRTCAudioRoute)route fromRoute:(TRTCAudioRoute)fromRoute{
    [self sendEvent:TRTC_onAudioRouteChanged params:@{
         @"newRoute": @(route),
         @"oldRoute": @(fromRoute)
     }];
}

- (void)onUserVoiceVolume:(NSArray<TRTCVolumeInfo *> *)userVolumes totalVolume:(NSInteger)totalVolume{
    NSMutableArray *array = [NSMutableArray new];
    for (TRTCVolumeInfo* info in userVolumes) {
        [array addObject:@{
            @"userId": info.userId == nil ? [NSNull null] : info.userId,
            @"volume": @(info.volume)
        }];
    }
    [self sendEvent:TRTC_onUserVoiceVolume params:@{
        @"totalVolume": @(totalVolume),
        @"userVolumes": array
     }];
}
- (void)onStartPublishing:(int)err errMsg:(NSString*)errMsg{
    [self sendEvent:TRTC_onStartPublishing params:@{
       @"err": @(err),
       @"errMsg": errMsg
    }];
}
- (void)onStopPublishing:(int)err errMsg:(NSString*)errMsg{
    [self sendEvent:TRTC_onStopPublishing params:@{
       @"err": @(err),
       @"errMsg": errMsg
    }];
}
- (void)onStartPublishCDNStream:(int)err errMsg:(NSString *)errMsg{
    [self sendEvent:TRTC_onStartPublishCDNStream params:@{
       @"err": @(err),
       @"errMsg": errMsg
    }];
}
- (void)onStopPublishCDNStream:(int)err errMsg:(NSString *)errMsg{
    [self sendEvent:TRTC_onStopPublishCDNStream params:@{
       @"err": @(err),
       @"errMsg": errMsg
    }];
}
@end
