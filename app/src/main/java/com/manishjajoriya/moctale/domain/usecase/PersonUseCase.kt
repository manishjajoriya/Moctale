package com.manishjajoriya.moctale.domain.usecase

import com.manishjajoriya.moctale.domain.model.person.Person
import com.manishjajoriya.moctale.domain.model.person.PersonContent
import com.manishjajoriya.moctale.data.remote.api.MoctaleApi
import jakarta.inject.Inject

class PersonUseCase @Inject constructor(private val moctaleApi: MoctaleApi) {
  suspend fun person(name: String): Person {
    return moctaleApi.person(name = name)
  }

  suspend fun personContent(name: String, page : Int): PersonContent {
    return moctaleApi.personContent(name = name, page = page)
  }
}
