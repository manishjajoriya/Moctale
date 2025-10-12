package com.manishjajoriya.moctale.model.explore

import com.google.gson.annotations.SerializedName

data class ExploreItem(
    @SerializedName("content_list") val contentList: List<Content>,
    val description: String,
    val icon: String,
    val name: String
)