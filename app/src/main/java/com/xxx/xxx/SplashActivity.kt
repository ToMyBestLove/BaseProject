package com.xxx.xxx

import android.content.Intent
import android.os.Bundle
import com.xxx.xxx.base.BaseActivity
import com.xxx.xxx.databinding.ActivitySplashBinding
import com.xxx.xxx.utils.ActivityEngine
import com.xxx.xxx.utils.CrashUtils

/**
 * @author zed
 * @date 9/10/21 5:50 下午
 * <p>
 */
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //防止某些机型点击桌面图标后会重启app
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            //must be call onCreate
            onCreateOnly(savedInstanceState)
            finish()
            return
        }
        //将App状态置为normal，用以判断是否被后台杀死
        CrashUtils.setNormalStatus()
        super.onCreate(savedInstanceState)
    }

    override fun initViewModel() {

        mViewModel.liveDataToMain.observe(this) {
            ActivityEngine.startMainActivity(this)
        }

    }

    override fun initView() {

    }
}