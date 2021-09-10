package com.xxx.xxx

import androidx.lifecycle.MutableLiveData
import com.xxx.xxx.base.BaseViewModel
import kotlinx.coroutines.delay

/**
 * @author zed
 * @date 9/10/21 5:49 下午
 * <p>
 */
class SplashViewModel : BaseViewModel() {

    val liveDataToMain by lazy { MutableLiveData<String?>() }

    override fun onActivityCreate() {
        super.onActivityCreate()

        startAsync {
            delay(1000)
            liveDataToMain.postValue(null)
        }
    }
}