package com.manishjajoriya.moctale.presentation.searchScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manishjajoriya.moctale.domain.model.search.SearchType

@Composable
fun SearchSelector(selectedSearchType: SearchType, onClick: (SearchType) -> Unit) {
  val tabs = listOf(SearchType.Content, SearchType.Person, SearchType.User)
  Row(modifier = Modifier.fillMaxWidth()) {
    tabs.forEach { tab ->
      Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
          modifier = Modifier.weight(1f).clickable { onClick(tab) }.padding(vertical = 12.dp),
      ) {
        Text(
            text = tab.displayName,
            color = if (selectedSearchType == tab) Color.White else Color.Gray,
            fontWeight = if (selectedSearchType == tab) FontWeight.Bold else FontWeight.Normal,
        )

        Box(
            modifier =
                Modifier.padding(top = 8.dp)
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(
                        if (selectedSearchType == tab) Color.White else Color.Transparent,
                        shape = RoundedCornerShape(1.dp),
                    )
        )
      }
    }
  }
}
