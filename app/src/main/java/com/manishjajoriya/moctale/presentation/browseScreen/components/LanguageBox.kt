package com.manishjajoriya.moctale.presentation.browseScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.domain.model.browse.language.Language
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun LanguageBox(language: Language, onClick: () -> Unit) {
  Column(
      modifier =
          Modifier.clickable(onClick = onClick)
              .height(160.dp)
              .background(Color(0xFF171717))
              .padding(8.dp), // padding around the whole column
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
  ) {
    // Circle with fixed size
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier.size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF171717))
                .border(2.dp, Color.Gray, CircleShape),
    ) {
      Text(
          text = language.name.take(2).uppercase(),
          style = Typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(text = language.name, style = Typography.titleMedium)
  }
}
