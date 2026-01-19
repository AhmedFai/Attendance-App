package com.example.attendance.util

import android.content.Context

sealed class UiText {

    data class Dynamic(val value: String) : UiText()

    data class StringRes(
        @param:androidx.annotation.StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is Dynamic -> value
            is StringRes ->
                context.getString(resId, *args.toTypedArray())
        }
    }

}