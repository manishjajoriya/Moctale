package com.manishjajoriya.moctale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.manishjajoriya.moctale.navgraph.NavGraph
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.components.BottomBar
import com.manishjajoriya.moctale.presentation.components.BrowseSheet
import com.manishjajoriya.moctale.presentation.components.TopBar
import com.manishjajoriya.moctale.ui.theme.MoctaleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MoctaleTheme {
        val navController = rememberNavController()
        var isShowBrowseSheet by remember { mutableStateOf(false) }
        val mainViewModel: MainViewModel = hiltViewModel()
        val isLogin by mainViewModel.isLogin.collectAsState()
        splashScreen.setKeepOnScreenCondition { isLogin == null }
        if (isLogin == null) return@MoctaleTheme

        val startDestination =
            if (isLogin == true) Routes.ExploreScreen.route else Routes.AuthScreen.route
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
              if (startDestination != Routes.AuthScreen.route) {
                TopBar(navController)
              }
            },
            bottomBar = {
              if (startDestination != Routes.AuthScreen.route) {
                BottomBar(
                    navController,
                    onClick = { clickText ->
                      when (clickText) {
                        "explore" -> navController.navigate(Routes.ExploreScreen.route)
                        "schedule" -> navController.navigate(Routes.ScheduleScreen.route)
                        "browse" -> isShowBrowseSheet = true
                        "clubs" -> navController.navigate(Routes.ComingSoonScreen.route)
                        "profile" -> navController.navigate(Routes.ComingSoonScreen.route)
                      }
                    },
                )
              }
            },
        ) { innerPadding ->
          if (isShowBrowseSheet)
              BrowseSheet(navController = navController) { isShowBrowseSheet = false }

          NavGraph(
              paddingValues = innerPadding,
              navController = navController,
              startDestination = startDestination,
              mainViewModel = mainViewModel,
          )
        }
      }
    }
  }
}
