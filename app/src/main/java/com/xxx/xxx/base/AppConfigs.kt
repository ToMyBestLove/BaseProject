package com.xxx.xxx.base

import java.util.regex.Pattern

/**
 * @author zed
 * @date 10/23/20 3:08 PM
 * <p>
 */
object AppConfigs {

    /* 正则表达式*/
    val TEL_PATTERN by lazy { Pattern.compile("^[1][3456789]\\d{9}$") }
    val CH_PATTERN by lazy { Pattern.compile("[\u4e00-\u9fa5]") }
    val NUMBER_PATTERN by lazy { Pattern.compile("[0-9]*") }

}