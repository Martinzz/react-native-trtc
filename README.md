# react-native-trtc

[![](https://img.shields.io/npm/v/rn-trtc.svg?style=flat-square)][npm]
[![](https://img.shields.io/npm/l/rn-trtc.svg?style=flat-square)][npm]
[![](https://img.shields.io/npm/dm/rn-trtc.svg?style=flat-square)][npm]

[npm]: https://www.npmjs.com/package/rn-trtc

基于腾讯实时音视频（TRTC）最新SDK React Native 组件基本封装完成，同时支持IOS和Android

没有实现全部API的封装，有很少部分不常用功能尚未实现

后续有时间会继续完善

## 安装(React Native >= 0.60.0)
```shell script
npm i --save rn-trtc
```
打开ios目录运行
```shell script
pod install
```
## 使用

TRTCLocalView 显示本视频预览
TRTCRemoteView 显示远程视频流

需要在加入房间后render

* [Example](https://github.com/Martinzz/react-native-trtc/blob/master/example.js)
* [腾讯实时音视频服务](https://cloud.tencent.com/product/trtc)
```js
import React, { Component, useEffect, useRef } from "react";
import TRTCEngine, {
    TRTCLocalView, 
    Scene, 
    Role, 
    VideoRotation, 
    TRTCRemoteView, 
    LogLevel,
    VideoResolution
} from 'rn-trtc';

TRTCEngine.setLogLevel(LogLevel.TRTCLogLevelNone); // 设置日志级别

// 初始化，参数参照官方文档
const engine = TRTCEngine.create({
    videoResolution: VideoResolution.TRTC_VIDEO_RESOLUTION_640_360,
    videoFps: 15,
    videoBitrate: 1200,
});

engine.setBeautyStyle(1); // 设置美颜风格
engine.setBeautyLevel(5); // 设置美颜级别
engine.setWhitenessLevel(1); // 设置美白级别
        
// 绑定事件，事件名参照官方文档
engine.addListener("onError", (args) => {
    console.error(args)
});

engine.addListener("onEnterRoom", (args) => {
    const {result} = args;
    if(result > 0){
        console.log(`进房成功，总计耗时${result}ms`)
    }else{
        console.error(`进房失败，错误码${result}`)
    }
});
// 进入房间
engine.enterRoom({
    sdkAppId: sdkAppId, //appId: number
    userId: userId,  // userId: string
    roomId: roomId, // roomId: nubmer
    userSig: userSig, // 用户签名参照官方文档生成
    role: Role.TRTCRoleAudience, // 加入房间的角色
}, Scene.TRTC_APP_SCENE_LIVE)

// API
engine.switchRole(Role.TRTCRoleAnchor);
engine.startLocalAudio();

// 加入房间成功后显示本地视频预览
{joinSucceed && <TRTCLocalView style={styles.videoContainer}/>}

// 有远程视频加入显示
{remoteUsers.map((uid, index)=><TRTCRemoteView uid={uid}
                                    key={`trtc-${index}`}
                                    style={styles.videoContainer}/>)}

```

## 官方API文档
* [UserSig 相关问题](https://cloud.tencent.com/document/product/647/17275)
* [TRTC API](https://cloud.tencent.com/document/product/647/32258)





