package com.manishjajoriya.moctale.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseSheet(
    isShowBrowseSheet: Boolean,
    navController: NavController,
    onDismissRequest: () -> Unit,
) {
  val data =
      listOf(
          BrowseData("Category", R.drawable.ic_category_icon, Routes.CategoriesScreen.route),
          BrowseData("Genre", R.drawable.ic_genre_icon, Routes.GenreScreen.route),
          BrowseData("Country", R.drawable.ic_country_icon),
          BrowseData("Language", R.drawable.ic_language_icon),
          BrowseData("Family Friendly", R.drawable.ic_family_friendly_icon),
          BrowseData("Award Winners", R.drawable.ic_award_winners_icon),
          BrowseData("Moctale Select", R.drawable.ic_moctale_select_icon),
          BrowseData("Anime", R.drawable.ic_anime_icon),
          BrowseData("Franchise", R.drawable.ic_franchise_icon),
      )
  if (isShowBrowseSheet) {
    ModalBottomSheet(
        containerColor = Color(0xFF080808),
        onDismissRequest = onDismissRequest,
    ) {
      LazyVerticalGrid(
          columns = GridCells.Fixed(3),
          modifier = Modifier.padding(horizontal = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
          Row(
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(
                text = "BROWSE BY",
                style =
                    Typography.titleMedium.copy(
                        Color.White.copy(.7f),
                        fontWeight = FontWeight.SemiBold,
                    ),
            )
            IconButton(onClick = onDismissRequest) {
              Icon(
                  painter = painterResource(R.drawable.ic_cross_mark_icon),
                  modifier = Modifier.size(24.dp),
                  contentDescription = "close",
                  tint = Color.Gray,
              )
            }
          }
        }
        itemsIndexed(data) { index, data ->
          Column(
              modifier =
                  Modifier.background(
                          brush =
                              Brush.linearGradient(
                                  colors = listOf(Color(0xFF131314), Color(0xFF0d0d0e)),
                                  start = Offset(0f, 0f),
                                  end = Offset(200f, 200f),
                              )
                      )
                      .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                      .height(80.dp)
                      .clickable(
                          onClick = {
                            onDismissRequest()
                            navController.navigate(data.route ?: "")
                          }
                      ),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Image(
                painter = painterResource(data.image),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Gray),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = data.displayName,
                style = Typography.labelMedium.copy(color = Color.White.copy(.8f)),
            )
          }
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
      }
    }
  }
}

data class BrowseData(val displayName: String, val image: Int, val route: String? = null)
