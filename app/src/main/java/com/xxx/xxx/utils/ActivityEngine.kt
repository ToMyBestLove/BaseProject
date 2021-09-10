package com.xxx.xxx.utils

import android.content.Context
import android.content.Intent
import com.xxx.xxx.MainActivity

/**
 * @author zed
 * @date 10/30/20 11:29 AM
 * <p>
 */
object ActivityEngine {

    /* ------ main ------ */
    fun startMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

}