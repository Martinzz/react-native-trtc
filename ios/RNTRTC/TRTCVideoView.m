//
//  TRTCVideoView.m
//  RNTRTC
//
//  Created by martin on 2020/9/24.
//  Copyright © 2020 com.yk.martin. All rights reserved.
//

#import "TRTCVideoView.h"

@implementation TRTCVideoView

- (instancetype)init{

  if (self == [super init]) {
    _rtcEngine = [TRTCConst share].rtcEngine;
  }

  return self;
}
-(void)setMode:(NSInteger)mode{
    _mode = mode;
    if([@"" isEqual: _uid]&[@"" isEqual:_subUid]){
        [self.rtcEngine setLocalViewFillMode:mode];
    }else{
        if([@"" isEqual: _uid]){
            [self.rtcEngine setRemoteSubStreamViewFillMode:_subUid mode:mode];

        }else{
            [self.rtcEngine setRemoteViewFillMode:_uid mode:mode];

        }
    }
}
-(void)setMirrorMode:(NSInteger)mirrorMode{
    _mirrorMode = mirrorMode;
    [self.rtcEngine setLocalViewMirror:_mirrorMode];
}

-(void)setSubUid:(NSString *)subUid{
    _subUid=subUid;
    [self.rtcEngine startRemoteSubStreamView:subUid view:self];
}

-(void)setUid:(NSString *)uid {
    _uid = uid;
    if(![@"" isEqual: uid]){
        [self.rtcEngine startRemoteView:uid view:self];
    }else{
        [self.rtcEngine startLocalPreview: YES view:self];
    }
}

-(void) willMoveToSuperview:(UIView *)newSuperview {
  [super willMoveToSuperview:newSuperview];
  if (!newSuperview) {
    if([@"" isEqual: _uid]&[@"" isEqual:_subUid]){
        [self.rtcEngine stopLocalPreview];
    }else{
        if([_subUid isEqual:@""]){
            [self.rtcEngine stopRemoteView:_subUid];
        }else{

            [self.rtcEngine stopRemoteSubStreamView:_uid];
        }

    }
  }
}
@end
