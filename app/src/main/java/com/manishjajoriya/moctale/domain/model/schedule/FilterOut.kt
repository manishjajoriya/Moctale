package com.manishjajoriya.moctale.domain.model.schedule

enum class TimeFilter(val value: String, val displayName: String) {
  RELEASED("released", "Released"),
  TODAY("today", "Today"),
  UPCOMING("upcoming", "Upcoming"),
}

enum class Category() {
  ALL,
  MOVIE,
  SERIES,
}

enum class MovieCategory(val value: String, val displayName: String) {
  IN_THEATRES("NEW_MOVIE_THEATRE", "In Theatres"),
  ON_OTT("NEW_MOVIE_OTT", "On OTT"),
}

enum class SeriesCategory(val value: String, val displayName: String) {
  NEW_SHOW("NEW_SHOW", "New Show"),
  NEW_SEASON("NEW_SEASON", "New Season"),
}
