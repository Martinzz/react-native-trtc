//
//  RNTRTC.h
//  RNTRTC
//
//  Created by martin on 2020/9/22.
//  Copyright © 2020 com.yk.martin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import "TXLiteAVSDK_TRTC/TRTCCloud.h"

@interface RNTRTC : NSObject<RCTBridgeModule, TRTCCloudDelegate>

@property (strong, nonatomic) TRTCCloud *rtcEngine;

@end
