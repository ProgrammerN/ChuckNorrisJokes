package com.dvt.chucknorrisjokes.app

import com.dvt.chucknorrisjokes.BuildConfig


/**
 * Holds constants of database, api etc.
 */

object Constants {

    const val BASE_URL = BuildConfig.API_URL
    const val DATABASE_VERSION = 1
    const val DATABASE_EXPORT_SCHEMA = false
    const val DATABASE_NAME = "jokes_database"

}