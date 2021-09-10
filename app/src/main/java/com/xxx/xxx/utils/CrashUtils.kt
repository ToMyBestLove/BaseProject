package com.xxx.xxx.utils

import java.util.*

/**
 * @author zed
 * @date 10/23/20 4:05 PM
 * <p>
 */
object CrashUtils {

    private const val TAG = "CrashUtils"

    private const val APP_STATUS_KILL = -1 //应用放在后台被强杀了

    private const val APP_STATUS_NORMAL = 1 //APP正常态

    private var mLocaleLanguage: String? = null

    private var mAppStatus = APP_STATUS_KILL //APP状态 初始值为没启动 不在前台状态

    fun isNormalStatus(): Boolean {
        return mAppStatus == APP_STATUS_NORMAL
    }

    fun setNormalStatus() {
        //给app赋值正常状态 当app被后台杀死时 此值会变为APP_STATUS_KILL
        mAppStatus = APP_STATUS_NORMAL
    }

    fun isChangeLanguage(): Boolean {
        val currentLanguage = Locale.getDefault().language
        if (mLocaleLanguage == null) {
            mLocaleLanguage = currentLanguage
            return false
        }

        val change = mLocaleLanguage != currentLanguage
        if (change) {
            mLocaleLanguage = currentLanguage
        }
        return change
    }

}