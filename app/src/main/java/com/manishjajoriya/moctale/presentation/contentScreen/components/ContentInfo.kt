package com.manishjajoriya.moctale.presentation.contentScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.domain.model.content.Content
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun ContentInfo(content: Content) {

  val type = if (content.isShow) "Show • ${content.year}" else "Movie • ${content.year}"

  Row {
    AsyncImage(
        model = content.image,
        contentDescription = null,
        modifier = Modifier.clip(RoundedCornerShape(12.dp)).width(132.dp),
    )
    Column(modifier = Modifier.padding(start = 20.dp)) {
      Spacer(modifier = Modifier.height(12.dp))
      Text(text = type, style = Typography.labelLarge, color = Color.Gray)
      Text(
          text = content.name,
          style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold),
      )
      Spacer(modifier = Modifier.height(12.dp))
      Label(content = content)
    }
  }
}

@Composable
fun Label(content: Content) {
  val isShowRunner = content.directorList.isEmpty()
  Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Row {
      Info(
          label = if (isShowRunner) "Showrunner" else "Directed By",
          value = if (isShowRunner) content.crewList[0].name else content.directorList[0].name,
          modifier = Modifier.weight(1f),
      )
      Info(label = "Country", value = content.countryOfOrigin.name, modifier = Modifier.weight(1f))
    }
    Row {
      Info(label = "Language", value = content.languageList[0].name, modifier = Modifier.weight(1f))
      content.ageRatingFormatted?.let {
        Info(label = "Age Rating", value = it, modifier = Modifier.weight(1f))
      }
    }
  }
}

@Composable
fun Info(label: String, value: String, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Text(text = label, style = Typography.labelLarge, color = Color.Gray)
    Text(
        text = value,
        style =
            Typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(.7f),
            ),
    )
  }
}
