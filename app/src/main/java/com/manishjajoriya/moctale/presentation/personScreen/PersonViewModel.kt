package com.manishjajoriya.moctale.presentation.personScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manishjajoriya.moctale.model.person.Person
import com.manishjajoriya.moctale.model.person.PersonContent
import com.manishjajoriya.moctale.usecase.MoctaleApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PersonViewModel @Inject constructor(private val moctaleApiUseCase: MoctaleApiUseCase) :
    ViewModel() {

  private val _person = MutableStateFlow<Person?>(null)
  val person = _person.asStateFlow()

  private val _personContent = MutableStateFlow<PersonContent?>(null)
  val personContent = _personContent.asStateFlow()

  fun fetchPerson(name: String) {
    var attempt = 0
    _person.value = null
    viewModelScope.launch(Dispatchers.IO) {
      while (attempt < 3 && _person.value == null) {
        attempt++
        try {
          _person.value = moctaleApiUseCase.personUseCase.person(name = name)
          break
        } catch (e: Exception) {
          e.printStackTrace()
          delay(500)
        }
      }
    }
  }

  fun fetchPersonContent(name: String) {
    var attempt = 0
    _personContent.value = null
    viewModelScope.launch(Dispatchers.IO) {
      while (attempt < 3 && _personContent.value == null) {
        attempt++
        try {
          _personContent.value = moctaleApiUseCase.personUseCase.personContent(name)
          break
        } catch (e: Exception) {
          e.printStackTrace()
          delay(500)
        }
      }
    }
  }
}
