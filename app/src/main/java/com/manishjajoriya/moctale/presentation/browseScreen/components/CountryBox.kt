package com.manishjajoriya.moctale.presentation.browseScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manishjajoriya.moctale.domain.model.browse.Country
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun CountryBox(country: Country, onClick: () -> Unit) {

  Column(
      modifier = Modifier.clickable(onClick = onClick).height(160.dp).background(Color(0xFF171717)),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    AsyncImage(
        model = country.image,
        contentDescription = null,
        modifier = Modifier.size(60.dp),
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = country.name, style = Typography.titleMedium)
  }
}
