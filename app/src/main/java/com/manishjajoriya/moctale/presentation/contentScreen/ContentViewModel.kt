package com.manishjajoriya.moctale.presentation.contentScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.moctale.model.content.Content
import com.manishjajoriya.moctale.usecase.MoctaleApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ContentViewModel @Inject constructor(private val moctaleApiUseCase: MoctaleApiUseCase) :
    ViewModel() {

  private val _content = MutableStateFlow<Content?>(null)
  val content = _content.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()

  fun fetchContent(slug: String) {
    var attempts = 0
    _content.value = null
    _loading.value = true
    viewModelScope.launch(Dispatchers.IO) {
      while (attempts < 3 && _content.value == null) {
        attempts++
        try {
          _content.value = moctaleApiUseCase.contentUseCase(slug = slug)
          break
        } catch (e: Exception) {
          e.printStackTrace()
          delay(500)
        }
      }
    }

    _loading.value = false
  }
}
