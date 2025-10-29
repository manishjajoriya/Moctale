package com.manishjajoriya.moctale.presentation.browseScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manishjajoriya.moctale.core.base.BaseViewModel
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.domain.model.browse.Data
import com.manishjajoriya.moctale.domain.model.browse.category.Category
import com.manishjajoriya.moctale.domain.model.browse.country.Country
import com.manishjajoriya.moctale.domain.model.browse.genre.Genre
import com.manishjajoriya.moctale.domain.model.browse.language.Language
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class BrowseViewModel
@Inject
constructor(
    private val moctaleApiUseCase: MoctaleApiUseCase,
    private val moctaleRepository: MoctaleRepository,
    networkStatusManager: NetworkStatusManager,
) : BaseViewModel(networkStatusManager) {

  private val _categories = MutableStateFlow<List<Category>?>(null)
  val categories = _categories.asStateFlow()

  private val _genres = MutableStateFlow<List<Genre>?>(null)
  val genre = _genres.asStateFlow()

  private val _countries = MutableStateFlow<List<Country>?>(null)
  val countries = _countries.asStateFlow()

  private val _languages = MutableStateFlow<List<Language>?>(null)
  val languages = _languages.asStateFlow()

  private val _browseData = MutableStateFlow<Flow<PagingData<Data>>?>(null)
  val browseData = _browseData.asStateFlow()

  private val _totalItems = MutableStateFlow<Int?>(null)
  val totalItem = _totalItems.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()
  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  fun fetchCategories(isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _categories.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = { _loading.value = it },
        onError = { _error.value = it },
    ) {
      moctaleApiUseCase.browseUseCase.categories()
    }
  }

  fun fetchGenres(isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _genres.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = { _loading.value = it },
        onError = { _error.value = it },
    ) {
      moctaleApiUseCase.browseUseCase.genres()
    }
  }

  fun fetchCountries(isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _countries.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = { _loading.value = it },
        onError = { _error.value = it },
    ) {
      moctaleApiUseCase.browseUseCase.countries()
    }
  }

  fun fetchLanguages(isRefreshCall: Boolean = false) {
    callApi(
        onValue = { _languages.value = it },
        isRefreshCall = isRefreshCall,
        onRefresh = { _isRefreshing.value = it },
        onLoading = { _loading.value = it },
        onError = { _error.value = it },
    ) {
      moctaleApiUseCase.browseUseCase.languages()
    }
  }

  fun fetchBrowseData(browseSlug: String, category: String?) {
    _browseData.value =
        moctaleRepository
            .getBrowseData(browseScreen = browseSlug, category)
            .cachedIn(viewModelScope)
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _totalItems.value =
            moctaleApiUseCase.browseUseCase
                .categoryData(browseScreen = browseSlug, category, page = 1)
                .count
      } catch (_: Exception) {}
    }
  }
}
