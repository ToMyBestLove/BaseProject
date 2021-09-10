package com.xxx.xxx.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author zed
 * @date 9/18/20 9:39 AM
 * <p>
 *     生命周期管理
 */
interface ViewModelLifecycle : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner, event: Lifecycle.Event)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onActivityCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onActivityStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onActivityResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onActivityPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onActivityStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onActivityDestroy()

}