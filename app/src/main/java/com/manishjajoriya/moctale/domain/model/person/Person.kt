package com.manishjajoriya.moctale.domain.model.person

import com.google.gson.annotations.SerializedName

data class Person(
    val banner: String?,
    val bio: String,
    @SerializedName("birth_city") val birthCity: String?,
    @SerializedName("birth_country") val birthCountry: String?,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("birth_state") val birthState: String?,
    @SerializedName("death_date") val deathDate: String?,
    val image: String,
    val name: String,
)

data class PersonContent(
    val count: Int,
    @SerializedName("current_page") val currentPage: Int,
    val `data`: List<Data>,
    @SerializedName("next_page") val nextPage: Any,
    @SerializedName("previous_page") val previousPage: Any,
    @SerializedName("total_pages") val totalPages: Int,
)

data class Data(
    val character: String,
    val content: Content,
    @SerializedName("role_list") val roleList: List<String>,
)

data class Content(
    val banner: String,
    val image: String?,
    @SerializedName("is_show") val isShow: Boolean,
    val name: String,
    val slug: String,
    val year: Int,
)
