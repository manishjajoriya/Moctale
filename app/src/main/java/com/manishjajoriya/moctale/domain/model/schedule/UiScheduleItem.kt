package com.manishjajoriya.moctale.domain.model.schedule

sealed class UiScheduleItem {
  data class Header(val day: String, val date: String, val month: String) : UiScheduleItem()

  data class Content(val data: Data) : UiScheduleItem()
}
