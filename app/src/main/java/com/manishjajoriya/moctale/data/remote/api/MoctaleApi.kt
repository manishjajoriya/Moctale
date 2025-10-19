package com.manishjajoriya.moctale.data.remote.api

import com.manishjajoriya.moctale.domain.model.content.Content
import com.manishjajoriya.moctale.domain.model.explore.ExploreItem
import com.manishjajoriya.moctale.domain.model.person.Person
import com.manishjajoriya.moctale.domain.model.person.PersonContent
import com.manishjajoriya.moctale.domain.model.schedule.Schedule
import com.manishjajoriya.moctale.domain.model.search.content.SearchContent
import com.manishjajoriya.moctale.domain.model.search.person.SearchPerson
import com.manishjajoriya.moctale.domain.model.search.user.SearchUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoctaleApi {

  @GET("explore") suspend fun explore(): List<ExploreItem>

  @GET("library/content/{slug}") suspend fun content(@Path("slug") slug: String): Content

  @GET("library/person/{name}") suspend fun person(@Path("name") name: String): Person

  @GET("library/person/{name}/content")
  suspend fun personContent(@Path("name") name: String, @Query("page") page: Int): PersonContent

  @GET("schedule")
  suspend fun schedule(
      @Query("timeFilter") timeFilter: String,
      @Query("page") page: Int,
      @Query("releaseType") releaseType: String?,
  ): Schedule

  @GET("search/")
  suspend fun searchContent(
      @Query("q") searchTerm: String,
      @Query("page") page: Int,
  ): SearchContent

  @GET("search/person")
  suspend fun searchPerson(
      @Query("q") searchTerm: String,
      @Query("page") page: Int,
  ): SearchPerson

  @GET("search/user")
  suspend fun searchUser(
      @Query("q") searchTerm: String,
      @Query("page") page: Int,
  ): SearchUser
}
