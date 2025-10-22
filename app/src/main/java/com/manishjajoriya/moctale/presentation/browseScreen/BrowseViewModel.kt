package com.manishjajoriya.moctale.presentation.browseScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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

  fun fetchCategories() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _categories.value = moctaleApiUseCase.browseUseCase.categories()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun fetchGenres() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _genres.value = moctaleApiUseCase.browseUseCase.genres()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun fetchCountries() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _countries.value = moctaleApiUseCase.browseUseCase.countries()
      } catch (e: Exception) {
        e.printStackTrace()
      }
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

  fun fetchBrowseData(browseSlug: String, category: String) {
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
