package com.manishjajoriya.moctale.presentation.searchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.manishjajoriya.moctale.R
import com.manishjajoriya.moctale.domain.model.search.SearchType
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.searchScreen.components.SearchContentResult
import com.manishjajoriya.moctale.presentation.searchScreen.components.SearchPersonResult
import com.manishjajoriya.moctale.presentation.searchScreen.components.SearchSelector
import com.manishjajoriya.moctale.presentation.searchScreen.components.SearchUserResult
import com.manishjajoriya.moctale.ui.theme.Typography
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    viewModel: SearchViewModel,
    navController: NavHostController,
) {
  var inputText by remember { mutableStateOf("") }
  val focusManager = LocalFocusManager.current
  var selectedSearchType by remember { mutableStateOf(SearchType.Content) }
  val searchContentFlow by viewModel.searchContentFlow.collectAsState()
  val searchPersonFlow by viewModel.searchPersonFlow.collectAsState()
  val searchUserFlow by viewModel.searchUserFlow.collectAsState()

  // Trigger search with debounce
  LaunchedEffect(inputText, selectedSearchType) {
    snapshotFlow { inputText.trim() }
        .filter { it.isNotEmpty() }
        .distinctUntilChanged()
        .debounce(1000)
        .collectLatest { searchTerm ->
          when (selectedSearchType) {
            SearchType.Content -> viewModel.getSearchContentFlow(searchTerm)
            SearchType.Person -> viewModel.getSearchPersonFlow(searchTerm)
            SearchType.User -> viewModel.getSearchUserFlow(searchTerm)
          }
        }
  }

  // Collect paging items once per new flow
  val searchContentItems = searchContentFlow?.collectAsLazyPagingItems()
  val searchPersonItems = searchPersonFlow?.collectAsLazyPagingItems()
  val searchUserItems = searchUserFlow?.collectAsLazyPagingItems()

  // GridCell
  val gridCells =
      when (selectedSearchType) {
        SearchType.Person -> GridCells.Adaptive(150.dp)
        else -> GridCells.Adaptive(300.dp)
      }

  LazyVerticalGrid(
      columns = gridCells,
      modifier =
          Modifier.padding(paddingValues).padding(8.dp).fillMaxSize().clickable(
              indication = null,
              interactionSource = remember { MutableInteractionSource() },
          ) {
            focusManager.clearFocus()
          },
      verticalArrangement = Arrangement.spacedBy(12.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
  ) {
    item(span = { GridItemSpan(maxLineSpan) }) {
      Column {
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
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

        SearchSelector(selectedSearchType) { selectedSearchType = it }

        Text(text = "SEARCH RESULTS", style = Typography.titleMedium)
      }
    }

    // Display content search results
    if (selectedSearchType == SearchType.Content) {
      searchContentItems?.let { items ->
        items(items.itemCount) { index ->
          items[index]?.let { data ->
            SearchContentResult(
                data = data,
                onClick = { slug -> navController.navigate(Routes.ContentScreen.route + "/$slug") },
            )
          }
        }
      }
    }

    // Displaying cast & crew results
    if (selectedSearchType == SearchType.Person) {
      searchPersonItems?.let { items ->
        items(items.itemCount) { index ->
          items[index]?.let { data ->
            SearchPersonResult(
                data,
                onClick = { slug -> navController.navigate(Routes.PersonScreen.route + "/$slug") },
            )
          }
        }
      }
    }

    if (selectedSearchType == SearchType.User) {
      searchUserItems?.let { items ->
        items(items.itemCount) { index -> items[index]?.let { data -> SearchUserResult(data) } }
      }
    }
  }
}
