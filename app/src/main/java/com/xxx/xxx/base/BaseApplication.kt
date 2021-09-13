package com.xxx.xxx.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.Stack

/**
 * @author zed
 * @date 10/26/20 8:02 PM
 * <p>
 */
class BaseApplication : Application() {

    companion object {

        lateinit var application: Application
        //页面堆栈集合
        private val ACTIVITY_STACK = Stack<Activity>()
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        /* 页面生命周期注册 */
        registerActivityLifecycleCallbacks(mLifecycleCallbacks)

    }

    private val mLifecycleCallbacks: ActivityLifecycleCallbacks =
            object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    ACTIVITY_STACK.add(0, activity)
                }

                override fun onActivityStarted(activity: Activity) {}

                override fun onActivityResumed(activity: Activity) {}

                override fun onActivityPaused(activity: Activity) {}

                override fun onActivityStopped(activity: Activity) {}

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

                override fun onActivityDestroyed(activity: Activity) {
                    ACTIVITY_STACK.remove(activity)
                }
            }
}