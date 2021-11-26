package com.example.coffefu.utils

import android.content.Context
import android.widget.Toast

class ToastAlert(private var context: Context, private var message: String, private var time: Int) {
    fun show() {
        Toast.makeText(context, message, time).show();
    }
}
