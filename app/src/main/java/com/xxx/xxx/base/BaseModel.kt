package com.xxx.xxx.base

import com.xxx.xxx.api.ApiError

/**
 * @author zed
 * @date 9/18/20 1:38 PM
 * <p>常用行为
 */
interface BaseModel {

    /**
     * 请求成功 code错误回调
     */
    fun onApiError(apiError: ApiError)

    /**
     * 网络错误回调
     */
    fun onHttpError()

}