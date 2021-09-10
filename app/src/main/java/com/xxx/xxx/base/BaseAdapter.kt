package com.xxx.xxx.base

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author zed
 * @date 9/27/20 2:36 PM
 * <p>
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseAdapter<T>(
    private val context: Context,
    private val dates: List<T>,
    @LayoutRes private val layoutId: Int,
    @LayoutRes private val headId: Int = 0,
    @LayoutRes private val footId: Int = 0
) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        const val HEADER = 1 //头部局
        const val FOOT = 2 //尾部局
        const val ITEM = 3 //一般item
    }

    /**
     * 是否长按监听
     */
    var canOnLongClick = false

    var canOnClick = true

    /**
     * item view 高度
     */
    var itemHeight = 0

    /**
     * item view 宽度
     */
    var itemWidth = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val viewHolder: BaseViewHolder = when (viewType) {
            HEADER -> {
                BaseViewHolder.createViewHolder(context, parent, headId)
            }
            FOOT -> {
                BaseViewHolder.createViewHolder(context, parent, footId)
            }
            ITEM -> {
                BaseViewHolder.createViewHolder(context, parent, layoutId)
            }
            else -> {
                throw Exception("No Find BaseViewHolder!")
            }
        }
        val layoutParams = viewHolder.itemView.layoutParams
        if (itemWidth != 0) {
            layoutParams?.width = itemWidth
        }
        if (itemHeight != 0) {
            layoutParams?.height = itemHeight
        }
        viewHolder.itemView.layoutParams = layoutParams
        return viewHolder
    }

    override fun getItemCount(): Int {
        val size = dates.size
        if (headId != 0 && footId != 0) {
            return size + 2
        }
        if (headId != 0) {
            return size + 1
        }
        if (footId != 0) {
            return size + 1
        }
        return size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (canOnLongClick) {
            holder.itemView.setOnLongClickListener {
                this.onLongClick(position)
                true
            }
        }
        if (canOnClick) {
            holder.itemView.setOnClickListener { this.onClick(position) }
        }
        onBindHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        if (headId != 0 && position == 0) {
            return HEADER
        }
        if (footId != 0) {
            if (headId != 0 && position == dates.size + 1) {
                return FOOT
            } else if (headId == 0 && position == dates.size) {
                return FOOT
            }
        }
        return ITEM
    }

    /**
     * item点击
     */
    open fun onClick(position: Int) {}

    /**
     * item长按监听
     */
    open fun onLongClick(position: Int) {}

    abstract fun onBindHolder(holder: BaseViewHolder, position: Int)
}