package com.manishjajoriya.moctale.remote

import com.manishjajoriya.moctale.model.content.Content
import com.manishjajoriya.moctale.model.explore.ExploreItem
import retrofit2.http.GET
import retrofit2.http.Path

interface MoctaleApi {

  @GET("explore") suspend fun explore(): List<ExploreItem>

  @GET("library/content/{slug}") suspend fun content(@Path("slug") slug: String): Content
}
