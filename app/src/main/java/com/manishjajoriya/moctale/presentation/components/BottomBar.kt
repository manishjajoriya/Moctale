package com.manishjajoriya.moctale.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.manishjajoriya.moctale.ui.theme.Background
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun BottomBar(navController: NavController, onClick: (String) -> Unit) {
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  Column(modifier = Modifier.background(Background)) {
    HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
    Row(
        modifier =
            Modifier.fillMaxWidth()
                .padding(
                    top = 4.dp,
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
                    start = 12.dp,
                    end = 12.dp,
                ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      BottomBarButton(
          icon = R.drawable.ic_explore_icon,
          title = "explore",
          currentRoute,
          onClick = onClick,
      )
      BottomBarButton(
          icon = R.drawable.ic_schedule_icon,
          title = "schedule",
          currentRoute,
          onClick = onClick,
      )
      BottomBarButton(
          icon = R.drawable.ic_browse_icon,
          title = "browse",
          currentRoute,
          onClick = onClick,
      )
      BottomBarButton(
          icon = R.drawable.ic_clubs_icon,
          title = "clubs",
          currentRoute,
          onClick = onClick,
      )
      BottomBarButton(
          icon = R.drawable.ic_profile_icon,
          title = "profile",
          currentRoute,
          onClick = onClick,
      )
    }
  }
}

@Composable
fun BottomBarButton(icon: Int, title: String, currentRoute: String?, onClick: (String) -> Unit) {
  TextButton(
      onClick = { onClick(title) },
      colors =
          ButtonDefaults.textButtonColors()
              .copy(contentColor = if (currentRoute == title) Color.White else Color.Gray),
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(painter = painterResource(icon), contentDescription = null)
      Text(text = title.replaceFirstChar { it.uppercase() }, style = Typography.labelSmall)
    }
  }
}
