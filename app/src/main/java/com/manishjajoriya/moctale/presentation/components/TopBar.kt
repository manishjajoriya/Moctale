package com.manishjajoriya.moctale.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.R

@Composable
fun TopBar(modifier: Modifier = Modifier) {
  Column(modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing)) {
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
        IconButton(onClick = {}) {
          Icon(
              painter = painterResource(R.drawable.ic_search_icon),
              contentDescription = "search",
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
        IconButton(onClick = {}) {
          Icon(
              painter = painterResource(R.drawable.ic_three_dot_icon),
              contentDescription = "menu",
              tint = Color.Gray,
          )
        }
      }
    }
    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
  }
}
