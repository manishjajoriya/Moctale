package com.manishjajoriya.moctale.domain.model.browse

import com.google.gson.annotations.SerializedName

data class BrowseData(
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("next_page") val nextPage: Int?,
    @SerializedName("previous_page") val previousPage: Int?,
    val count: Int,
    val data: List<Data>,
)

data class Data(
    val name: String,
    val image: String,
    val year: Int,
    @SerializedName("is_show") val isShow: Boolean,
    val slug: String,
    val banner: String,
)
