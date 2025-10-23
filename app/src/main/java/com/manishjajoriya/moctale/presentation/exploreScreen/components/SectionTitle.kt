package com.manishjajoriya.moctale.presentation.exploreScreen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.domain.model.explore.ExploreItem
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun SectionTitle(exploreItem: ExploreItem) {
  Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
  ) {
    AsyncImage(
        model = exploreItem.icon,
        contentDescription = exploreItem.name,
        modifier = Modifier.size(32.dp).padding(end = 8.dp),
    )
    Text(
        text = exploreItem.name,
        style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
    )
  }
}
