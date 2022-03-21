package com.dvt.chucknorrisjokes.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Immutable Parcelable model class for a Favorite Joke. Also serves as room entity for our database
 *
 * @param categories joke category
 * @param createdAt time joke was created
 * @param iconUrl joke icon
 * @param id id of the joke
 * @param url url
 * @param value  the joke itself
 */

@Entity(tableName = "jokes_table")
@Parcelize
data class Joke(
    val categories: List<String>,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("icon_url") val iconUrl: String,
    @PrimaryKey val id: String,
    @SerializedName("updated_at") val updatedAt: String,
    val url: String,
    val value: String
) : Parcelable {

    companion object {
        @JvmStatic
        @BindingAdapter("iconUrl")
        fun displayIcon(imageViewIcon: ImageView, iconUrl: String) {
            Glide.with(imageViewIcon.context).load(iconUrl).into(imageViewIcon)
        }
    }

}

