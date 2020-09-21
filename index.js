export * from './src/Types';
import React, { Component } from "react";
import TRTCVideoView from './src/TRTCVideoView';
import {View, NativeModules, Platform, requireNativeComponent, NativeEventEmitter} from "react-native";
const { RTCTencent } = NativeModules;
const TRTCEventEmitter = new NativeEventEmitter(RTCTencent);
export {
    TRTCVideoView,
    TRTCEventEmitter
};


export default RTCTencent;

