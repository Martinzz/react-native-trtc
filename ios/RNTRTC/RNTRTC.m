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

- (void) startObserving {
    hasListeners = YES;
}

- (void) stopObserving {
    hasListeners = NO;
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
RCT_EXPORT_METHOD(enterRoom:(NSDictionary *)options scene:(BOOL) scene){
    TRTCParams *param = [[TRTCParams alloc] init];
    param.sdkAppId = [options[@"sdkAppId"] intValue];
    param.userId = options[@"userId"];
    param.roomId = [options[@"roomId"] intValue];
}

@end
