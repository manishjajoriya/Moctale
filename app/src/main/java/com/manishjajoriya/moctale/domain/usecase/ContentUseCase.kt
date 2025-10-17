package com.manishjajoriya.moctale.domain.usecase

import com.manishjajoriya.moctale.domain.model.content.Content
import com.manishjajoriya.moctale.data.remote.MoctaleApi

class ContentUseCase(private val moctaleApi: MoctaleApi) {
  suspend operator fun invoke(slug: String): Content {
    return moctaleApi.content(slug = slug)
  }
}
