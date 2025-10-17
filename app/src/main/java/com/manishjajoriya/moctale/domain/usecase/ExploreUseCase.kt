package com.manishjajoriya.moctale.domain.usecase

import com.manishjajoriya.moctale.domain.model.explore.ExploreItem
import com.manishjajoriya.moctale.data.remote.MoctaleApi

class ExploreUseCase(private val moctaleApi: MoctaleApi) {
  suspend operator fun invoke(): List<ExploreItem> {
    return moctaleApi.explore()
  }
}
