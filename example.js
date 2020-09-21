import React, { Component, useState, useEffect } from "react";
import TRTCEngine, {TRTCVideoView, TRTCEventEmitter} from 'rn-trtc';
import { View} from "react-native";

const Example = () => {
    const [join, setJoin] = useState(false);
    useEffect(()=>{
        TRTCEngine.init({});
        TRTCEventEmitter.addListener(`joinSuccess`, (args) => {
            setJoin(true);
        });
        TRTCEngine.enterRoom({
            appId: 0,
            userId: "",
            roomId: 0,
            userSig: ''
        })
    },[])
    return(
        <View style={{flex:1}}>
            {join && <TRTCVideoView style={{height: 200}} showLocalVideo={true}/>}
        </View>
    )
}

export default Example;
