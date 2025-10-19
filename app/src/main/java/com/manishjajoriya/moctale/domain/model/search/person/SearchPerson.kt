package com.manishjajoriya.moctale.domain.model.search.person

import com.google.gson.annotations.SerializedName

data class SearchPerson(
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("next_page") val nextPage: Int?,
    @SerializedName("previous_page") val previousPage: Int?,
    val count: Long,
    val data: List<Data>,
)

data class Data(
    val name: String,
    val image: String?,
    val slug: String,
)
