package com.manishjajoriya.moctale.remote

import com.manishjajoriya.moctale.model.explore.ExploreItem
import retrofit2.http.GET

interface MoctaleApi {

  @GET("explore") suspend fun explore(): List<ExploreItem>
}
