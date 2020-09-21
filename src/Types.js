/**
 * 视频分辨率
 * http://doc.qcloudtrtc.com/group__TRTCCloudDef__android.html#aa3b72c532f3ffdf64c6aacab26be5f87
 */
export const VideoResolutions = {
    "TRTC_VIDEO_RESOLUTION_120_120": 1,
    "TRTC_VIDEO_RESOLUTION_160_160": 3,
    "TRTC_VIDEO_RESOLUTION_270_270": 5,
    "TRTC_VIDEO_RESOLUTION_480_480": 7,
    "TRTC_VIDEO_RESOLUTION_160_120": 50,
    "TRTC_VIDEO_RESOLUTION_240_180": 52,
    "TRTC_VIDEO_RESOLUTION_280_210": 54,
    "TRTC_VIDEO_RESOLUTION_320_240": 56,
    "TRTC_VIDEO_RESOLUTION_400_300": 58,
    "TRTC_VIDEO_RESOLUTION_480_360": 60,
    "TRTC_VIDEO_RESOLUTION_640_480": 62,
    "TRTC_VIDEO_RESOLUTION_960_720": 64,
    "TRTC_VIDEO_RESOLUTION_160_90": 100,
    "TRTC_VIDEO_RESOLUTION_256_144": 102,
    "TRTC_VIDEO_RESOLUTION_320_180": 104,
    "TRTC_VIDEO_RESOLUTION_480_270": 106,
    "TRTC_VIDEO_RESOLUTION_640_360": 108,
    "TRTC_VIDEO_RESOLUTION_960_540": 110,
    "TRTC_VIDEO_RESOLUTION_1280_720": 112,
    "TRTC_VIDEO_RESOLUTION_1920_1080": 114,
}
/**
 * 分辨率模式
 */
export const VideoResolutionModes = {
    "TRTC_VIDEO_RESOLUTION_MODE_LANDSCAPE": 0,
    "TRTC_VIDEO_RESOLUTION_MODE_PORTRAIT": 1,
}

/**
 * 应用场景
 * http://doc.qcloudtrtc.com/group__TRTCCloudDef__android.html#a2ba88700d4e8f364866c328ad3fedc2a
 */
export const Scenes = {
    "TRTC_APP_SCENE_VIDEOCALL": 0,
    "TRTC_APP_SCENE_LIVE": 1,
    "TRTC_APP_SCENE_AUDIOCALL": 2,
    "TRTC_APP_SCENE_VOICE_CHATROOM": 3,
}

/**
 * 角色
 * TRTCCloudDef#TRTCRoleAnchor 主播，可以上行视频和音频，一个房间里最多支持50个主播同时上行音视频。
 * TRTCCloudDef#TRTCRoleAudience 观众，只能观看，不能上行视频和音频，一个房间里的观众人数没有上限。
 */
export const Roles = {
    "TRTCRoleAnchor": 20,
    "TRTCRoleAudience": 21
}
