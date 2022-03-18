package com.dvt.chucknorrisjokes.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Immutable Parcelable model class for a Joke Category. Also serves as room entity for our database
 *
 * @param category category name
 * @param id id of the category
 */
@Entity(tableName = "categories_table")
@Parcelize
class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String
) : Parcelable