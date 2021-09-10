package com.xxx.xxx.api

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xxx.xxx.bean.PostBean
import com.xxx.xxx.utils.LogUtils
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

/**
 * @author zed
 * @date 9/30/20 1:35 PM
 * <p> 所有具体的网络请求
 */
class ApiLiveData(private val viewModelScope: CoroutineScope) {

    companion object {
        private const val TAG = "ApiLiveData"
    }

    //网络错误回调
    val httpError by lazy { MutableLiveData<String?>() }

    //请求成功 code错误回调
    val apiError by lazy { MutableLiveData<ApiError>() }

    /**
     * xxx
     */
    fun xxx(
        httpError: (() -> Unit)? = responseHttpError,
        apiError: ((ApiError) -> Unit)? = responseApiError,
        async: ((PostBean) -> Unit)? = null
    ) {
        apiRequest(
            apiType = ApiType.XXX,
            requestIO = {
                ServerManager.Api.xxxAsync()
            },
            responseIO = {
                Gson().fromJson(it, PostBean::class.java)
            },
            responseUI = async,
            httpError = httpError,
            apiError = apiError
        )
    }

    /**
     * api请求 liveData统一封装回调
     */
    private fun <T> apiRequest(
        apiType: String,
        requestIO: () -> Deferred<Response<ResponseBody>>,
        responseIO: (String) -> T,
        responseUI: ((T) -> Unit)?,
        httpError: (() -> Unit)?,
        apiError: ((ApiError) -> Unit)?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //开始请求
                val response = requestIO().await()
                //获取请求结果的code
                val httpCode = response.code()
                if (httpCode != ApiHelp.ERROR_SUCCESS) {
                    val string = response.errorBody()?.string()
                    val msg = if (string.isNullOrEmpty()) response.message() ?: "" else string
                    val code = response.code()
                    LogUtils.e(TAG, "[${apiType}] code = $code msg = $msg")
                    httpError?.invoke()
                    return@launch
                }
                //数据请求string
                val resultString = printlnSuccessResult(apiType, response)

                //todo 解析数据
                val jsonObject = JSONObject(resultString)
                val serverCode = jsonObject.optInt("code", ApiHelp.SERVER_CODE_ERROR)
                val errorString = jsonObject.optString("msg", "")
                //ui处理
                if (serverCode != ApiHelp.SERVER_CODE_SUCCESS && serverCode != ApiHelp.ERROR_SUCCESS) {
                    LogUtils.e(TAG, "[${apiType}] code = $serverCode msg = $errorString")
                    apiError?.invoke(ApiError(serverCode, errorString))
                    return@launch
                }
                //数据解析 io处理
                val t = responseIO(resultString)
                withContext(Dispatchers.Main) {
                    responseUI?.invoke(t)
                }
            } catch (e: Exception) {
                LogUtils.e(TAG, "[$apiType] code = ${ApiHelp.ERROR_UNKNOWN} msg = $e")
                httpError?.invoke()
            }
        }
    }

    private val responseHttpError: () -> Unit = {
        httpError.postValue(null)
    }

    private val responseApiError: (ApiError) -> Unit = {
        apiError.postValue(it)
    }

    /**
     * 打印请求成功后的结果
     */
    private fun printlnSuccessResult(type: String, response: Response<ResponseBody>): String {
        val string = response.body()?.string() ?: ""
        LogUtils.i(TAG, "[${type}] code = ${response.code()} msg = $string")
        return string
    }

}