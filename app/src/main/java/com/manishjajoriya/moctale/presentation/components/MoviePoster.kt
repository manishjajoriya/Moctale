package com.manishjajoriya.moctale.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.model.explore.Content
import com.manishjajoriya.moctale.model.person.Data
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun MoviePoster(content: Content, onClick: (String) -> Unit) {
  val caption =
      if (!content.caption.isNullOrEmpty()) content.caption
      else if (content.isShow) "Show • ${content.year}" else "Movie • ${content.year}"

  Column(modifier = Modifier.clickable(onClick = { onClick(content.slug) })) {
    AsyncImage(
        model = content.image,
        contentDescription = content.name,
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = content.name, style = Typography.titleMedium)
    Text(text = caption, style = Typography.labelMedium, color = Color.Gray)
  }
}

@Composable
fun MoviePoster(
    data: Data,
    onClick: (String) -> Unit,
) {
  val caption =
      if (data.content.isShow) "Show • ${data.content.year}" else "Movie • ${data.content.year}"

  Column(modifier = Modifier.clickable(onClick = { onClick(data.content.slug) })) {
    if (data.content.image != null) {
      AsyncImage(
          model = data.content.image,
          contentDescription = data.content.name,
          modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
          contentScale = ContentScale.Crop,
      )
    } else {
      Image(
          painter = painterResource(R.drawable.ic_placeholder_move),
          contentDescription = data.content.name,
          modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
          contentScale = ContentScale.Crop,
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = data.content.name, style = Typography.titleMedium)
    Text(text = caption, style = Typography.labelMedium, color = Color.Gray)
    Text(text = "as ${data.character}.", style = Typography.labelMedium, color = Color.Gray)
  }
}
