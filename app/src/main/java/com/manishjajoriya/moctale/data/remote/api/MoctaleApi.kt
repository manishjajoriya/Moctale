package com.manishjajoriya.moctale.data.remote.api

import com.manishjajoriya.moctale.domain.model.browse.BrowseData
import com.manishjajoriya.moctale.domain.model.browse.category.Category
import com.manishjajoriya.moctale.domain.model.browse.country.Country
import com.manishjajoriya.moctale.domain.model.browse.genre.Genre
import com.manishjajoriya.moctale.domain.model.browse.language.Language
import com.manishjajoriya.moctale.domain.model.content.Content
import com.manishjajoriya.moctale.domain.model.content.info.ContentInfo
import com.manishjajoriya.moctale.domain.model.explore.ExploreItem
import com.manishjajoriya.moctale.domain.model.identity.IdentityUserProfile
import com.manishjajoriya.moctale.domain.model.person.Person
import com.manishjajoriya.moctale.domain.model.person.PersonContent
import com.manishjajoriya.moctale.domain.model.schedule.Schedule
import com.manishjajoriya.moctale.domain.model.search.content.SearchContent
import com.manishjajoriya.moctale.domain.model.search.person.SearchPerson
import com.manishjajoriya.moctale.domain.model.search.user.SearchUser
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoctaleApi {

  // Validate
  @GET("identity/user/profile") suspend fun identityUserProfile(): IdentityUserProfile

  // Explore
  @GET("explore") suspend fun explore(): List<ExploreItem>

  // Content
  @GET("library/content/{slug}") suspend fun content(@Path("slug") slug: String): Content

  // Activity
  @GET("activity/content/{slug}/info")
  suspend fun contentInfo(@Path("slug") slug: String): ContentInfo

  @POST("activity/content/{slug}/watched") suspend fun markAsWatched(@Path("slug") slug: String)

  @DELETE("activity/content/{slug}/watched")
  suspend fun deleteMarkAsWatched(@Path("slug") slug: String)

  // Person
  @GET("library/person/{name}") suspend fun person(@Path("name") name: String): Person

  @GET("library/person/{name}/content")
  suspend fun personContent(@Path("name") name: String, @Query("page") page: Int): PersonContent

  // Schedule
  @GET("schedule")
  suspend fun schedule(
      @Query("timeFilter") timeFilter: String,
      @Query("page") page: Int,
      @Query("releaseType") releaseType: String?,
  ): Schedule

  // Search
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

  // Browse
  @GET("library/{browseScreen}/{category}/content")
  suspend fun categoryData(
      @Path("browseScreen") browseScreen: String,
      @Path("category") category: String?,
      @Query("page") page: Int,
      @Query("type") type: String? = null,
      @Query("show_anime") showAnime: String? = null,
  ): BrowseData

  @GET("library/category") suspend fun categories(): List<Category>

  @GET("library/genre") suspend fun genres(): List<Genre>

  @GET("library/country") suspend fun countries(): List<Country>

  @GET("library/language") suspend fun languages(): List<Language>
}
