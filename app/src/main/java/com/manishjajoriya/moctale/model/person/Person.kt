package com.manishjajoriya.moctale.model.person

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
