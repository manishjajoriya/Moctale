package com.manishjajoriya.moctale.model.content

import com.google.gson.annotations.SerializedName

data class Video(val caption: String, @SerializedName("video_id") val videoId: String)
