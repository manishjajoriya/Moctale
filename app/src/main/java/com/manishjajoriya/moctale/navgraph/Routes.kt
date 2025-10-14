package com.manishjajoriya.moctale.navgraph

sealed class Routes(val route: String) {
  object ExploreScreen : Routes(route = "explore")

  object ContentScreen : Routes(route = "content")

  object PersonScreen : Routes(route = "person")

  object ScheduleScreen : Routes(route = "schedule")
}
