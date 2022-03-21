package com.dvt.chucknorrisjokes.extentions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.dvt.chucknorrisjokes.model.Joke

/**
 * Common Extension functions.
 */

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.share(joke: Joke) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, joke.value)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}