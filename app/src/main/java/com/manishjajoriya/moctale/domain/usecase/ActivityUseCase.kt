package com.manishjajoriya.moctale.domain.usecase

import com.manishjajoriya.moctale.data.remote.api.MoctaleApi
import com.manishjajoriya.moctale.domain.model.content.info.ContentInfo

class ActivityUseCase(private val moctaleApi: MoctaleApi) {
  suspend fun contentInfo(slug: String): ContentInfo {
    return moctaleApi.contentInfo(slug)
  }

  suspend fun markAsWatched(slug: String) {
    return moctaleApi.markAsWatched(slug)
  }

  suspend fun deleteMarkAsWatched(slug: String){
    return moctaleApi.deleteMarkAsWatched(slug)
  }
}
