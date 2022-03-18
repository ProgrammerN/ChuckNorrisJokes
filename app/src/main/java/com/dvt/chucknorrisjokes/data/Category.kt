package com.dvt.chucknorrisjokes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "categories_table")
@Parcelize
class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String
) : Parcelable