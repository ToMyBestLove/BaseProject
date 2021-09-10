package com.xxx.xxx.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.xxx.xxx.api.ApiError
import com.xxx.xxx.utils.ReflectedUtils
import java.lang.reflect.ParameterizedType

/**
 * @author zed
 * @date 9/16/20 4:01 PM
 * <p>
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(), BaseModel {

    protected val TAG by lazy { javaClass.simpleName }

    @Suppress("UNCHECKED_CAST")
    val mViewBinding by lazy {
        ReflectedUtils.createViewBinding(
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>,
            LayoutInflater.from(context)
        )
    }

    @Suppress("UNCHECKED_CAST")
    val mViewModel by lazy {
        ReflectedUtils.createViewModel(
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>,
            this
        )
    }

    abstract fun initViewModel()

    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mViewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
    }

    /**
     * initBaseLiveData
     */
    private fun initBaseLiveData() {
        mViewModel.uiLoading.observe(viewLifecycleOwner) {
            (activity as? BaseActivity<*, *>)?.mViewModel?.uiLoading?.postValue(it)
        }
        mViewModel.uiSingleDialog.observe(viewLifecycleOwner) {
            (activity as? BaseActivity<*, *>)?.mViewModel?.uiSingleDialog?.postValue(it)
        }
        mViewModel.uiOkCancelDialog.observe(viewLifecycleOwner) {
            (activity as? BaseActivity<*, *>)?.mViewModel?.uiOkCancelDialog?.postValue(it)
        }
        mViewModel.uiToast.observe(viewLifecycleOwner) {
            (activity as? BaseActivity<*, *>)?.mViewModel?.uiToast?.postValue(it)
        }
        mViewModel.uiShowNetworkError.observe(viewLifecycleOwner) {
            (activity as? BaseActivity<*, *>)?.mViewModel?.uiShowNetworkError?.postValue(it)
        }
        /* api error */
        mViewModel.apiLiveData.apiError.observe(viewLifecycleOwner) {
            onApiError(it)
        }
        /* http error */
        mViewModel.apiLiveData.httpError.observe(viewLifecycleOwner) {
            onHttpError()
        }
    }

    /**
     * 请求成功 code错误回调
     */
    override fun onApiError(apiError: ApiError) {
        (activity as? BaseActivity<*, *>)?.onApiError(apiError)
    }

    /**
     * 网络错误回调
     */
    override fun onHttpError() {
        (activity as? BaseActivity<*, *>)?.onHttpError()
    }
}