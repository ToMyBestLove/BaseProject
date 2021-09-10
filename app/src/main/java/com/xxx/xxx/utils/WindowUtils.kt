package com.xxx.xxx.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * @author zed
 * @date 10/26/20 10:34 AM
 * <p>
 */
object WindowUtils {
    /**
     * 改变Window亮度
     *
     * @param brightness 0.001f~1.0f
     */
    fun setWindowBrightness(window: Window, brightness: Float) {
        val lp = window.attributes
        lp.screenBrightness = brightness
        window.attributes = lp
    }

    /**
     * 恢复window亮度
     */
    fun recoverWindowBrightness(window: Window) {
        val lp = window.attributes
        lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        window.attributes = lp
    }

    /**
     * 获得系统亮度
     */
    fun getSystemBrightness(context: Context): Int {
        var systemBrightness = 0
        try {
            systemBrightness = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
        }
        return systemBrightness
    }

    /**
     * 获取屏幕高
     *
     * @param context
     * @return
     */
    fun getDefaultDisplayHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay?.getSize(point)
        return point.y
    }

    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    fun getDefaultDisplayWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay?.getSize(point)
        return point.x
    }

    /**
     * status height
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    /**
     * dp转px
     *
     * @param context 上下文对象
     * @param dpVal   dp值
     * @return px值
     */
    fun dp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, Resources.getSystem().displayMetrics).toInt()
    }

    /**
     * sp转px
     *
     * @param context 上下文对象
     * @param spVal   sp值
     * @return px值
     */
    fun sp2px(spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, Resources.getSystem().displayMetrics).toInt()
    }

    /**
     * px转dp
     *
     * @param context 上下文对象
     * @param pxVal   px值
     * @return dp值
     */
    fun px2dp(pxVal: Float): Float {
        return pxVal / Resources.getSystem().displayMetrics.density
    }

    /**
     * px转sp
     *
     * @param context 上下文对象
     * @param pxVal   px值
     * @return sp值
     */
    fun px2sp(pxVal: Float): Float {
        return pxVal / Resources.getSystem().displayMetrics.scaledDensity
    }

    /**
     * 沉浸式
     * 1、隐藏虚拟按键
     * 2、布局设置成全屏
     * 3、actionbar显示 颜色透明
     *
     * @param blackStatus true is black and false is white
     */
    fun windowFullScreen(activity: Activity, blackStatus: Boolean) {

        //布局全屏
//		int flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        var flag = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        //设置状态栏图标亮色
        if (blackStatus) {
            flag = flag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //黑色
        }

        //设置特殊机型的状态栏图标、文字颜色
        val manufacturer = Build.MANUFACTURER
        if (isXiaoMi(manufacturer)) {
            setMiuiStatusBar(activity, blackStatus)
        } else if (isMeiZu(manufacturer)) {
            setFlymeStatusBar(activity, blackStatus)
        }

        //设置虚拟按键图标亮色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            flag = flag or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        //设置flag
        activity.window.decorView.systemUiVisibility = flag
        //设置状态栏背景颜色
        activity.window.statusBarColor = Color.TRANSPARENT
        //设置虚拟键盘背景颜色
        activity.window.navigationBarColor = Color.TRANSPARENT
    }

    /**
     * 设置小米机型状态栏颜色
     */
    private fun setMiuiStatusBar(activity: Activity, blackStatus: Boolean) {
        val window = activity.window
        val clazz: Class<*> = window.javaClass
        try {
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            if (blackStatus) {
                //状态栏透明且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
            } else {
                //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag)
            }
        } catch (e: Exception) {
        }
    }

    /**
     * 设置魅族机型状态栏颜色
     */
    private fun setFlymeStatusBar(activity: Activity, blackStatus: Boolean) {
        try {
            val lp = activity.window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (blackStatus) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            activity.window.attributes = lp
        } catch (e: Exception) {
        }
    }

    private fun isXiaoMi(manufacturer: String): Boolean {
        return "xiaomi".equals(manufacturer, ignoreCase = true)
    }

    private fun isHuaWei(manufacturer: String): Boolean {
        return "huawei".equals(manufacturer, ignoreCase = true)
    }

    private fun isOppo(manufacturer: String): Boolean {
        return "oppo".equals(manufacturer, ignoreCase = true)
    }

    private fun isVivo(manufacturer: String): Boolean {
        return "vivo".equals(manufacturer, ignoreCase = true)
    }

    private fun isMeiZu(manufacturer: String): Boolean {
        return "meizu".equals(manufacturer, ignoreCase = true)
    }
}