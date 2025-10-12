package com.manishjajoriya.moctale.usecase

import com.manishjajoriya.moctale.model.content.Content
import com.manishjajoriya.moctale.remote.MoctaleApi

class ContentUseCase(private val moctaleApi: MoctaleApi) {
  suspend operator fun invoke(slug: String): Content {
    return moctaleApi.content(slug = slug)
  }
}
