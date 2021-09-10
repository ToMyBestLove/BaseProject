package com.xxx.xxx.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.xxx.xxx.utils.ReflectedUtils

/**
 * @author zed
 * @date 9/27/20 2:31 PM
 * <p>
 */
class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {

        /**
         * 创建ViewHolder实例
         */
        fun createViewHolder(
            context: Context,
            parent: ViewGroup,
            @LayoutRes layoutId: Int
        ): BaseViewHolder {
            return BaseViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false))
        }

    }

    /**
     * 获取ViewBinding
     */
    inline fun <reified T : ViewBinding> getViewBind(): T {
        return ReflectedUtils.createViewBinding(itemView)
    }
}