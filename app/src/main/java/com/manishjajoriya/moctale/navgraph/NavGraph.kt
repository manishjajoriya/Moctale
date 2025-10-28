package com.manishjajoriya.moctale.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manishjajoriya.moctale.MainViewModel
import com.manishjajoriya.moctale.presentation.authScreen.AuthScreen
import com.manishjajoriya.moctale.presentation.browseScreen.BrowseScreen
import com.manishjajoriya.moctale.presentation.browseScreen.BrowseViewModel
import com.manishjajoriya.moctale.presentation.browseScreen.category.CategoriesScreen
import com.manishjajoriya.moctale.presentation.browseScreen.country.CountryScreen
import com.manishjajoriya.moctale.presentation.browseScreen.genre.GenreScreen
import com.manishjajoriya.moctale.presentation.browseScreen.language.LanguageScreen
import com.manishjajoriya.moctale.presentation.comingSoonScreen.ComingSoonScree
import com.manishjajoriya.moctale.presentation.contentScreen.ContentScreen
import com.manishjajoriya.moctale.presentation.contentScreen.ContentViewModel
import com.manishjajoriya.moctale.presentation.exploreScreen.ExploreScreen
import com.manishjajoriya.moctale.presentation.exploreScreen.ExploreViewModel
import com.manishjajoriya.moctale.presentation.menuScreen.MenuScreen
import com.manishjajoriya.moctale.presentation.personScreen.PersonScreen
import com.manishjajoriya.moctale.presentation.personScreen.PersonViewModel
import com.manishjajoriya.moctale.presentation.scheduleScreen.ScheduleScreen
import com.manishjajoriya.moctale.presentation.scheduleScreen.ScheduleViewModel
import com.manishjajoriya.moctale.presentation.searchScreen.SearchScreen
import com.manishjajoriya.moctale.presentation.searchScreen.SearchViewModel

@Composable
fun NavGraph(
    paddingValues: PaddingValues,
    startDestination: String,
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {
  val exploreViewModel: ExploreViewModel = hiltViewModel()
  val contentViewModel: ContentViewModel = hiltViewModel()
  val personViewModel: PersonViewModel = hiltViewModel()
  val scheduleViewModel: ScheduleViewModel = hiltViewModel()
  val searchViewModel: SearchViewModel = hiltViewModel()
  val browseViewModel: BrowseViewModel = hiltViewModel()

  NavHost(navController = navController, startDestination = startDestination) {
    composable(Routes.AuthScreen.route) {
      AuthScreen(
          viewModel = mainViewModel,
          navController = navController,
      )
    }

    composable(Routes.ExploreScreen.route) {
      ExploreScreen(
          paddingValues = paddingValues,
          viewModel = exploreViewModel,
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
      PersonScreen(
          paddingValues = paddingValues,
          name = name,
          viewModel = personViewModel,
          navController = navController,
      )
    }

    composable(Routes.ScheduleScreen.route) {
      ScheduleScreen(
          paddingValues = paddingValues,
          viewModel = scheduleViewModel,
          navController = navController,
      )
    }

    composable(
        Routes.SearchScreen.route,
        enterTransition = {
          slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(750)) +
              fadeIn(tween(750))
        },
        popExitTransition = {
          slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(500)) +
              fadeOut(tween(500))
        },
    ) {
      SearchScreen(
          paddingValues = paddingValues,
          viewModel = searchViewModel,
          navController = navController,
      )
    }

    composable(
        Routes.BrowseScreen.route + "/{browse-slug}" + "/{category-slug}" + "/{category-name}"
    ) {
      val browseSlug = it.arguments?.getString("browse-slug") ?: "category"
      val categorySlug = it.arguments?.getString("category-slug")
      val categoryName = it.arguments?.getString("category-name") ?: "Action"
      BrowseScreen(
          browseSlug = browseSlug,
          categorySlug = categorySlug,
          categoryName = categoryName,
          paddingValues = paddingValues,
          viewModel = browseViewModel,
          navController = navController,
      )
    }

    composable(Routes.CategoryScreen.route) {
      CategoriesScreen(
          paddingValues = paddingValues,
          viewModel = browseViewModel,
          navController = navController,
      )
    }

    composable(Routes.GenreScreen.route) {
      GenreScreen(
          paddingValues = paddingValues,
          viewModel = browseViewModel,
          navController = navController,
      )
    }

    composable(Routes.CountryScreen.route) {
      CountryScreen(
          paddingValues = paddingValues,
          viewModel = browseViewModel,
          navController = navController,
      )
    }

    composable(Routes.LanguageScreen.route) {
      LanguageScreen(
          paddingValues = paddingValues,
          viewModel = browseViewModel,
          navController = navController,
      )
    }

    composable(Routes.ComingSoonScreen.route) { ComingSoonScree(paddingValues = paddingValues) }

    composable(
        Routes.MenuScreen.route,
        enterTransition = {
          slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(750)) +
              fadeIn(tween(750))
        },
        popExitTransition = {
          slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(500)) +
              fadeOut(tween(500))
        },
    ) {
      MenuScreen(paddingValues = paddingValues, mainViewModel)
    }
  }
}
