import React, { Component, useEffect, useRef } from "react";
import TRTCEngine, {
    TRTCLocalView, Scene, Role, RenderMode,
    VideoRotation, TRTCRemoteView, VideoResolution, LogLevel
} from 'rn-trtc';
import {StyleSheet, View, TouchableOpacity, Text, SafeAreaView} from "react-native";

const sdkAppId = 0;
const userId = '';
const roomId = 0;
const userSig = ''

const Example = () => {
    const [roomState, setRoomState] = React.useState({
        linkMic: false,
        remoteUsers: [],
    });
    const engineRef = useRef(null);

    const setState = state => {
        setRoomState(oldState => Object.assign({}, oldState, state));
    }

    useEffect(()=>{
        TRTCEngine.setLogLevel(LogLevel.TRTCLogLevelInfo); // 设置日志级别
        // init
        const engine = TRTCEngine.create({
            videoResolution: VideoResolution.TRTC_VIDEO_RESOLUTION_120_120,
            videoFps: 15,
            videoBitrate: 1200,
        });
        engine.setBeautyStyle(1); // 设置美颜风格
        engine.setBeautyLevel(5); // 设置美颜级别
        engine.setWhitenessLevel(1); // 设置美白级别

        engineRef.current = engine;
        // error event
        engine.addListener("onError", (args) => {
            console.error(args)
        });
        engine.addListener("onUserVideoAvailable", args => {
            console.log(args)
            const {userId, available} = args;
            if(available){
                setRoomState(oldState=>{
                    const index = oldState.remoteUsers.indexOf(userId);
                    if(index > -1)return oldState;
                    return Object.assign({}, oldState, {remoteUsers: [...oldState.remoteUsers, userId]})
                })
            }else{
                setRoomState(oldState=>{
                    const index = oldState.remoteUsers.indexOf(userId);
                    if(index === -1)return oldState;
                    oldState.remoteUsers.splice(index, 1);
                    return Object.assign({}, oldState);

                })
            }
        })
        // enter room event
        engine.addListener("onEnterRoom", (args) => {
            const {result} = args;
            if(result > 0){
                console.log(`进房成功，总计耗时${result}ms`)
            }else{
                console.error(`进房失败，错误码${result}`)
            }
        });
        // enter room
        engine.enterRoom({
            sdkAppId: sdkAppId,  // number类型
            userId: userId,   // string 类型
            roomId: roomId, // number 类型
            userSig: userSig,
            role: Role.TRTCRoleAudience, // 加入房间的角色
        }, Scene.TRTC_APP_SCENE_LIVE)
        return()=>{
            // 清空数据
            setState({
                linkMic: false,
                remoteUsers: [],
            })
            // destroy
            engine.destroy();
        }
    },[])

    const switchCamera = () => {
        engineRef.current.switchCamera();
    };
    const onLinkMic = () => {
        // 下麦
        if(roomState.linkMic){
            engineRef.current.switchRole(Role.TRTCRoleAudience);
            engineRef.current.stopLocalAudio();
        }else{ // 上麦
            engineRef.current.switchRole(Role.TRTCRoleAnchor);
            engineRef.current.startLocalAudio();
        }
        setState({linkMic: !roomState.linkMic}); // 设置显示本地预览视频
    };
    return(
        <SafeAreaView style={styles.container}>
            <View style={styles.video}>
                {roomState.linkMic && <TRTCLocalView renderMode={RenderMode.TRTC_VIDEO_RENDER_MODE_FILL}
                                                     style={styles.videoContainer}/>}
                {roomState.remoteUsers.map((item, index)=><TRTCRemoteView uid={item}
                                                                          key={`trtc-${index}`}
                                                                          renderMode={RenderMode.TRTC_VIDEO_RENDER_MODE_FILL}
                                                                          style={styles.videoContainer}/>)}
            </View>
            <View style={styles.buttonHolder}>
                <TouchableOpacity
                    onPress={switchCamera}
                    style={styles.button}>
                    <Text style={styles.buttonText}>切换摄像头</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    onPress={onLinkMic}
                    style={styles.button}>
                    <Text style={styles.buttonText}>{roomState.linkMic ? '下麦' : '上麦'}</Text>
                </TouchableOpacity>
            </View>
        </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    video: {
        flexDirection: 'row',
        flexWrap:'wrap'
    },
    buttonHolder: {
        alignItems: 'center',
        flex: 1,
        flexDirection: 'row',
    },
    videoContainer:{
        height: 150,
        width: 150,
        margin: 10,
        overflow: 'hidden',
        backgroundColor:'#000'
    },
    button: {
        paddingHorizontal: 20,
        paddingVertical: 10,
        backgroundColor: '#0093E9',
        borderRadius: 25,
    },
    buttonText: {
        color: '#fff',
    },
});

export default Example;
