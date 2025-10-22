package com.manishjajoriya.moctale.domain.repository

import androidx.paging.PagingData
import com.manishjajoriya.moctale.domain.model.schedule.TimeFilter
import com.manishjajoriya.moctale.domain.model.schedule.UiScheduleItem
import com.manishjajoriya.moctale.domain.model.search.content.Data
import kotlinx.coroutines.flow.Flow

interface MoctaleRepository {

  fun getGroupedScheduleData(
      timeFilter: TimeFilter,
      releaseType: String?,
  ): Flow<PagingData<UiScheduleItem>>

  fun getPersonContentData(
      name: String
  ): Flow<PagingData<com.manishjajoriya.moctale.domain.model.person.Data>>

  fun getSearchContentData(searchTerm: String): Flow<PagingData<Data>>

  fun getSearchPersonData(
      searchTerm: String
  ): Flow<PagingData<com.manishjajoriya.moctale.domain.model.search.person.Data>>

  fun getSearchUserData(
      searchTerm: String
  ): Flow<PagingData<com.manishjajoriya.moctale.domain.model.search.user.Data>>

  fun getBrowseData(
      browseScreen: String,
      category: String,
  ): Flow<PagingData<com.manishjajoriya.moctale.domain.model.browse.Data>>
}
