package com.manishjajoriya.moctale.presentation.personScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.manishjajoriya.moctale.domain.model.person.Data
import com.manishjajoriya.moctale.domain.model.person.Person
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

@HiltViewModel
class PersonViewModel
@Inject
constructor(
    private val moctaleApiUseCase: MoctaleApiUseCase,
    private val moctaleRepository: MoctaleRepository,
) : ViewModel() {

  private val _person = MutableStateFlow<Person?>(null)
  val person = _person.asStateFlow()

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

  fun getPersonContentFlow(name: String): Flow<PagingData<Data>> {
    return moctaleRepository.getPersonContentData(name)
  }
}
