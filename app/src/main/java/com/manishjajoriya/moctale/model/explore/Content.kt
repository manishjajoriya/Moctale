package com.manishjajoriya.moctale.model.explore

import com.google.gson.annotations.SerializedName

data class Content(
    val caption: String?,
    val image: String,
    @SerializedName("is_show") val isShow: Boolean,
    val name: String,
    val slug: String,
    val year: Int
)