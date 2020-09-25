//
//  RNTRTC.h
//  RNTRTC
//
//  Created by martin on 2020/9/22.
//  Copyright © 2020 com.yk.martin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import "TRTCConst.h"

@interface RNTRTC : RCTEventEmitter<RCTBridgeModule, TRTCCloudDelegate>

@property (strong, nonatomic) TRTCCloud *rtcEngine;

@end
