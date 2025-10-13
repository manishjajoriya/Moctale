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
import com.manishjajoriya.moctale.presentation.personScreen.PersonScreen
import com.manishjajoriya.moctale.presentation.personScreen.PersonViewModel

@Composable
fun NavGraph(paddingValues: PaddingValues, navController: NavHostController) {
  val exploreViewModel: ExploreViewModel = hiltViewModel()
  val contentViewModel: ContentViewModel = hiltViewModel()
  val personViewModel: PersonViewModel = hiltViewModel()
//    val startDestination = Routes.PersonScreen.route + "/rishab-shetty"
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
      ContentScreen(
          paddingValues = paddingValues,
          slug = slug,
          viewModel = contentViewModel,
          navController = navController,
      )
    }

    composable(Routes.PersonScreen.route + "/{name}") {
      val name = it.arguments?.getString("name") ?: "rishab-shetty"
      PersonScreen(paddingValues = paddingValues, name = name, viewModel = personViewModel)
    }
  }
}
