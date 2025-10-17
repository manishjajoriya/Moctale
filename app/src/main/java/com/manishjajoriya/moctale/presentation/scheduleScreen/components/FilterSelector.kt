package com.manishjajoriya.moctale.presentation.scheduleScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.domain.model.schedule.MovieCategory
import com.manishjajoriya.moctale.domain.model.schedule.SeriesCategory
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun <T> FilterSelector(
    fields: List<T>,
    selectedFiled: T,
    onClear: () -> Unit,
    onClick: (T) -> Unit,
) where T : Any {

  var name: String? = null
  if (selectedFiled is MovieCategory) {
    name = "Movie"
  } else if (selectedFiled is SeriesCategory) {
    name = "Series"
  }

  Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
    name?.let {
      TextButton(
          onClick = onClear,
          colors =
              ButtonDefaults.textButtonColors()
                  .copy(containerColor = Color.White, contentColor = Color.Black),
      ) {
        Row {
          Icon(
              painter = painterResource(R.drawable.ic_cross_mark_icon),
              contentDescription = "deselect",
          )

          Text(text = name, style = Typography.labelLarge.copy(fontWeight = FontWeight.SemiBold))
        }
      }
    }

    fields.forEach { field ->
      val fieldValue =
          when (field) {
            is MovieCategory -> field.displayName
            is SeriesCategory -> field.displayName
            else -> field.toString()
          }

      TextButton(
          onClick = { onClick(field) },
          colors =
              ButtonDefaults.textButtonColors()
                  .copy(
                      containerColor =
                          if (selectedFiled == field) Color.White else Color(0xFF262626),
                      contentColor = if (selectedFiled == field) Color.Black else Color.White,
                  ),
      ) {
        Row {
          if (selectedFiled == field) {
            Icon(
                painter = painterResource(R.drawable.ic_check_mark_icon),
                contentDescription = "selected",
            )
          }
          Text(
              text = fieldValue,
              style = Typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
          )
        }
      }
    }
  }
}
