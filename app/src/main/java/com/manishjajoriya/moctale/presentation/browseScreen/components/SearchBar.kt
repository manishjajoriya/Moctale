package com.manishjajoriya.moctale.presentation.browseScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun SearchBar(value: String, placeHolderText: String, onValueChange: (String) -> Unit) {
  TextField(
      value = value,
      onValueChange = onValueChange,
      modifier = Modifier.fillMaxWidth(),
      singleLine = true,
      leadingIcon = {
        Icon(painter = painterResource(R.drawable.ic_search_icon), contentDescription = null)
      },
      placeholder = {
        Text(text = placeHolderText, style = Typography.titleMedium.copy(color = Color.Gray))
      },
      shape = RoundedCornerShape(36.dp),
      colors =
          TextFieldDefaults.colors()
              .copy(
                  cursorColor = Color.White,
                  focusedIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent,
              ),
  )
}
