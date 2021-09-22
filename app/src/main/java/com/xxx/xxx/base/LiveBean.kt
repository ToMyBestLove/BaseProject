package com.xxx.xxx.base

/**
 * @author zed
 * @date 9/22/21 5:12 下午
 * <p>
 */
class LiveBean {

    var content: String? = null
    var left: String? = null
    var right: String? = null
    var single: String? = null

    var contentId: Int? = null
    var leftId: Int? = null
    var rightId: Int? = null
    var singleId: Int? = null

    var onLeftClick: (() -> Unit)? = null
    var onRightClick: (() -> Unit)? = null
    var onSingleClick: (() -> Unit)? = null
}