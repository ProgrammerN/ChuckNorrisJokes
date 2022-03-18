package com.dvt.chucknorrisjokes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorites_table")
@Parcelize
data class JokeFavorite(
    val categories: List<String>,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("icon_url") val iconUrl: String,
    @PrimaryKey val id: String,
    @SerializedName("updated_at") val updatedAt: String,
    val url: String,
    val value: String
) : Parcelable

