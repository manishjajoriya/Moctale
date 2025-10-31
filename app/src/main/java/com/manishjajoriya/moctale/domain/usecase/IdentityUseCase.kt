package com.manishjajoriya.moctale.domain.usecase

import com.manishjajoriya.moctale.data.remote.api.MoctaleApi
import com.manishjajoriya.moctale.domain.model.identity.IdentityUserProfile
import jakarta.inject.Inject

class IdentityUseCase @Inject constructor(private val moctaleApi: MoctaleApi) {
  suspend fun identityUserProfile(): IdentityUserProfile {
    return moctaleApi.identityUserProfile()
  }
}
