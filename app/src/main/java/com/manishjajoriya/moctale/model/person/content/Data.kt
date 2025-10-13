package com.manishjajoriya.moctale.model.person.content

import com.google.gson.annotations.SerializedName

data class Data(
    val character: String,
    val content: Content,
    @SerializedName("role_list") val roleList: List<String>,
)
