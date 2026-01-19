package com.example.attendance.di

import android.content.Context
import com.example.attendance.data.api.ApiServices
import com.example.attendance.data.api.LoginApiService
import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.data.network.ApiLoggingInterceptor
import com.example.attendance.data.network.AuthInterceptor
import com.example.attendance.data.repository.AuthRepositoryImpl
import com.example.attendance.data.repository.DomainRepositoryImpl
import com.example.attendance.data.repository.LoginRepositoryImpl
import com.example.attendance.domain.repository.AuthRepository
import com.example.attendance.domain.repository.DomainRepository
import com.example.attendance.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePrefs(@ApplicationContext context: Context) =
        AppPreferences(context)

    @Provides
    @Singleton
    fun provideDomainRepo(prefs: AppPreferences): DomainRepository =
        DomainRepositoryImpl(prefs)

    @Provides
    @Singleton
    fun providesAuthRepo(prefs: AppPreferences): AuthRepository =
        AuthRepositoryImpl(prefs)

    @Provides
    @Singleton
    @PublicClient
    fun providePublicOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ApiLoggingInterceptor())
            .build()

    @Provides
    @Singleton
    @SecureClient
    fun provideSecureOkHttp(
        prefs: AppPreferences
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(prefs))
            .addInterceptor(ApiLoggingInterceptor())
            .build()

    @Provides
    @Singleton
    @PublicClient
    fun providePublicRetrofit(
        @PublicClient client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://localhost/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    @Provides
    @Singleton
    @SecureClient
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://localhost/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideLoginApi(
        @PublicClient retrofit: Retrofit
    ): LoginApiService =
        retrofit.create(LoginApiService::class.java)

//    @Provides
//    @Singleton
//    fun provideApiService(
//        @SecureClient retrofit: Retrofit
//    ): ApiServices =
//        retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideLoginRepository(
        api: LoginApiService,
        prefs: AppPreferences
    ): LoginRepository =
        LoginRepositoryImpl(api,prefs)

//    @Provides
//    @Singleton
//    fun loginApiRepository(
//        api: ApiServices,
//        prefs: AppPreferences
//    ): LoginRepository =
//        LoginRepositoryImpl(api,prefs)

}