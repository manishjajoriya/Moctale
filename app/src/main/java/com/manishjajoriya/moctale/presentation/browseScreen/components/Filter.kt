package com.manishjajoriya.moctale.presentation.browseScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.domain.model.browse.ShowAnime
import com.manishjajoriya.moctale.domain.model.browse.Type
import com.manishjajoriya.moctale.ui.theme.Background
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun FilterOut(
    selectedType: Type,
    onSelectOption: (Type) -> Unit,
    selectedDropDownIndex: Int,
    onDropDownSelect: (Int) -> Unit,
) {
  val types = Type.entries
  val dropDownItems = ShowAnime.entries
  var expanded by remember { mutableStateOf(false) }

  LazyRow(modifier = Modifier) {
    item {
      Row(
          modifier =
              Modifier.clip(RoundedCornerShape(12.dp))
                  .background(Color(0xFF1b1b1b))
                  .padding(horizontal = 8.dp)
      ) {
        // Toggle buttons
        types.forEach { opt ->
          TextButton(
              shape = RoundedCornerShape(8.dp),
              colors =
                  ButtonDefaults.textButtonColors(
                      containerColor =
                          if (selectedType == opt) Color(0xFF474747) else Color.Transparent,
                      contentColor =
                          if (selectedType == opt) Color.White.copy(0.8f)
                          else Color.White.copy(0.7f),
                  ),
              onClick = { onSelectOption(opt) },
          ) {
            Text(text = opt.displayName, style = Typography.titleSmall)
          }
        }
      }
    }
    item {
      Box {
        TextButton(
            modifier = Modifier.height(48.dp).width(160.dp),
            shape = RoundedCornerShape(8.dp),
            colors =
                ButtonDefaults.textButtonColors(
                    containerColor = Color(0xFF474747),
                    contentColor = Color.White.copy(0.8f),
                ),
            onClick = { expanded = true },
        ) {
          Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            Icon(
                painter = painterResource(dropDownItems[selectedDropDownIndex].icon),
                contentDescription = null,
            )
            Text(
                text = dropDownItems[selectedDropDownIndex].displayName,
                style = Typography.titleSmall,
            )
            Icon(
                painter = painterResource(R.drawable.ic_dropdown_icon),
                modifier = Modifier.size(20.dp),
                contentDescription = null,
            )
          }
        }
        DropdownMenu(
            modifier = Modifier.width(160.dp),
            containerColor = Background,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
          dropDownItems.forEachIndexed { index, item ->
            DropdownMenuItem(
                text = { Text(text = item.displayName) },
                leadingIcon = {
                  Icon(painter = painterResource(item.icon), contentDescription = null)
                },
                onClick = { onDropDownSelect(index) },
            )
          }
        }
      }
    }
  }
}
