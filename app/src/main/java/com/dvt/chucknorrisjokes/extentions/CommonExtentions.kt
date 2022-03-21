package com.dvt.chucknorrisjokes.extentions

import android.content.Context
import android.widget.Toast

/**
 * Extension functions.
 */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}