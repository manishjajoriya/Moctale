package com.manishjajoriya.moctale.domain.model.explore

import com.google.gson.annotations.SerializedName

data class Content(
  val caption: String?,
  val image: String,
  @SerializedName("is_show") val isShow: Boolean,
  val name: String,
  val slug: String,
  val year: Int
)

data class ExploreItem(
  @SerializedName("content_list") val contentList: List<Content>,
  val description: String,
  val icon: String,
  val name: String
)
