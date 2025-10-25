package com.manishjajoriya.moctale.presentation.searchScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit, focusManager: FocusManager) {
  OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      modifier = Modifier.fillMaxWidth(0.95f).padding(horizontal = 8.dp),
      textStyle = Typography.bodyLarge,
      placeholder = {
        Text(
            text = "Search for Movies, Shows, Anime, Cast & Crew or Users...",
            style = Typography.bodyLarge,
            softWrap = false,
        )
      },
      leadingIcon = {
        Icon(
            painter = painterResource(R.drawable.ic_search_icon),
            contentDescription = null,
        )
      },
      maxLines = 1,
      singleLine = true,
      shape = RoundedCornerShape(8.dp),
      colors =
          OutlinedTextFieldDefaults.colors(
              focusedBorderColor = Color.White.copy(alpha = 0.9f),
              unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
              cursorColor = Color.White.copy(alpha = 0.9f),
              focusedLeadingIconColor = Color.Gray,
              unfocusedLeadingIconColor = Color.Gray,
              focusedPlaceholderColor = Color.White.copy(alpha = 0.8f),
              unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
          ),
      keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
  )
}
