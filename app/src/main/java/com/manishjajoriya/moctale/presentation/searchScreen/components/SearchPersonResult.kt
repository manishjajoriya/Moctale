package com.manishjajoriya.moctale.presentation.searchScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.manishjajoriya.moctale.domain.model.search.person.Data
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun SearchPersonResult(data: Data, onClick: (String) -> Unit) {
  Column(
      modifier =
          Modifier.fillMaxWidth()
              .clip(RoundedCornerShape(16.dp))
              .background(Color(0xFF171717))
              .padding(20.dp)
              .clickable(onClick = { onClick(data.slug) }),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    if (data.image != null) {
      AsyncImage(
          model = data.image,
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
    Text(text = data.name, style = Typography.labelLarge, modifier = Modifier.padding(top = 4.dp))
  }
}
