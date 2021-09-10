package com.xxx.xxx.base

import androidx.annotation.StringRes

/**
 * @author zed
 * @date 9/18/20 1:38 PM
 * <p>常用ui
 */
interface BaseView {

    /**
     * start async
     */
    fun <T> startAsync(delay: Long = 0, block: suspend () -> T)

    /**
     * Show loading.
     *
     * @param show show or dismiss
     */
    fun uiShowLoading(show: Boolean)

    /**
     * Show single dialog.
     */
    fun uiShowSingleDialog(
            content: String,
            single: String,
            onSingleClick: (() -> Unit)? = null
    )

    /**
     * Show single dialog.
     */
    fun uiShowOkCancelDialog(
        content: String, left: String, right: String,
        onLeftClick: (() -> Unit)? = null,
        onRightClick: (() -> Unit)? = null
    )

    /**
     * Show toast.
     */
    fun uiShowToast(text: String)

    /**
     * Show toast by id
     */
    fun uiShowToast(@StringRes textId: Int)

    /**
     * showNetworkError
     */
    fun uiShowNetworkError()

}