package com.manishjajoriya.moctale.usecase

import com.manishjajoriya.moctale.model.explore.ExploreItem
import com.manishjajoriya.moctale.remote.MoctaleApi

class ExploreUseCase(private val moctaleApi: MoctaleApi) {
  suspend operator fun invoke(): List<ExploreItem> {
    return moctaleApi.explore()
  }
}
