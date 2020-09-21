import React, { Component, useState, useEffect, useRef } from "react";
import TRTCEngine, {
    TRTCView, Scenes,
    VideoResolutionModes,
    VideoResolutions, Roles
} from 'rn-trtc';
import {StyleSheet, View, TouchableOpacity, Text} from "react-native";

const appId = 0;
const userId = '';
const roomId = 0;
const userSig = ''

const Example = () => {
    const [join, setJoin] = useState(false);
    const [role, setRole] = useState(Roles.TRTCRoleAudience);

    useEffect(()=>{
        // init
        const engine = TRTCEngine.create({
            videoResolutionMode: VideoResolutionModes.TRTC_VIDEO_RESOLUTION_MODE_LANDSCAPE,
        });
        // error event
        engine.addListener("onError", (args) => {
            console.log(args)
        });
        engine.addListener("onUserVideoAvailable", args => {
            console.log(args)
            const {userId, available} = args;
        })
        // enter room event
        engine.addListener("onEnterRoom", (args) => {
            const {result} = args;
            if(result > 0){
                console.log(`进房成功，总计耗时${result}ms`)
                setJoin(true);
            }else{
                console.log(`进房失败，错误码${result}`)
            }
        });
        // enter room
        engine.enterRoom({
            appId: appId,
            userId: userId,
            roomId: roomId,
            userSig: userSig
        }, Scenes.TRTC_APP_SCENE_LIVE)
        return()=>{
            // destroy
            engine.destroy();
        }
    },[])

    const switchRole = () => {
        if(role === Roles.TRTCRoleAudience){

        }else{

        }
    };
    return(
        <View style={styles.container}>
            {join && <TRTCView style={styles.videoContainer}/>}
            <View style={styles.buttonHolder}>
                <TouchableOpacity
                    onPress={switchRole}
                    style={styles.button}>
                    <Text style={styles.buttonText}>{role === Roles.TRTCRoleAudience ? 'Audience' : 'Anchor'}</Text>
                </TouchableOpacity>
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    buttonHolder: {
        alignItems: 'center',
        flex: 1,
        flexDirection: 'row',
    },
    videoContainer:{
        height: 200,
        width: 300,
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
