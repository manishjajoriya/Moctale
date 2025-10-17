package com.manishjajoriya.moctale.presentation.exploreScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.moctale.domain.model.explore.ExploreItem
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ExploreViewModel @Inject constructor(private val moctaleApiUseCase: MoctaleApiUseCase) :
    ViewModel() {

  private val _exploreData = MutableStateFlow<List<ExploreItem>?>(null)
  val exploreData = _exploreData.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  fun fetchExploreData() {
    var attempts = 0
    _loading.value = true
    viewModelScope.launch(Dispatchers.IO) {
      while (attempts < 3 && _exploreData.value == null) {
        attempts++
        try {
          _exploreData.value = moctaleApiUseCase.exploreUseCase()
          _loading.value = false
        } catch (e: Exception) {
          e.printStackTrace()
          delay(500)
        }
      }
      _loading.value = false
    }
  }
}
