package com.manishjajoriya.moctale.di

import com.manishjajoriya.moctale.BuildConfig
import com.manishjajoriya.moctale.Constants
import com.manishjajoriya.moctale.data.remote.api.MoctaleApi
import com.manishjajoriya.moctale.data.repository.MoctaleRepositoryImpl
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import com.manishjajoriya.moctale.domain.usecase.BrowseUseCase
import com.manishjajoriya.moctale.domain.usecase.ContentUseCase
import com.manishjajoriya.moctale.domain.usecase.ExploreUseCase
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import com.manishjajoriya.moctale.domain.usecase.PersonUseCase
import com.manishjajoriya.moctale.domain.usecase.ScheduleUseCase
import com.manishjajoriya.moctale.domain.usecase.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object Module {

  @Provides
  @Singleton
  fun provideClient(): OkHttpClient {
    val headerInterceptor = Interceptor { chain ->
      val original: Request = chain.request()
      val request =
          original
              .newBuilder()
              .header("Cookie", "auth_token=${BuildConfig.AUTH_TOKEN}")
              .method(original.method, original.body)
              .build()
      chain.proceed(request)
    }

    return OkHttpClient.Builder().addInterceptor(headerInterceptor).build()
  }

  @Provides
  @Singleton
  fun provideMoctaleApi(client: OkHttpClient): MoctaleApi =
      Retrofit.Builder()
          .baseUrl(Constants.BASE_URL)
          .client(client)
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
  fun providePersonUseCase(moctaleApi: MoctaleApi) = PersonUseCase(moctaleApi = moctaleApi)

  @Provides
  @Singleton
  fun provideScheduleUseCase(moctaleApi: MoctaleApi) = ScheduleUseCase(moctaleApi = moctaleApi)

  @Provides
  @Singleton
  fun provideSearchUseCase(moctaleApi: MoctaleApi) = SearchUseCase(moctaleApi = moctaleApi)

  @Provides
  @Singleton
  fun provideBrowseUseCase(moctaleApi: MoctaleApi) = BrowseUseCase(moctaleApi = moctaleApi)

  @Provides
  @Singleton
  fun provideMoctaleApiUseCase(
      exploreUseCase: ExploreUseCase,
      contentUseCase: ContentUseCase,
      personUseCase: PersonUseCase,
      scheduleUseCase: ScheduleUseCase,
      searchUseCase: SearchUseCase,
      browseUseCase: BrowseUseCase,
  ) =
      MoctaleApiUseCase(
          exploreUseCase,
          contentUseCase,
          personUseCase,
          scheduleUseCase,
          searchUseCase,
          browseUseCase,
      )

  @Provides
  @Singleton
  fun provideMoctaleRepository(moctaleApiUseCase: MoctaleApiUseCase): MoctaleRepository =
      MoctaleRepositoryImpl(moctaleApiUseCase = moctaleApiUseCase)
}
