package com.xxx.xxx.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.xxx.xxx.api.ApiLiveData
import com.xxx.xxx.utils.GlobalScopeUtils

/**
 * @author zed
 * @date 9/16/20 4:32 PM
 * <p>
 * ViewModel拥有远比Activity和Fragment还要长的生命周期，
 * 所以ViewModel中最好不要持有Activity或者Fragment的引用，否则很容易引起内存泄漏
 */
open class BaseViewModel : ViewModel(), ViewModelLifecycle,
    BaseView {

    protected val TAG by lazy { javaClass.simpleName }

    /* --------- 网络请求 --------- */
    val apiLiveData by lazy { ApiLiveData(viewModelScope) }

    /* --------- lifecycle --------- */

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    override fun onActivityCreate() {
//        LogUtils.debug(TAG + " ------ onActivityCreate")
    }

    override fun onActivityStart() {

    }

    override fun onActivityResume() {

    }

    override fun onActivityPause() {

    }

    override fun onActivityStop() {

    }

    override fun onActivityDestroy() {
//        LogUtils.debug(TAG + " ------ onActivityDestroy")
    }

    /* --------- base thread behavior --------- */

    override fun <T> startAsync(delay: Long, block: suspend () -> T) {
        GlobalScopeUtils.startAsync(viewModelScope, delay, block)
    }

    /* --------- base ui behavior --------- */

    val uiLoading by lazy { MutableLiveData<Boolean>() }
    val uiSingleDialog by lazy { MutableLiveData<Bundle>() }
    val uiOkCancelDialog by lazy { MutableLiveData<Bundle>() }
    val uiToast by lazy { MutableLiveData<String>() }
    val uiShowNetworkError by lazy { MutableLiveData<String?>() }

    override fun uiShowLoading(show: Boolean) {
        uiLoading.postValue(show)
    }

    override fun uiShowSingleDialog(content: String, single: String, onSingleClick: (() -> Unit)?) {
        //todo uiShowSingleDialog
    }

    override fun uiShowOkCancelDialog(
        content: String,
        left: String,
        right: String,
        onLeftClick: (() -> Unit)?,
        onRightClick: (() -> Unit)?
    ) {
        //todo uiShowOkCancelDialog
    }

    override fun uiShowToast(text: String) {
        uiToast.postValue(text)
    }

    override fun uiShowToast(@StringRes textId: Int) {
        uiToast.postValue(BaseApplication.application.getString(textId))
    }

    override fun uiShowNetworkError() {
        uiShowNetworkError.postValue(null)
    }
}