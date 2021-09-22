package com.xxx.xxx.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.xxx.xxx.R
import com.xxx.xxx.api.ApiError
import com.xxx.xxx.dialog.AllDialog
import com.xxx.xxx.utils.CrashUtils
import com.xxx.xxx.utils.ReflectedUtils
import java.lang.reflect.ParameterizedType

/**
 * @author zed
 * @date 9/16/20 4:01 PM
 * <p>
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(), BaseModel {

    protected val TAG by lazy { javaClass.simpleName }

    @Suppress("UNCHECKED_CAST")
    val mViewBinding by lazy {
        ReflectedUtils.createViewBinding(
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>,
            layoutInflater
        )
    }

    @Suppress("UNCHECKED_CAST")
    val mViewModel by lazy {
        ReflectedUtils.createViewModel(
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>,
            this
        )
    }

    //view model初始化
    abstract fun initViewModel()

    //ui 初始化
    abstract fun initView()

    fun onCreateOnly(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //判断应用在后台是否被杀死 重启应用
        if (!CrashUtils.isNormalStatus()) {
            val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
            if (i != null) {
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }
            val pid = Process.myPid()
            Process.killProcess(pid)
            return
        }

        setContentView(mViewBinding.root)

        //add base ui observer
        initBaseLiveData()

        //init ViewModel
        initViewModel()

        //initView
        initView()

        //add lifecycle
        lifecycle.addObserver(mViewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        //remove lifecycle
        lifecycle.removeObserver(mViewModel)
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
        if (mNetworkErrorDialog.isShowing) {
            mNetworkErrorDialog.dismiss()
        }
        if (mTokenInvalidDialog.isShowing) {
            mTokenInvalidDialog.dismiss()
        }
    }

    //加载
    private val mLoadingDialog by lazy {
        AllDialog(this)
    }

    //网络错误
    private val mNetworkErrorDialog by lazy {
        AllDialog(this)
    }

    //token失效
    private val mTokenInvalidDialog by lazy {
        AllDialog(this)
    }

    /**
     * initBaseLiveData
     */
    private fun initBaseLiveData() {
        /* base ui */
        mViewModel.uiLoading.observe(this) {
            if (it) {
                if (!mLoadingDialog.isShowing) {
                    mLoadingDialog.showLoading()
                }
            } else {
                mLoadingDialog.dismiss()
            }
        }
        mViewModel.uiSingleDialog.observe(this) {
            AllDialog(this).showSingleDialog(
                content = it.content ?: getString(it.contentId ?: 0),
                single = it.single ?: getString(it.singleId ?: 0),
                onSingleClick = it.onSingleClick
            )
        }
        mViewModel.uiOkCancelDialog.observe(this) {
            AllDialog(this).showOkCancelDialog(
                content = it.content ?: getString(it.contentId ?: 0),
                left = it.left ?: getString(it.leftId ?: 0),
                right = it.right ?: getString(it.rightId ?: 0),
                onLeftClick = it.onLeftClick,
                onRightClick = it.onRightClick,
            )
        }
        mViewModel.uiToast.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        mViewModel.uiShowNetworkError.observe(this) {
            mLoadingDialog.dismiss()
            if (mNetworkErrorDialog.isShowing) {
                return@observe
            }
            mNetworkErrorDialog.showNetErrorDialog(
                getString(R.string.tips_network_error),
                getString(R.string.ok)
            )
        }
        /* api error */
        mViewModel.apiLiveData.apiError.observe(this) {
            onApiError(it)
        }
        /* http errpr */
        mViewModel.apiLiveData.httpError.observe(this) {
            onHttpError()
        }
    }

    /**
     * 请求成功 code错误回调
     */
    override fun onApiError(apiError: ApiError) {
        //关闭加载
        mViewModel.uiShowLoading(false)
        //显示字串
        mViewModel.uiShowToast(apiError.msg)
    }

    /**
     * 网络错误回调
     */
    override fun onHttpError() {
        //关闭加载
        mViewModel.uiShowLoading(false)
        //显示弹窗
        mViewModel.uiShowNetworkError()
    }

}