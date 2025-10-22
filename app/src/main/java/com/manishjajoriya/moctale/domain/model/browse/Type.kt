package com.manishjajoriya.moctale.domain.model.browse

enum class Type(val value: String?, val displayName: String) {
  ALL(null, "All"),
  MOVIE("MOVIE", "Movies"),
  SERIES("SHOW", "Series"),
}