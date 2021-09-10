package com.xxx.xxx.utils

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author zed
 * @date 10/10/20 5:52 PM
 * <p>
 */
object DateUtils {

    const val TAG = "DateUtils"

    private val simpleDateFormat by lazy { SimpleDateFormat("", Locale.getDefault()) }

    /**
     * 这是一个相对于currentTimeMillis的差值
     *
     * 在用户手动更改时区后，使用这个差值来计算当前本地时区的正确时间
     * note：server目前只会检测此时间是否小于当前时间，如果大于则判断通过
     */
    private val VALUE_TIME by lazy {
        System.currentTimeMillis() - System.nanoTime() / 1000000
    }

    /**
     * 初始化
     */
    fun initValueTime() {
        getCurrentTime()
    }

    /**
     * 获取修正过的本地时区正确时间
     */
    fun getCurrentTime(): Long {
//        DateUtil.getFixedSkewedTimeMillis()
        return System.nanoTime() / 1000000 + VALUE_TIME
    }

    /**
     * 日期格式化
     * @param format 格式
     * @param time 时间ms
     * @return 格式化后的日期
     */
    fun format(format: String, time: Long): String {
        simpleDateFormat.applyPattern(format)
        return simpleDateFormat.format(time)
    }

}