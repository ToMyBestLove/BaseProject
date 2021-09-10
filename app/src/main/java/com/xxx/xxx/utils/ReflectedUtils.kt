package com.xxx.xxx.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding

/**
 * @author zed
 * @date 9/28/20 9:56 AM
 * <p> 反射帮助类  利用反射创建泛型实例
 *  Person::class// kClass
 *  person.javaClass.kotlin             // kClass
 *  (Person::class as Any).javaClass    // javaClass
 *  Person::class.java                  // javaClass
 */
@Suppress("UNCHECKED_CAST")
object ReflectedUtils {

    /**
     * 创建ViewBind实例
     */
    fun <T : ViewBinding> createViewBinding(
        viewBindClass: Class<T>,
        inflater: LayoutInflater,
        parent: ViewGroup? = null,
        attachToRoot: Boolean = false
    ): T {
        if (parent != null) {
            val method = viewBindClass.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
            return method.invoke(null, inflater, parent, attachToRoot) as T
        }
        val method = viewBindClass.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, inflater) as T
    }

    /**
     * 创建ViewBind实例
     */
    inline fun <reified T : ViewBinding> createViewBinding(view: View): T {
        val method = T::class.java.getMethod("bind", View::class.java)
        return method.invoke(null, view) as T
    }

    /**
     * 创建ViewModel实例
     */
    fun <T : ViewModel> createViewModel(modelClass: Class<T>, owner: ViewModelStoreOwner): T {
        return ViewModelProvider(owner).get(modelClass)
    }
}