package com.manishjajoriya.moctale.navgraph

sealed class Routes(val route : String) {
  object ExploreScreen : Routes(route = "exploreScreen")
}
