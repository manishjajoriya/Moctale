package com.manishjajoriya.moctale.presentation.searchScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.domain.model.search.content.Data
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun SearchContentResult(data: Data, onClick : (String) -> Unit) {
  Row(
      modifier =
          Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color(0xFF171717)).clickable(onClick = {onClick(data.slug)}),
      verticalAlignment = Alignment.CenterVertically,
  ) {
    AsyncImage(
        model = data.image,
        contentDescription = null,
        modifier = Modifier.width(160.dp).padding(12.dp).clip(RoundedCornerShape(12.dp)),
    )
    Column {
      Text(
          text = data.name,
          style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
      )
      Text(
          text = "${data.year} • ${if (data.isShow) "Show" else "Movie"}",
          style = Typography.labelLarge.copy(color = Color.Gray),
      )
    }
  }
}
