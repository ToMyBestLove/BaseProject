package com.xxx.xxx.api

/**
 * @author zed
 * @date 10/26/20 3:11 PM
 * <p>
 */
object ApiHelp {
    /**
     * The Api MAIN_URL.
     */
    const val MAIN_URL = "https://xxx.xxx.com"

    //server code
    const val SERVER_CODE_SUCCESS = 0 //正常

    const val SERVER_CODE_ERROR = 1 //操作失败，具体见返回Msg信息

    //http code
    const val ERROR_SUCCESS = 200//网络请求成功

    const val ERROR_UNKNOWN = -10000//网络请求失败

    //json string
    const val JSON_MSG = "msg"

    const val JSON_CODE = "code"

    //server api
    const val XXX = "/xxx"
}