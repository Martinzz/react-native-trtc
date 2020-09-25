import React, {memo} from "react";
import TRTCVideoView from './TRTCView';
import PropTypes from 'prop-types';
const TRTCView = memo((props)=>{
    return(
        <TRTCVideoView {...props} uid=""/>
    )
})
TRTCView.propTypes = {
    renderMode: PropTypes.number,
    mirrorMode: PropTypes.number,
    style: PropTypes.any,
}

export default TRTCView;
