package com.manishjajoriya.moctale.domain.model.content.info

import com.google.gson.annotations.SerializedName

data class ContentInfo(
    @SerializedName("is_watched") val isWatched: Boolean,
    @SerializedName("watched_seasons") val watchedSeasons: Boolean?,
    @SerializedName("watched_date") val watchedDate: String,
    @SerializedName("is_in_watchlist") val isInWatchlist: Boolean,
)
