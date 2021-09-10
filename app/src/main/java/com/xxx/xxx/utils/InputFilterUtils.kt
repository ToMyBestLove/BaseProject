package com.xxx.xxx.utils

import android.app.Activity
import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author zed
 * @date 10/26/20 9:30 AM
 * <p>
 */
object InputFilterUtils {

    @JvmField
    val NULL_FILTER = InputFilter { source: CharSequence, _: Int, _: Int, _: Spanned?, _: Int, _: Int ->
        //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
        return@InputFilter if (" ".contentEquals(source)) {
            ""
        } else {
            null
        }
    }

    //显示键盘
    fun openSoftInput(view: View) {
        val imm = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    //强制隐藏键盘
    fun closeSoftInput(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.peekDecorView().windowToken, 0)
    }

}