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
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (id != other.id) return false
        if (category != other.category) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + category.hashCode()
        return result
    }


}