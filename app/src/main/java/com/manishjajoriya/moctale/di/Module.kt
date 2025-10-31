package com.manishjajoriya.moctale.di

import android.app.Application
import com.manishjajoriya.moctale.Constants
import com.manishjajoriya.moctale.data.local.datastore.PreferencesDataStoreImpl
import com.manishjajoriya.moctale.data.manager.NetworkStatusManager
import com.manishjajoriya.moctale.data.manager.PreferencesManager
import com.manishjajoriya.moctale.data.remote.api.MoctaleApi
import com.manishjajoriya.moctale.data.repository.MoctaleRepositoryImpl
import com.manishjajoriya.moctale.domain.repository.MoctaleRepository
import com.manishjajoriya.moctale.domain.repository.PreferencesRepository
import com.manishjajoriya.moctale.domain.usecase.ActivityUseCase
import com.manishjajoriya.moctale.domain.usecase.BrowseUseCase
import com.manishjajoriya.moctale.domain.usecase.ContentUseCase
import com.manishjajoriya.moctale.domain.usecase.ExploreUseCase
import com.manishjajoriya.moctale.domain.usecase.MoctaleApiUseCase
import com.manishjajoriya.moctale.domain.usecase.PersonUseCase
import com.manishjajoriya.moctale.domain.usecase.ScheduleUseCase
import com.manishjajoriya.moctale.domain.usecase.SearchUseCase
import com.manishjajoriya.moctale.domain.usecase.IdentityUseCase
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
  fun provideClient(preferencesManager: PreferencesManager): OkHttpClient {
    val headerInterceptor = Interceptor { chain ->
      val original: Request = chain.request()
      val request =
          original
              .newBuilder()
              .header("Cookie", "auth_token=${preferencesManager.authToken.value}")
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
  fun provideValidateUseCase(moctaleApi: MoctaleApi) = IdentityUseCase(moctaleApi = moctaleApi)

  @Provides
  @Singleton
  fun provideExploreUseCase(moctaleApi: MoctaleApi) = ExploreUseCase(moctaleApi = moctaleApi)

  @Provides
  @Singleton
  fun provideContentUseCase(moctaleApi: MoctaleApi) = ContentUseCase(moctaleApi = moctaleApi)

  @Provides
  @Singleton
  fun provideActivityUseCase(moctaleApi: MoctaleApi) = ActivityUseCase(moctaleApi = moctaleApi)

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
    identityUseCase: IdentityUseCase,
    exploreUseCase: ExploreUseCase,
    contentUseCase: ContentUseCase,
    activityUseCase: ActivityUseCase,
    personUseCase: PersonUseCase,
    scheduleUseCase: ScheduleUseCase,
    searchUseCase: SearchUseCase,
    browseUseCase: BrowseUseCase,
  ) =
      MoctaleApiUseCase(
          identityUseCase,
          exploreUseCase,
          contentUseCase,
          activityUseCase,
          personUseCase,
          scheduleUseCase,
          searchUseCase,
          browseUseCase,
      )

  @Provides
  @Singleton
  fun provideMoctaleRepository(moctaleApiUseCase: MoctaleApiUseCase): MoctaleRepository =
      MoctaleRepositoryImpl(moctaleApiUseCase = moctaleApiUseCase)

  @Provides
  @Singleton
  fun providePreferencesRepository(application: Application): PreferencesRepository =
      PreferencesDataStoreImpl(context = application)

  @Provides @Singleton fun providePreferencesManager() = PreferencesManager()

  @Provides
  @Singleton
  fun provideNetworkManager(application: Application) = NetworkStatusManager(context = application)
}
