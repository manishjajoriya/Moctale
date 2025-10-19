package com.manishjajoriya.moctale.domain.model.search.user

import com.google.gson.annotations.SerializedName

data class SearchUser(
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("next_page") val nextPage: Int?,
    @SerializedName("previous_page") val previousPage: Int?,
    val count: Long,
    val data: List<Data>,
)

data class Data(
    val username: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("photo_thumbnail") val photoThumbnail: String?,
    @SerializedName("is_verified") val isVerified: Boolean,
)
