package com.manishjajoriya.moctale.presentation.searchScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.manishjajoriya.moctale.domain.model.search.SearchType
import com.manishjajoriya.moctale.navgraph.Routes
import com.manishjajoriya.moctale.presentation.components.ErrorMessageText
import com.manishjajoriya.moctale.presentation.searchScreen.components.SearchBar
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
  val searchContentFlow by viewModel.searchContentData.collectAsState()
  val searchPersonFlow by viewModel.searchPersonData.collectAsState()
  val searchUserFlow by viewModel.searchUserData.collectAsState()
  val isRefreshing by viewModel.isRefreshing.collectAsState()
  val error by viewModel.error.collectAsState()

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

  // Get Load sate of each flow item
  val searchContentLoadState = searchContentItems?.loadState
  val searchPersonLoadState = searchPersonItems?.loadState
  val searchUserLoadState = searchUserItems?.loadState

  var loadStateError by remember { mutableStateOf<String?>(null) }

  LaunchedEffect(searchContentLoadState, searchPersonLoadState, searchUserLoadState) {
    when (selectedSearchType) {
      SearchType.Content -> {
        when (searchContentLoadState?.refresh) {
          is LoadState.Error ->
              loadStateError = (searchContentLoadState.refresh as LoadState.Error).error.message
          else -> {}
        }
      }
      SearchType.Person -> {
        when (searchPersonLoadState?.refresh) {
          is LoadState.Error ->
              loadStateError = (searchPersonLoadState.refresh as LoadState.Error).error.message
          else -> {}
        }
      }
      SearchType.User -> {
        when (searchUserLoadState?.refresh) {
          is LoadState.Error ->
              loadStateError = (searchUserLoadState.refresh as LoadState.Error).error.message
          else -> {}
        }
      }
    }
  }

  // GridCell
  val gridCells =
      when (selectedSearchType) {
        SearchType.Person -> GridCells.Adaptive(150.dp)
        else -> GridCells.Adaptive(300.dp)
      }

  PullToRefreshBox(
      isRefreshing = isRefreshing,
      onRefresh = {
        when (selectedSearchType) {
          SearchType.Content -> viewModel.getSearchContentFlow(inputText, isRefreshCall = true)
          SearchType.Person -> viewModel.getSearchPersonFlow(inputText, isRefreshCall = true)
          SearchType.User -> viewModel.getSearchUserFlow(inputText, isRefreshCall = true)
        }
      },
  ) {
    LazyVerticalGrid(
        columns = gridCells,
        modifier =
            Modifier.padding(paddingValues).padding(8.dp).fillMaxSize().clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
              focusManager.clearFocus()
            },
        verticalArrangement =
            if (error != null || loadStateError != null) Arrangement.Center
            else Arrangement.spacedBy(20.dp),
        horizontalArrangement =
            if (error != null || loadStateError != null) Arrangement.Center
            else Arrangement.spacedBy(20.dp),
    ) {
      if (error == null && !isRefreshing && loadStateError == null) {
        item(span = { GridItemSpan(maxLineSpan) }) {
          Column {
            Spacer(modifier = Modifier.height(12.dp))

            SearchBar(
                value = inputText,
                onValueChange = { inputText = it },
                focusManager = focusManager,
            )
            SearchSelector(selectedSearchType) { selectedSearchType = it }
          }
        }

        // Display content search results
        if (selectedSearchType == SearchType.Content) {
          searchContentItems?.let { items ->
            item(span = { GridItemSpan(maxLineSpan) }) {
              Text(text = "SEARCH RESULTS", style = Typography.titleMedium)
            }
            items(items.itemCount) { index ->
              items[index]?.let { data ->
                SearchContentResult(
                    data = data,
                    onClick = { slug ->
                      navController.navigate(Routes.ContentScreen.route + "/$slug")
                    },
                )
              }
            }
          }
        }

        // Displaying cast & crew results
        if (selectedSearchType == SearchType.Person) {
          searchPersonItems?.let { items ->
            item(span = { GridItemSpan(maxLineSpan) }) {
              Text(text = "SEARCH RESULTS", style = Typography.titleMedium)
            }
            items(items.itemCount) { index ->
              items[index]?.let { data ->
                SearchPersonResult(
                    data,
                    onClick = { slug ->
                      navController.navigate(Routes.PersonScreen.route + "/$slug")
                    },
                )
              }
            }
          }
        }

        if (selectedSearchType == SearchType.User) {
          searchUserItems?.let { items ->
            item(span = { GridItemSpan(maxLineSpan) }) {
              Text(text = "SEARCH RESULTS", style = Typography.titleMedium)
            }
            items(items.itemCount) { index -> items[index]?.let { data -> SearchUserResult(data) } }
          }
        }
      } else {
        error?.let { error -> item { ErrorMessageText(error, isPullToRefreshAvailable = true) } }
        loadStateError?.let { error ->
          item { ErrorMessageText(error, isPullToRefreshAvailable = true) }
        }
      }
    }
  }
}
