package com.manishjajoriya.moctale.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.navgraph.Routes

@Composable
fun TopBar(navController: NavController) {
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route
  val isSearchScreen = currentRoute == Routes.SearchScreen.route
  val isMenuScreen = currentRoute == Routes.MenuScreen.route

  Column(modifier = Modifier.statusBarsPadding()) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Row(
          modifier = Modifier.padding(start = 16.dp),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        Image(
            painter = painterResource(R.drawable.ic_moctale_logo),
            modifier = Modifier.width(140.dp).height(40.dp),
            contentDescription = "Moctale",
        )
        Icon(
            painter = painterResource(R.drawable.ic_beta_icon),
            modifier = Modifier.size(28.dp).padding(start = 4.dp),
            contentDescription = "beta",
            tint = Color.Gray,
        )
      }

      Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        IconButton(
            onClick = {
              if (isSearchScreen) navController.popBackStack()
              else navController.navigate(Routes.SearchScreen.route)
            }
        ) {
          Icon(
              painter =
                  painterResource(
                      if (isSearchScreen) R.drawable.ic_cross_mark_icon
                      else R.drawable.ic_search_icon
                  ),
              contentDescription = if (isSearchScreen) "close" else "search",
              tint = Color.Gray,
          )
        }
        IconButton(onClick = {}) {
          Icon(
              painter = painterResource(R.drawable.ic_bell_icon),
              contentDescription = "notifications",
              tint = Color.Gray,
          )
        }
        IconButton(
            onClick = {
              if (isMenuScreen) navController.popBackStack()
              else navController.navigate(Routes.MenuScreen.route)
            }
        ) {
          Icon(
              painter =
                  if (isMenuScreen) painterResource(R.drawable.ic_cross_mark_icon)
                  else painterResource(R.drawable.ic_three_dot_icon),
              contentDescription = "menu",
              tint = Color.Gray,
          )
        }
      }
    }
    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
  }
}
