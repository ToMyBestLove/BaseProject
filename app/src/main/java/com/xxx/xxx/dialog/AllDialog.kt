package com.xxx.xxx.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import com.xxx.xxx.databinding.DialogLoadingBinding
import com.xxx.xxx.databinding.DialogNetErrorBinding
import com.xxx.xxx.databinding.DialogOkcanelBinding
import com.xxx.xxx.databinding.DialogSingleBinding

/**
 * @author Zed
 * @date 2020-01-14 11:43
 * <p>
 * 类别		回收机制			用途					生存时间
 * 强引用	从不回收			对象状态				JVM停止运行时
 * 软引用	内存不足时进行回收	缓存					内存不足
 * 弱引用	对象不被引用时回收	缓存					GC运行后
 * 虚引用	对象被回收时		管理控制精确内存稳定性	unknown
 */
@SuppressLint("SetTextI18n")
class AllDialog(context: Context) : Dialog(context) {

    init {
        setCancelable(true)
        setCanceledOnTouchOutside(false)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (isShowing) {
            dismiss()
        }
    }

    //loading
    fun showLoading() {
        val viewBinding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        show()
    }

    //网络error
    fun showNetErrorDialog(
        content: String,
        single: String,
        onSingleClick: (() -> Unit)? = null
    ) {
        val viewBinding = DialogNetErrorBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.tvContent.text = content
        viewBinding.tvSingle.text = single
        viewBinding.tvSingle.setOnClickListener {
            dismiss()
            onSingleClick?.invoke()
        }
        show()
    }

    //单选
    fun showSingleDialog(
        content: String,
        single: String,
        onSingleClick: (() -> Unit)? = null
    ) {
        val viewBinding = DialogSingleBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnSingle.text = single
        viewBinding.tvContent.text = content
        viewBinding.btnSingle.setOnClickListener {
            dismiss()
            onSingleClick?.invoke()
        }
        show()
    }

    //确定取消
    fun showOkCancelDialog(
        content: String, left: String, right: String,
        onLeftClick: (() -> Unit)? = null,
        onRightClick: (() -> Unit)? = null
    ) {
        val viewBinding = DialogOkcanelBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.tvContent.text = content
        viewBinding.btnLeft.text = left
        viewBinding.btnLeft.setOnClickListener {
            dismiss()
            onLeftClick?.invoke()
        }
        viewBinding.btnRight.text = right
        viewBinding.btnRight.setOnClickListener {
            dismiss()
            onRightClick?.invoke()
        }
        show()
    }
}