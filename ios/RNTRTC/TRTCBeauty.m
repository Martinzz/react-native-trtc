//
//  TRTCBeauty.m
//  Pods
//
//  Created by martin on 2020/9/30.
//

#import "TRTCBeauty.h"

@implementation TRTCBeauty

RCT_EXPORT_MODULE();

-(TXBeautyManager *)getBeautyManager{
    return [[TRTCCloud sharedInstance] getBeautyManager];
}
RCT_EXPORT_METHOD(setBeautyStyle:(int)beautyStyle){
    [[self getBeautyManager] setBeautyStyle: beautyStyle];
}
RCT_EXPORT_METHOD(setBeautyLevel:(float)beautyLevel){
    [[self getBeautyManager] setBeautyLevel: beautyLevel];
}
RCT_EXPORT_METHOD(setWhitenessLevel:(float)whitenessLevel){
    [[self getBeautyManager] setWhitenessLevel: whitenessLevel];
}
RCT_EXPORT_METHOD(setRuddyLevel:(float)ruddyLevel){
    [[self getBeautyManager] setRuddyLevel: ruddyLevel];
}
@end
