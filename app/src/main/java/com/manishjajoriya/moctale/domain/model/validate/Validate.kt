package com.manishjajoriya.moctale.domain.model.validate

import com.google.gson.annotations.SerializedName

data class Validate(@SerializedName("unseen_notifications_count") val unseenNotificationsCount: Int)
