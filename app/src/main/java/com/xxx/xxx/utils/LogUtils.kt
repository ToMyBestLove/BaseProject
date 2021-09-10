package com.xxx.xxx.utils

import android.util.Log

/**
 * @author zed
 * @date 10/26/20 9:47 AM
 * <p>
 */
object LogUtils {
    private const val DEBUG_TAG = "zed_test"

    private const val IS_DEBUG = true

    fun i(tag: String, text: String) {
        if (!IS_DEBUG) {
            return
        }
        var text_ = text
        val segmentSize = 3 * 1024
        val length = text_.length.toLong()
        if (length > segmentSize) {
            while (text_.length > segmentSize) {
                val logContent = text_.substring(0, segmentSize)
                text_ = text_.replace(logContent, "")
                Log.i(tag, logContent)
            }
        }
        Log.i(tag, text_)
    }

    fun w(tag: String, text: String) {
        if (!IS_DEBUG) {
            return
        }
        var text_ = text
        val segmentSize = 3 * 1024
        val length = text_.length.toLong()
        if (length > segmentSize) {
            while (text_.length > segmentSize) {
                val logContent = text_.substring(0, segmentSize)
                text_ = text_.replace(logContent, "")
                Log.w(tag, logContent)
            }
        }
        Log.w(tag, text_)
    }

    fun e(tag: String, text: String) {
        if (!IS_DEBUG) {
            return
        }
        var text_ = text
        val segmentSize = 3 * 1024
        val length = text_.length.toLong()
        if (length > segmentSize) {
            while (text_.length > segmentSize) {
                val logContent = text_.substring(0, segmentSize)
                text_ = text_.replace(logContent, "")
                Log.e(tag, logContent)
            }
        }
        Log.e(tag, text_)
    }

    fun debug(text: String) {
        if (!IS_DEBUG) {
            return
        }
        var text_ = text
        val segmentSize = 3 * 1024
        val length = text_.length.toLong()
        if (length > segmentSize) {
            while (text_.length > segmentSize) {
                val logContent = text_.substring(0, segmentSize)
                text_ = text_.replace(logContent, "")
                Log.i(DEBUG_TAG, logContent)
            }
        }
        Log.i(DEBUG_TAG, text_)
    }
}