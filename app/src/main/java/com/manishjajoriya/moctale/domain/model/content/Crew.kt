package com.manishjajoriya.moctale.domain.model.content

import com.google.gson.annotations.SerializedName

data class Crew(
    val image: String?,
    val name: String,
    @SerializedName("role_list") val roleList: List<String>,
    val slug: String,
)
