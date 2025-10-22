package com.manishjajoriya.moctale.domain.usecase

import com.manishjajoriya.moctale.data.remote.api.MoctaleApi
import com.manishjajoriya.moctale.domain.model.validate.Validate
import jakarta.inject.Inject

class ValidateUseCase @Inject constructor(private val moctaleApi: MoctaleApi) {
  suspend operator fun invoke(): Validate {
    return moctaleApi.validate()
  }
}
