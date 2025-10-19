package com.manishjajoriya.moctale.domain.model.search

enum class SearchType(val value : String, val displayName : String) {
  Content("", "Content"),
  Person("person", "Cast & Crew"),
  User("user", "Users")
}
