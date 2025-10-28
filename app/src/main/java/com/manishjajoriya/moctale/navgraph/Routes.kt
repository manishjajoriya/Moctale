package com.manishjajoriya.moctale.navgraph

sealed class Routes(val route: String) {

  object AuthScreen : Routes(route = "auth")

  object ExploreScreen : Routes(route = "explore")

  object ContentScreen : Routes(route = "content")

  object PersonScreen : Routes(route = "person")

  object ScheduleScreen : Routes(route = "schedule")

  object SearchScreen : Routes(route = "search")

  object BrowseScreen : Routes(route = "browse")

  object CategoryScreen : Routes(route = "category")

  object GenreScreen : Routes(route = "genre")

  object CountryScreen : Routes(route = "country")

  object LanguageScreen : Routes(route = "language")

  object ComingSoonScreen : Routes(route = "coming-soon")

  object MenuScreen : Routes(route = "menu")
}
