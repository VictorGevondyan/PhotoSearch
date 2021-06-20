package com.android.photosearch.util

/**
 * Class with data for live data which will be handled only once
 */
class LDEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getIfNotHandled(): T? {
        return if (hasBeenHandled) { null } else {
            hasBeenHandled = true
            content
        }
    }

    fun getContent(): T = content

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LDEvent<*>) return false

        if (content != other.content) return false

        return true
    }

    override fun hashCode(): Int {
        val result = content?.hashCode() ?: 0
        return result
    }


}
