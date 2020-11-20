//
//  TRTCViewManager.m
//  RNTRTC
//
//  Created by martin on 2020/9/24.
//  Copyright © 2020 com.yk.martin. All rights reserved.
//

#import "TRTCViewManager.h"
#import "TRTCVideoView.h"

@implementation TRTCViewManager

RCT_EXPORT_MODULE(TRTCVideoView)


RCT_CUSTOM_VIEW_PROPERTY(uid, NSString, TRTCVideoView) {
    view.uid = [RCTConvert NSString:json];
}
RCT_CUSTOM_VIEW_PROPERTY(renderMode, NSInteger, TRTCVideoView) {
    view.mode = [RCTConvert NSInteger:json];
}
RCT_CUSTOM_VIEW_PROPERTY(mirrorMode, NSInteger, TRTCVideoView) {
    view.mirrorMode = [RCTConvert NSInteger:json];
}
RCT_CUSTOM_VIEW_PROPERTY(isSubStream, BOOL, TRTCVideoView){
    view.isSubStream =[RCTConvert BOOL:json];
}

- (UIView *)view {
    return [TRTCVideoView new];
}

@end
