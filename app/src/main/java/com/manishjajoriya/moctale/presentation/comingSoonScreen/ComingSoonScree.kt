package com.manishjajoriya.moctale.presentation.comingSoonScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun ComingSoonScree(paddingValues: PaddingValues) {
  Column(
      modifier = Modifier.padding(paddingValues).fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    Text("Coming Soon...", style = Typography.headlineMedium)
  }
}
