package com.manishjajoriya.moctale.navgraph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manishjajoriya.moctale.presentation.contentScreen.ContentScreen
import com.manishjajoriya.moctale.presentation.contentScreen.ContentViewModel
import com.manishjajoriya.moctale.presentation.exploreScreen.ExploreScreen
import com.manishjajoriya.moctale.presentation.exploreScreen.ExploreViewModel

@Composable
fun NavGraph(paddingValues: PaddingValues, navController: NavHostController) {
  val exploreViewModel: ExploreViewModel = hiltViewModel()
  val contentViewModel: ContentViewModel = hiltViewModel()
//  val startDestination = Routes.ContentScreen.route + "/kantara-chapter-1-2025"
    val startDestination = Routes.ExploreScreen.route

  NavHost(navController = navController, startDestination = startDestination) {
    composable(Routes.ExploreScreen.route) {
      ExploreScreen(
          paddingValues = paddingValues,
          exploreViewModel = exploreViewModel,
          navController = navController,
      )
    }

    composable(Routes.ContentScreen.route + "/{slug}") {
      val slug = it.arguments?.getString("slug") ?: "kantara-chapter-1-2025"
      ContentScreen(paddingValues = paddingValues, slug = slug, viewModel = contentViewModel)
    }
  }
}
