package com.manishjajoriya.moctale.domain.usecase

import android.util.Log
import com.manishjajoriya.moctale.Constants
import com.manishjajoriya.moctale.data.remote.api.MoctaleApi
import com.manishjajoriya.moctale.domain.model.browse.category.Category
import com.manishjajoriya.moctale.domain.model.browse.BrowseData
import com.manishjajoriya.moctale.domain.model.browse.Country
import com.manishjajoriya.moctale.domain.model.browse.genre.Genre

class BrowseUseCase(private val moctaleApi: MoctaleApi) {

  suspend fun categoryData(browseScreen: String, category: String, page: Int): BrowseData {
    Log.i("LOG", "${Constants.BASE_URL}/library/$browseScreen/$category/content/?page=$page")
    return moctaleApi.categoryData(browseScreen = browseScreen, category = category, page = page)
  }

  suspend fun categories(): List<Category> {
    return moctaleApi.categories()
  }

  suspend fun genres(): List<Genre> {
    return moctaleApi.genres()
  }

  suspend fun countries(): List<Country> {
    return moctaleApi.countries()
  }
}
