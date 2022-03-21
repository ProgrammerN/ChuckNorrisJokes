package com.dvt.chucknorrisjokes.extentions

import android.content.Context
import android.content.Intent
import com.dvt.chucknorrisjokes.model.Joke

/**
 * Common Extension functions.
 */

fun Context.share(joke: Joke) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, joke.value)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}