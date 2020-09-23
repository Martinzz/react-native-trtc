//
//  TRTCConst.h
//  RNTRTC
//
//  Created by martin on 2020/9/23.
//  Copyright © 2020 com.yk.martin. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

#define TRTC_onError @"onError"
#define TRTC_onEnterRoom @"onEnterRoom"
#define TRTC_onExitRoom @"onExitRoom"
#define TRTC_onRemoteUserEnterRoom @"onRemoteUserEnterRoom"
#define TRTC_onRemoteUserLeaveRoom @"onRemoteUserLeaveRoom"
#define TRTC_onUserVideoAvailable @"onUserVideoAvailable"
#define TRTC_onUserAudioAvailable @"onUserAudioAvailable"
#define TRTC_onFirstVideoFrame @"onFirstVideoFrame"
#define TRTC_onFirstAudioFrame @"onFirstAudioFrame"
#define TRTC_onSendFirstLocalVideoFrame @"onSendFirstLocalVideoFrame"
#define TRTC_onSendFirstLocalAudioFrame @"onSendFirstLocalAudioFrame"
#define TRTC_onNetworkQuality @"onNetworkQuality"
#define TRTC_onConnectionLost @"onConnectionLost"
#define TRTC_onTryToReconnect @"onTryToReconnect"
#define TRTC_onConnectionRecovery @"onConnectionRecovery"
#define TRTC_onSpeedTest @"onSpeedTest"
#define TRTC_onCameraDidReady @"onCameraDidReady"
#define TRTC_onMicDidReady @"onMicDidReady"
#define TRTC_onAudioRouteChanged @"onAudioRouteChanged"
#define TRTC_onUserVoiceVolume @"onUserVoiceVolume"
#define TRTC_onStartPublishing @"onStartPublishing"
#define TRTC_onStopPublishing @"onStopPublishing"
#define TRTC_onStartPublishCDNStream @"onStartPublishCDNStream"
#define TRTC_onStopPublishCDNStream @"onStopPublishCDNStream"
#define TRTC_onScreenCaptureStarted @"onScreenCaptureStarted"
#define TRTC_onScreenCapturePaused @"onScreenCapturePaused"
#define TRTC_onScreenCaptureResumed @"onScreenCaptureResumed"
#define TRTC_onScreenCaptureStopped @"onScreenCaptureStopped"

@interface TRTCConst : NSObject

+ (NSArray<NSString*> *) supportEvents;
@end

NS_ASSUME_NONNULL_END
