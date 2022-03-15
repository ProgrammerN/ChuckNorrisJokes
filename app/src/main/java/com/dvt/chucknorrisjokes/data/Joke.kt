package com.dvt.chucknorrisjokes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "jokes")
@Parcelize
class Joke(
    @SerializedName("icon_url") val iconUrl: String,
    @PrimaryKey val id: String,
    val url: String,
    val value: String
) : Parcelable

