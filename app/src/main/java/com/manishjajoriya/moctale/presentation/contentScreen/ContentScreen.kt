package com.manishjajoriya.moctale.presentation.contentScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.components.Section
import com.manishjajoriya.moctale.presentation.contentScreen.components.Banner
import com.manishjajoriya.moctale.presentation.contentScreen.components.CategoryChip
import com.manishjajoriya.moctale.presentation.contentScreen.components.ContentInfo
import com.manishjajoriya.moctale.presentation.contentScreen.components.CustomButton
import com.manishjajoriya.moctale.presentation.contentScreen.components.GenresText
import com.manishjajoriya.moctale.presentation.contentScreen.components.MoctaleMeter
import com.manishjajoriya.moctale.presentation.contentScreen.components.ProfileCircle
import com.manishjajoriya.moctale.presentation.contentScreen.components.ReviewPercentage
import com.manishjajoriya.moctale.presentation.contentScreen.components.TicketButton
import com.manishjajoriya.moctale.presentation.contentScreen.components.VibeChart
import com.manishjajoriya.moctale.ui.theme.Typography
import kotlin.math.roundToInt

@Composable
fun ContentScreen(
    paddingValues: PaddingValues,
    slug: String,
    viewModel: ContentViewModel,
    navController: NavHostController,
) {
  val loading by viewModel.loading.collectAsState()
  val content by viewModel.content.collectAsState()
  val reviewColors =
      listOf(Color(0xFFB048FF), Color(0xFF00D391), Color(0xFFFCB700), Color(0xFFFE647E))
  LaunchedEffect(slug) { viewModel.fetchContent(slug) }

  if (loading) {
    Column(modifier = Modifier.padding(paddingValues)) { CircularProgressIndicator() }
  }

  content?.let { content ->
    LazyColumn(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
      item { Banner(bannerUrl = content.banner) }

      item {
        Column(
            modifier =
                Modifier.offset(y = (-40).dp)
                    .padding(
                        start =
                            paddingValues.calculateStartPadding(
                                layoutDirection = LayoutDirection.Ltr
                            )
                    )
                    .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          ContentInfo(content = content)
          Spacer(modifier = Modifier.height(12.dp))

          // Buttons
          CustomButton(
              modifier = Modifier.fillMaxWidth(),
              icon = R.drawable.ic_eye_icon,
              title = "Mark as Watched",
              color = Color(0xFF9244f1),
          )
          CustomButton(
              modifier = Modifier.fillMaxWidth(),
              icon = R.drawable.ic_bookmark_icon,
              title = "Add to Collection",
              color = Color(0xFF474747),
          )

          // Overview Section + Category Buttons
          Section(title = "Overview") {
            Text(text = content.description, style = Typography.bodyMedium.copy(color = Color.Gray))
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            ) {
              content.categoryList.forEach { category -> CategoryChip(category) }
            }
          }

          // Vibe Chart
          if (!content.genreList.isNullOrEmpty()) {
            val genres = content.genreList
            Section(title = "Vibe Chart") {
              VibeChart(modifier = Modifier.size(260.dp), genres = genres)
              Spacer(modifier = Modifier.height(12.dp))
              FlowRow(
                  modifier = Modifier.width(280.dp),
                  verticalArrangement = Arrangement.spacedBy(16.dp),
                  horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
              ) {
                genres.forEach { genre -> GenresText(genre = genre) }
              }
            }
          }

          // Cast Section
          if (!content.actorList.isNullOrEmpty()) {
            val actors = content.actorList
            Section(title = "Cast") {
              LazyRow(
                  modifier = Modifier.fillMaxWidth().heightIn(min = 184.dp),
                  horizontalArrangement = Arrangement.spacedBy(12.dp),
              ) {
                itemsIndexed(actors, key = { _, actor -> actor.slug }) { _, actor ->
                  ProfileCircle(
                      imageUrl = actor.image,
                      name = actor.name,
                      label = actor.character ?: "Actor",
                      onClick = {
                        navController.navigate(Routes.PersonScreen.route + "/${actor.slug}")
                      },
                  )
                }
              }
            }
          }

          // Crew Section
          content.crewList.let { crews ->
            Section(title = "Crew") {
              LazyRow(
                  modifier = Modifier.fillMaxWidth().heightIn(min = 184.dp),
                  horizontalArrangement = Arrangement.spacedBy(12.dp),
              ) {
                itemsIndexed(crews, key = { _, crew -> crew.slug }) { _, crew ->
                  val label = crew.roleList.joinToString(", ")
                  ProfileCircle(
                      imageUrl = crew.image,
                      name = crew.name,
                      label = label,
                      onClick = {
                        navController.navigate(Routes.PersonScreen.route + "/${crew.slug}")
                      },
                  )
                }
              }
            }
          }

          // Tickets On
          if (content.ticketingSiteList.isNotEmpty()) {
            val ticketList = content.ticketingSiteList
            Section(title = "Ticket On") {
              LazyRow(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
              ) {
                itemsIndexed(items = ticketList) { index, ticketSite ->
                  TicketButton(ticketingSite = ticketSite)
                }
              }
            }
          }

          // Moctale Meter
          Section(title = "Moctale Meter") {
            val typeOfReview = listOf("Perfection", "Go fot it", "Timepass", "Skip")
            val reviewCount =
                listOf(
                    content.countNegativeReview,
                    content.countNeutralReview,
                    content.countPositiveReview,
                    content.countPerfectReview,
                )
            val reviewPercentage =
                listOf(
                    content.percentNegativeReview,
                    content.percentNeutralReview,
                    content.percentPositiveReview,
                    content.percentPerfectReview,
                )
            val totalReviewCount = content.countTotalReview

            Box(
                contentAlignment = Alignment.BottomCenter,
            ) {
              MoctaleMeter(
                  modifier = Modifier.size(300.dp),
                  reviewCount = reviewCount,
                  reviewPercentage = reviewPercentage,
                  totalReviewCount = totalReviewCount,
              )
              Box(
                  contentAlignment = Alignment.BottomCenter,
              ) {
                Column(modifier = Modifier.fillMaxWidth(.6f)) {
                  ReviewPercentage(
                      color = reviewColors[0],
                      title = typeOfReview[0],
                      percentage = reviewPercentage[3].roundToInt().toString(),
                  )
                  ReviewPercentage(
                      color = reviewColors[1],
                      title = typeOfReview[1],
                      percentage = reviewPercentage[2].roundToInt().toString(),
                  )
                  ReviewPercentage(
                      color = reviewColors[2],
                      title = typeOfReview[2],
                      percentage = reviewPercentage[1].roundToInt().toString(),
                  )
                  ReviewPercentage(
                      color = reviewColors[3],
                      title = typeOfReview[3],
                      percentage = reviewPercentage[0].roundToInt().toString(),
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
