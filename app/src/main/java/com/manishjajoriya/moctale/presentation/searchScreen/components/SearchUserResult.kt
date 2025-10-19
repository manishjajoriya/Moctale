package com.manishjajoriya.moctale.presentation.searchScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.domain.model.search.user.Data
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun SearchUserResult(data: Data) {
  Row(
      modifier =
          Modifier.fillMaxWidth()
              .clip(RoundedCornerShape(16.dp))
              .background(Color(0xFF171717))
              .padding(20.dp),
      verticalAlignment = Alignment.CenterVertically,
  ) {
    if (data.photoThumbnail != null) {
      AsyncImage(
          model = data.photoThumbnail,
          contentDescription = null,
          modifier = Modifier.size(80.dp).clip(CircleShape),
      )
    } else {
      Image(
          painter = painterResource(R.drawable.ic_default_placeholder_avtar),
          contentDescription = null,
          modifier = Modifier.size(80.dp).clip(CircleShape),
      )
    }
    Column(modifier = Modifier.padding(12.dp)) {
      Text(text = "${ data.firstName } ${data.lastName}", style = Typography.titleMedium)
      Text(text = "@${data.username}", style = Typography.labelLarge.copy(color = Color.Gray))
    }
  }
}
