package com.manishjajoriya.moctale.di

import com.manishjajoriya.moctale.Constants
import com.manishjajoriya.moctale.remote.MoctaleApi
import com.manishjajoriya.moctale.usecase.ContentUseCase
import com.manishjajoriya.moctale.usecase.ExploreUseCase
import com.manishjajoriya.moctale.usecase.MoctaleApiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object Module {

  @Provides
  @Singleton
  fun provideMoctaleApi(): MoctaleApi =
      Retrofit.Builder()
          .baseUrl(Constants.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(MoctaleApi::class.java)

  @Provides
  @Singleton
  fun provideExploreUseCase(moctaleApi: MoctaleApi) = ExploreUseCase(moctaleApi = moctaleApi)

  @Provides
  @Singleton
  fun provideContentUseCase(moctaleApi: MoctaleApi) = ContentUseCase(moctaleApi = moctaleApi)

  @Provides
  @Singleton
  fun provideMoctaleApiUseCase(exploreUseCase: ExploreUseCase, contentUseCase: ContentUseCase) =
      MoctaleApiUseCase(exploreUseCase, contentUseCase)
}
