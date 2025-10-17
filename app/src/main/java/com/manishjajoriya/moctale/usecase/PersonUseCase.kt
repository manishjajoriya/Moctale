package com.manishjajoriya.moctale.usecase

import com.manishjajoriya.moctale.model.person.Person
import com.manishjajoriya.moctale.model.person.PersonContent
import com.manishjajoriya.moctale.remote.MoctaleApi
import jakarta.inject.Inject

class PersonUseCase @Inject constructor(private val moctaleApi: MoctaleApi) {
  suspend fun person(name: String): Person {
    return moctaleApi.person(name = name)
  }

  suspend fun personContent(name: String): PersonContent {
    return moctaleApi.personContent(name = name)
  }
}
