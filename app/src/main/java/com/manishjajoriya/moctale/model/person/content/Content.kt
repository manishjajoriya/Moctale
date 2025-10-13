package com.manishjajoriya.moctale.model.person.content

import com.google.gson.annotations.SerializedName

data class Content(
    val banner: String,
    val image: String?,
    @SerializedName("is_show") val isShow: Boolean,
    val name: String,
    val slug: String,
    val year: Int,
)
