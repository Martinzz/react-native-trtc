//
//  TRTCConst.m
//  RNTRTC
//
//  Created by martin on 2020/9/23.
//  Copyright © 2020 com.yk.martin. All rights reserved.
//

#import "TRTCConst.h"

@implementation TRTCConst

static TRTCConst *_person;
+ (instancetype)share {
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    _person = [[self alloc]init];
  });
  return _person;
}

+ (NSArray<NSString*>*) supportEvents {
    NSArray<NSString*>* array = @[
        TRTC_onError,
        TRTC_onEnterRoom,
        TRTC_onExitRoom,
        TRTC_onRemoteUserEnterRoom,
        TRTC_onRemoteUserLeaveRoom,
        TRTC_onUserVideoAvailable,
        TRTC_onUserAudioAvailable,
        TRTC_onUserSubStreamAvailable,
        TRTC_onFirstVideoFrame,
        TRTC_onFirstAudioFrame,
        TRTC_onSendFirstLocalVideoFrame,
        TRTC_onSendFirstLocalAudioFrame,
        TRTC_onNetworkQuality,
        TRTC_onConnectionLost,
        TRTC_onTryToReconnect,
        TRTC_onConnectionRecovery,
        TRTC_onSpeedTest,
        TRTC_onCameraDidReady,
        TRTC_onMicDidReady,
        TRTC_onAudioRouteChanged,
        TRTC_onUserVoiceVolume,
        TRTC_onStartPublishing,
        TRTC_onStopPublishing,
        TRTC_onStartPublishCDNStream,
        TRTC_onStopPublishCDNStream,
        TRTC_onScreenCaptureStarted,
        TRTC_onScreenCapturePaused,
        TRTC_onScreenCaptureResumed,
        TRTC_onScreenCaptureStopped
    ];
    return array;
}

@end
