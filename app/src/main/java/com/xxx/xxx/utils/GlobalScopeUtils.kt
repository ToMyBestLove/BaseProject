package com.xxx.xxx.utils

import kotlinx.coroutines.*

/**
 * @author zed
 * @date 11/5/20 10:47 AM
 * <p>
 *    GlobalScope.launch 开启子线程
 *    GlobalScope.launch(Dispatchers.Default) 开启子线程
 *    GlobalScope.launch(Dispatchers.Unconfined) 跟随开启线程
 *
 *    viewModelScope.launch  跟随开启线程
 *    viewModelScope.launch(Dispatchers.Default) 开启子线程
 *    viewModelScope.launch(Dispatchers.Unconfined) 跟随开启线程
 */
object GlobalScopeUtils {

    fun <T> startAsync(
        scope: CoroutineScope = GlobalScope,
        delay: Long = 0,
        block: suspend () -> T
    ) {
        scope.launch(Dispatchers.Default) {
            delay(delay)
            block()
        }
    }

}