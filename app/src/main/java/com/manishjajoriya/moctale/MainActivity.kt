package com.manishjajoriya.moctale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.manishjajoriya.moctale.navgraph.NavGraph
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.components.BottomBar
import com.manishjajoriya.moctale.presentation.components.TopBar
import com.manishjajoriya.moctale.ui.theme.MoctaleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MoctaleTheme {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(navController) },
            bottomBar = {
              BottomBar(
                  navController,
                  onClick = { clickText ->
                    when (clickText) {
                      "explore" -> navController.navigate(Routes.ExploreScreen.route)
                      "schedule" -> navController.navigate(Routes.ScheduleScreen.route)
                      "browse" -> {}
                      "clubs" -> {}
                      "profile" -> {}
                    }
                  },
              )
            },
        ) { innerPadding ->
          NavGraph(paddingValues = innerPadding, navController = navController)
        }
      }
    }
  }
}
