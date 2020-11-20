//
//  TRTCVideoView.h
//  RNTRTC
//
//  Created by martin on 2020/9/24.
//  Copyright © 2020 com.yk.martin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TRTCConst.h"

NS_ASSUME_NONNULL_BEGIN

@interface TRTCVideoView : UIView

@property (nonatomic) NSString* uid;
@property (nonatomic, assign) NSInteger mode;
@property (nonatomic, assign) NSInteger mirrorMode;
@property (strong, nonatomic) TRTCCloud *rtcEngine;
@property (nonatomic) NSString* subUid;

@end

NS_ASSUME_NONNULL_END
