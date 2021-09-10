package com.xxx.xxx.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.xxx.xxx.utils.LogUtils
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.Proxy
import java.net.ProxySelector
import java.net.SocketAddress
import java.net.URI
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author zed
 * @date 10/26/20 2:17 PM
 * <p>
 */
object ServerManager {

    val Api: ServerApi by lazy {
        Retrofit
            .Builder()
            .baseUrl(ApiHelp.MAIN_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(ServerApi::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val timeout = 10000L
        val builder = OkHttpClient.Builder().apply {
            readTimeout(timeout, TimeUnit.MILLISECONDS)
            connectTimeout(timeout, TimeUnit.MILLISECONDS)
            writeTimeout(timeout, TimeUnit.MILLISECONDS)
            addInterceptor(INTERCEPTOR)
            //修复退到背景后，过段时间回页面做请求会timeout
            connectionPool(ConnectionPool(8, timeout * 2, TimeUnit.MILLISECONDS))
            //防抓包 防代理
            proxy(Proxy.NO_PROXY)
            proxySelector(object : ProxySelector() {
                override fun select(uri: URI?): MutableList<Proxy> {
                    return Collections.singletonList(Proxy.NO_PROXY)
                }

                override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {
                    ioe?.printStackTrace()
                }
            })
        }
        return builder.build()
    }

    private val INTERCEPTOR: Interceptor = Interceptor { chain ->
        val request = chain.request()
        val url = request.url().toString()
        LogUtils.debug("url = $url")
        chain.proceed(request)
    }
}