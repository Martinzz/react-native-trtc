import { View, NativeModules, Platform, requireNativeComponent} from "react-native";
import {memo} from "react";
const TRTCVideoView = requireNativeComponent('TRTCVideoView');
import PropTypes from 'prop-types';
const TRTCView = memo((props)=>{

    return(
        <TRTCVideoView {...props}/>
    )
})

TRTCView.propTypes = {
    uid: PropTypes.string, // "0" 代表显示本地视频
    mode: PropTypes.int,
    frontCamera: PropTypes.bool,
    style: View.any,
}
TRTCView.defaultProps = {
    frontCamera: true,
    uid: "0",
}

export default TRTCView;
