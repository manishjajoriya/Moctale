package com.manishjajoriya.moctale.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manishjajoriya.moctale.presentation.exploreScreen.ExploreScreen
import com.manishjajoriya.moctale.presentation.exploreScreen.ExploreViewModel

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
  val exploreViewModel: ExploreViewModel = hiltViewModel()
  val startDestination = Routes.ExploreScreen.route

  NavHost(navController = navController, startDestination = startDestination) {
    composable(Routes.ExploreScreen.route) {
      ExploreScreen(modifier = modifier, exploreViewModel = exploreViewModel)
    }
  }
}
