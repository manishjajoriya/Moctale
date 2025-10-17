package com.manishjajoriya.moctale.domain.model.schedule

import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("next_page") val nextPage: Int,
    @SerializedName("previous_page") val previousPage: Int?,
    val count: Long,
    val data: List<Data>,
)

data class Data(
    val day: String,
    val month: String,
    val year: String,
    val timestamp: Long,
    @SerializedName("day_of_week") val dayOfWeek: String,
    val time: String?,
    val code: String,
    @SerializedName("event_type") val eventType: String,
    @SerializedName("release_type") val releaseType: String,
    val content: Content,
    val live: String?,
    @SerializedName("to_be_confirmed") val toBeConfirmed: Boolean,
    @SerializedName("event_interested_count") val eventInterestedCount: Int,
)

data class Content(
    val name: String,
    @SerializedName("is_show") val isShow: Boolean,
    @SerializedName("is_anime") val isAnime: Boolean,
    val image: String,
    val year: Int,
    val slug: String,
    @SerializedName("count_total_review") val countTotalReview: Int,
    @SerializedName("percent_positive_review") val percentPositiveReview: Double,
)
