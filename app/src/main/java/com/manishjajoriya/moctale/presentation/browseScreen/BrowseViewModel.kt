package com.manishjajoriya.moctale.presentation.browseScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manishjajoriya.moctale.Constants
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class BrowseViewModel
@Inject
constructor(
    private val moctaleApiUseCase: MoctaleApiUseCase,
    private val moctaleRepository: MoctaleRepository,
    private val networkStatusManager: NetworkStatusManager,
) : ViewModel() {

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

  fun <T : Any> callApi(
      value: MutableStateFlow<List<T>?>,
      isRefreshCall: Boolean,
      fn: suspend () -> List<T>,
  ) {
    viewModelScope.launch {
      try {
        if (isRefreshCall) _isRefreshing.value = true else _loading.value = true
        _error.value = null
        value.value = null

        if (!networkStatusManager.isConnected()) {
          delay(500)
          _error.value = Constants.ERROR_MESSAGE
          return@launch
        }
        val result = withContext(Dispatchers.IO) { fn() }
        value.value = result
      } catch (e: Exception) {
        _error.value = e.message ?: "Unknown error"
      } finally {
        _isRefreshing.value = false
        _loading.value = false
      }
    }
  }

  fun fetchCategories(isRefreshCall: Boolean = false) {
    callApi(
        value = _categories,
        isRefreshCall = isRefreshCall,
    ) {
      moctaleApiUseCase.browseUseCase.categories()
    }
  }

  fun fetchGenres(isRefreshCall: Boolean = false) {
    callApi(value = _genres, isRefreshCall = isRefreshCall) {
      moctaleApiUseCase.browseUseCase.genres()
    }
  }

  fun fetchCountries(isRefreshCall: Boolean = false) {
    callApi(value = _countries, isRefreshCall = isRefreshCall) {
      moctaleApiUseCase.browseUseCase.countries()
    }
  }

  fun fetchLanguages() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _languages.value = moctaleApiUseCase.browseUseCase.languages()
      } catch (e: Exception) {
        e.printStackTrace()
      }
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
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}
