package com.manishjajoriya.moctale.presentation.personScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.components.MoviePoster
import com.manishjajoriya.moctale.presentation.components.Section
import com.manishjajoriya.moctale.presentation.personScreen.components.PersonPic
import com.manishjajoriya.moctale.ui.theme.Background
import com.manishjajoriya.moctale.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun PersonScreen(
    paddingValues: PaddingValues,
    viewModel: PersonViewModel,
    name: String,
    navController: NavController,
) {

  val person by viewModel.person.collectAsState()
  val personContent by viewModel.personContent.collectAsState()
  LaunchedEffect(name) {
    viewModel.fetchPerson(name)
    viewModel.fetchPersonContent(name)
  }

  person?.let { person ->
    personContent?.let { personContent ->
      //    2000-10-22
      val formatter = DateTimeFormatter.ISO_LOCAL_DATE
      val dateOfBirth = LocalDate.parse(person.birthDate, formatter)
      val stringDateOfBirth =
          "${dateOfBirth.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dateOfBirth.dayOfMonth}, ${dateOfBirth.year}"
      val birthPlace =
          listOf(
                  person.birthCity.takeIf { !it.isNullOrEmpty() },
                  person.birthState.takeIf { !it.isNullOrEmpty() },
                  person.birthCountry.takeIf { !it.isNullOrEmpty() },
              )
              .joinToString(", ")

      LazyVerticalGrid(
          columns = GridCells.Adaptive(minSize = 160.dp),
          modifier = Modifier.padding(paddingValues).padding(horizontal = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(20.dp),
          verticalArrangement = Arrangement.spacedBy(20.dp),
      ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
          Box(
              modifier =
                  Modifier.fillMaxWidth()
                      .height(200.dp)
                      .background(
                          brush =
                              Brush.linearGradient(
                                  listOf(Color(0xFF13223a), Color(0xFF0e1421), Background),
                                  start = Offset(0f, 0f),
                                  end = Offset(0f, Float.POSITIVE_INFINITY),
                              ),
                      )
          )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
          Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
              PersonPic(imageUrl = person.image)
              Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = person.name,
                    style =
                        Typography.titleMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        ),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                  Tag(modifier = Modifier.weight(1f), label = "Born", value = stringDateOfBirth)
                  Tag(modifier = Modifier.weight(1f), label = "Birthplace", value = birthPlace)
                }
              }
            }
            Section(title = "Biography", showDivider = false) {
              Text(text = person.bio, style = Typography.bodyLarge)
            }
          }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
          Text(
              text = "Filmography",
              modifier = Modifier.fillMaxWidth(),
              style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
          )
        }
        itemsIndexed(personContent.data) { index, data ->
          MoviePoster(data = data) { slug ->
            navController.navigate(Routes.ContentScreen.route + "/$slug")
          }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {}
      }
    }
  }
}

@Composable
fun Tag(modifier: Modifier = Modifier, label: String, value: String) {
  Column(modifier = modifier) {
    Text(text = label, style = Typography.titleSmall.copy(color = Color.Gray))
    Text(text = value, style = Typography.titleMedium.copy(color = Color.White.copy(.8f)))
  }
}
