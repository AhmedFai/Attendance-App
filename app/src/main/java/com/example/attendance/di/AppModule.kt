package com.example.attendance.di

import android.content.Context
import androidx.room.Room
import com.example.attendance.data.remote.api.ApiServices
import com.example.attendance.data.remote.api.LoginApiService
import com.example.attendance.data.datastore.AppPreferences
import com.example.attendance.data.local.dao.AttendanceDao
import com.example.attendance.data.local.dao.BatchDao
import com.example.attendance.data.local.dao.CandidateDao
import com.example.attendance.data.local.dao.FacultyDao
import com.example.attendance.data.local.database.AttendanceDatabase
import com.example.attendance.data.network.ApiLoggingInterceptor
import com.example.attendance.data.network.AuthInterceptor
import com.example.attendance.data.network.NetworkCheckerImpl
import com.example.attendance.data.network.PublicAuthInterceptor
import com.example.attendance.data.repository.AuthRepositoryImpl
import com.example.attendance.data.repository.CandidateMasterDataRepositoryImpl
import com.example.attendance.data.repository.DomainRepositoryImpl
import com.example.attendance.data.repository.FacultyMasterDataRepositoryImpl
import com.example.attendance.data.repository.LoginRepositoryImpl
import com.example.attendance.domain.repository.AuthRepository
import com.example.attendance.domain.repository.CandidateMasterDataRepository
import com.example.attendance.domain.repository.DomainRepository
import com.example.attendance.domain.repository.FacultyMasterDataRepository
import com.example.attendance.domain.repository.LoginRepository
import com.example.attendance.domain.repository.NetworkChecker
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
            .addInterceptor(PublicAuthInterceptor())
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
        @SecureClient client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://localhost/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    fun provideNetworkChecker(
        @ApplicationContext context: Context
    ): NetworkChecker {
        return NetworkCheckerImpl(context)
    }

    @Provides
    @Singleton
    fun provideLoginApi(
        @PublicClient retrofit: Retrofit
    ): LoginApiService =
        retrofit.create(LoginApiService::class.java)

    @Provides
    @Singleton
    fun provideApiService(
        @SecureClient retrofit: Retrofit
    ): ApiServices =
        retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideLoginRepository(
        api: LoginApiService,
        prefs: AppPreferences
    ): LoginRepository =
        LoginRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideCandidateMasterDataApiRepository(
        api: ApiServices,
        prefs: AppPreferences
    ): CandidateMasterDataRepository =
        CandidateMasterDataRepositoryImpl(api,prefs)


    @Provides
    @Singleton
    fun provideFacultyMasterDataApiRepository(
        api: ApiServices,
        prefs: AppPreferences
    ): FacultyMasterDataRepository =
        FacultyMasterDataRepositoryImpl(api,prefs)


    // Room Database

    @Provides
    @Singleton
    fun provideAttendanceDatabase(
        @ApplicationContext context: Context
    ): AttendanceDatabase{
        return Room.databaseBuilder(
            context,
            AttendanceDatabase::class.java,
            "attendance_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBatchDao(
        database: AttendanceDatabase
    ): BatchDao = database.batchDao()

    @Provides
    fun provideCandidateDao(
        database: AttendanceDatabase
    ): CandidateDao = database.candidateDao()

    @Provides
    fun provideFacultyDao(
        database: AttendanceDatabase
    ): FacultyDao = database.facultyDao()

    @Provides
    fun provideAttendanceDao(
        database: AttendanceDatabase
    ): AttendanceDao = database.attendanceDao()

}