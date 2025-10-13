package com.manishjajoriya.moctale.remote

import com.manishjajoriya.moctale.model.content.Content
import com.manishjajoriya.moctale.model.explore.ExploreItem
import com.manishjajoriya.moctale.model.person.Person
import com.manishjajoriya.moctale.model.person.content.PersonContent
import retrofit2.http.GET
import retrofit2.http.Path

interface MoctaleApi {

  @GET("explore") suspend fun explore(): List<ExploreItem>

  @GET("library/content/{slug}") suspend fun content(@Path("slug") slug: String): Content

  @GET("library/person/{name}") suspend fun person(@Path("name") name: String): Person

  @GET("library/person/{name}/content")
  suspend fun personContent(@Path("name") name: String): PersonContent
}
