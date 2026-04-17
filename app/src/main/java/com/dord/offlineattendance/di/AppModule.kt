package com.dord.offlineattendance.di

import android.content.Context
import androidx.room.Room
import com.dord.offlineattendance.data.remote.api.ApiServices
import com.dord.offlineattendance.data.remote.api.LoginApiService
import com.dord.offlineattendance.data.datastore.AppPreferences
import com.dord.offlineattendance.data.local.dao.AttendanceDao
import com.dord.offlineattendance.data.local.dao.BatchDao
import com.dord.offlineattendance.data.local.dao.CandidateDao
import com.dord.offlineattendance.data.local.dao.FacultyDao
import com.dord.offlineattendance.data.local.database.AttendanceDatabase
import com.dord.offlineattendance.data.network.ApiLoggingInterceptor
import com.dord.offlineattendance.data.network.AuthInterceptor
import com.dord.offlineattendance.data.network.NetworkCheckerImpl
import com.dord.offlineattendance.data.network.PublicAuthInterceptor
import com.dord.offlineattendance.data.repository.AuthRepositoryImpl
import com.dord.offlineattendance.data.repository.CandidateMasterDataRepositoryImpl
import com.dord.offlineattendance.data.repository.DomainRepositoryImpl
import com.dord.offlineattendance.data.repository.FacultyMasterDataRepositoryImpl
import com.dord.offlineattendance.data.repository.LanguageRepositoryImpl
import com.dord.offlineattendance.data.repository.LoginRepositoryImpl
import com.dord.offlineattendance.data.repository.LogoutRepositoryImpl
import com.dord.offlineattendance.data.repository.UpdateRegisteredFaceRepositoryImpl
import com.dord.offlineattendance.domain.repository.AuthRepository
import com.dord.offlineattendance.domain.repository.CandidateMasterDataRepository
import com.dord.offlineattendance.domain.repository.DomainRepository
import com.dord.offlineattendance.domain.repository.FacultyMasterDataRepository
import com.dord.offlineattendance.domain.repository.LanguageRepository
import com.dord.offlineattendance.domain.repository.LoginRepository
import com.dord.offlineattendance.domain.repository.LogoutRepository
import com.dord.offlineattendance.domain.repository.NetworkChecker
import com.dord.offlineattendance.domain.repository.UpdateRegisteredFaceRepository
import com.dord.offlineattendance.domain.usecase.changeLanguage.GetLanguageUseCase
import com.dord.offlineattendance.domain.usecase.changeLanguage.SetLanguageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
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
    fun providePublicOkHttp(): OkHttpClient {

        // Demo
        val certificatePinner = CertificatePinner.Builder()
            .add(
                "kaushal.dord.gov.in",
                "sha256/e6j+An86I+qn81N79S/QicWTVdfjj0YsJo3/k7sq43E="
            )
            .add(
                "kaushal.dord.gov.in",
                "sha256/a9khL0ZJxlnJyrxstg/P+seiDCm+Yf30srXyFocBaI0="
            )
            .build()

//        // Live
//        val certificatePinner = CertificatePinner.Builder()
//            .add(
//                "kaushal.rural.gov.in",
//                "sha256/KY00gO3RItl8kWF7tuMBl13Q4kXD+pZanVHy6o1XR1c="
//            )
//            .add(
//                "kaushal.rural.gov.in",
//                "sha256/AlSQhgtJirc8ahLyekmtX+Iw+v46yPYRLJt9Cq1GlB0="
//            )
//            .build()

        return OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .addInterceptor(PublicAuthInterceptor())
            .addInterceptor(ApiLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    @SecureClient
    fun provideSecureOkHttp(
        prefs: AppPreferences
    ): OkHttpClient {

        // Demo
        val certificatePinner = CertificatePinner.Builder()
            .add(
                "kaushal.dord.gov.in",
                "sha256/e6j+An86I+qn81N79S/QicWTVdfjj0YsJo3/k7sq43E="
            )
            .add(
                "kaushal.dord.gov.in",
                "sha256/a9khLOZJxlnJyrxstg/P+seiDCm+Yf3OsrXyFocBaI0="
            )
            .build()

//        // Live
//        val certificatePinner = CertificatePinner.Builder()
//            .add(
//                "kaushal.rural.gov.in",
//                "sha256/KY00gO3RItl8kWF7tuMBl13Q4kXD+pZanVHy6o1XR1c="
//            )
//            .add(
//                "kaushal.rural.gov.in",
//                "sha256/AlSQhgtJirc8ahLyekmtX+Iw+v46yPYRLJt9Cq1GlB0="
//            )
//            .build()

        return OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .addInterceptor(AuthInterceptor(prefs))
            .addInterceptor(ApiLoggingInterceptor())
            .build()
    }

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
        CandidateMasterDataRepositoryImpl(api, prefs)


    @Provides
    @Singleton
    fun provideFacultyMasterDataApiRepository(
        api: ApiServices,
        prefs: AppPreferences
    ): FacultyMasterDataRepository =
        FacultyMasterDataRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideUpdateFaceRegisteredApiRepository(
        api: ApiServices,
        prefs: AppPreferences
    ): UpdateRegisteredFaceRepository =
        UpdateRegisteredFaceRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideLogoutApiRepository(
        api: ApiServices,
        prefs: AppPreferences
    ): LogoutRepository =
        LogoutRepositoryImpl(api, prefs)



    // Room Database

    @Provides
    @Singleton
    fun provideAttendanceDatabase(
        @ApplicationContext context: Context
    ): AttendanceDatabase {
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

    @Provides
    @Singleton
    fun provideLanguageRepository(ds: AppPreferences): LanguageRepository =
        LanguageRepositoryImpl(ds)

    @Provides
    fun provideGetLanguageUseCase(repo: LanguageRepository) =
        GetLanguageUseCase(repo)

    @Provides
    fun provideSetLanguageUseCase(repo: LanguageRepository) =
        SetLanguageUseCase(repo)

}