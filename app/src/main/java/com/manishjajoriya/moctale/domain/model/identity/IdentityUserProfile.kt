package com.manishjajoriya.moctale.domain.model.identity

import com.google.gson.annotations.SerializedName

data class IdentityUserProfile(
    val success: Boolean,
    val username: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("photo_original") val photoOriginal: String?,
    @SerializedName("photo_thumbnail") val photoThumbnail: String?,
    @SerializedName("date_of_birth") val dateOfBirth: String,
    @SerializedName("instagram_handle") val instagramHandle: String?,
    @SerializedName("x_handle") val xHandle: String?,
    @SerializedName("youtube_handle") val youtubeHandle: String?,
)
