//
//  RNTRTC.m
//  RNTRTC
//
//  Created by martin on 2020/9/22.
//  Copyright © 2020 com.yk.martin. All rights reserved.
//

#import "RNTRTC.h"
#import "TRTCConst.h"

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
        param.enableAdjustRes = [options[@"enableAdjustRes"] intValue];
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
    if ([options objectForKey:@"privateMapKey"]) {
        param.privateMapKey = options[@"privateMapKey"];
    }
    [self.rtcEngine enterRoom:param appScene:scene];
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
@end
