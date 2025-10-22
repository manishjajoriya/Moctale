package com.manishjajoriya.moctale.presentation.browseScreen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.browseScreen.BrowseViewModel
import com.manishjajoriya.moctale.ui.theme.Typography

@Composable
fun CategoriesScreen(
    paddingValues: PaddingValues,
    viewModel: BrowseViewModel,
    navController: NavController,
) {
  val categories by viewModel.categories.collectAsState()
  LaunchedEffect(Unit) { viewModel.fetchCategories() }

  Column(modifier = Modifier.padding(paddingValues).padding(12.dp)) {
    categories?.let { categories ->
      val groupedCategories = categories.groupBy { it.name.first() }
      LazyVerticalGrid(
          columns = GridCells.Adaptive(200.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        groupedCategories.forEach { char, category ->
          item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = char.uppercase(),
                textAlign = TextAlign.Center,
                style = Typography.displayLarge.copy(fontWeight = FontWeight.Bold),
            )
          }
          itemsIndexed(category) { index, data ->
            Text(
                text = data.name,
                modifier =
                    Modifier.clickable {
                          navController.navigate(
                              "${Routes.BrowseScreen.route}/category/${data.slug}/${data.name}"
                          )
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF151515))
                        .padding(12.dp)
                        .fillMaxWidth(),
                style = Typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            )
          }
        }
      }
    }
  }
}
