package com.xxx.xxx.utils

import com.tencent.mmkv.MMKV

/**
 * @author zed
 * @date 10/26/20 9:52 AM
 * <p>
 */
object SPUtils {

    private const val TAG = "SPUtil"

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key   保存数据的key值
     * @param any   保存数据的类型
     */
    operator fun set(key: String, any: Any) {
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)
        when (any) {
            is String -> {
                kv.encode(key, any)
            }
            is Int -> {
                kv.encode(key, any)
            }
            is Boolean -> {
                kv.encode(key, any)
            }
            is Float -> {
                kv.encode(key, any)
            }
            is Long -> {
                kv.encode(key, any)
            }
            else -> {
                kv.encode(key, any.toString())
            }
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           保存数据的key值
     * @param any 保存数据的类型
     * @return 得到保存数据
     */
    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String, any: T): T {
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)
        when (any) {
            is String -> {
                return kv.decodeString(key, any) as T
            }
            is Int -> {
                return kv.decodeInt(key, any) as T
            }
            is Boolean -> {
                return kv.decodeBool(key, any) as T
            }
            is Float -> {
                return kv.decodeFloat(key, any) as T
            }
            is Long -> {
                return kv.decodeLong(key, any) as T
            }
            else -> {
                throw Exception("[get] not define this == $any")
            }
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key 保存数据的key值
     */
    fun remove(key: String) {
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)
        kv.removeValueForKey(key)
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)
        kv.clearAll()
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key 保存数据的key值
     * @return key是否已经存在
     */
    operator fun contains(key: String?): Boolean {
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)
        return kv.contains(key)
    }

}