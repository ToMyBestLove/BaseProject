package com.xxx.xxx.base

/**
 * @author Zed
 * @date 2020-01-08 10:01
 *
 *
 */
object AppObserver {

    private val OBSERVERS = ArrayList<Observer>()

    fun addObserver(observer: Observer) {
        if (!OBSERVERS.contains(observer)) {
            OBSERVERS.add(observer)
        }
    }

    fun removeObserver(observer: Observer?) {
        OBSERVERS.remove(observer)
    }

    /**
     * xxx
     */
    fun notifyXxxChanged() {
        for (observer in OBSERVERS) {
            observer.onXxxChanged()
        }
    }

    interface Observer {

        fun onXxxChanged() {}

    }

}