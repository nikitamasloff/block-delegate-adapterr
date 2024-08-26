package com.guavapay.crypto.common.base.recycler

import androidx.recyclerview.widget.RecyclerView

inline fun <reified A : RecyclerView.Adapter<*>> RecyclerView.getTypedAdapter(): A {
    val adapter = this.adapter
    if (adapter == null) {
        error("Adapter for RecyclerView has not been set")
    }
    return try {
        adapter as A
    } catch (e: ClassCastException) {
        error("Wrong adapter type was expected")
    }
}

fun View.applyIfTagNotSet(
    tag: String = "default_tag",
    block: () -> Unit
) {
    if (this.tag == tag) {
        return
    } else {
        block.invoke()
    }
}
