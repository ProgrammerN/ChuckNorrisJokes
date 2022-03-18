package com.dvt.chucknorrisjokes.util

import com.dvt.chucknorrisjokes.R

/**
 * Return a random color
 */

object RandomColor {
    fun randomBackgroundColor(): Int =
        listOf(
            R.color.random_color_1,
            R.color.random_color_2,
            R.color.random_color_3,
            R.color.random_color_4,
            R.color.random_color_5,
            R.color.random_color_6,
            R.color.random_color_7
        ).random()
}

