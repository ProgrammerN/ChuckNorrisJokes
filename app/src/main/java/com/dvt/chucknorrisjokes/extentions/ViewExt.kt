package com.dvt.chucknorrisjokes.extentions

import androidx.appcompat.widget.SearchView

/**
 * Transforms SearchView to an extension function ignoring onQueryTextSubmit.
 */

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}