package com.manishjajoriya.moctale.model.person.content

import com.google.gson.annotations.SerializedName

data class PersonContent(
    val count: Int,
    @SerializedName("current_page") val currentPage: Int,
    val `data`: List<Data>,
    @SerializedName("next_page") val nextPage: Any,
    @SerializedName("previous_page") val previousPage: Any,
    @SerializedName("total_pages") val totalPages: Int,
)
