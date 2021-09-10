package com.xxx.xxx.api

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author zed
 * @date 10/26/20 2:22 PM
 * <p>
 */
interface ServerApi {

    /**
     * xxx
     */
    @FormUrlEncoded
    @POST(ApiHelp.XXX)
    fun xxxAsync(
        @Field("XXX") xxx: String = "xxx"
    ): Deferred<Response<ResponseBody>>

}