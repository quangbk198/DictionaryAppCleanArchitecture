package com.example.dictionaryappcleanarchitecture.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.dictionaryappcleanarchitecture.data.data_source.local.Converters
import com.example.dictionaryappcleanarchitecture.data.data_source.local.WordInfoDatabase
import com.example.dictionaryappcleanarchitecture.data.data_source.remote.DictionaryApi
import com.example.dictionaryappcleanarchitecture.data.repository.WordInfoRepositoryImpl
import com.example.dictionaryappcleanarchitecture.data.util.GsonParser
import com.example.dictionaryappcleanarchitecture.domain.repository.WordInfoRepository
import com.example.dictionaryappcleanarchitecture.domain.use_case.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(
        repository: WordInfoRepository
    ): GetWordInfo {
        return GetWordInfo(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: WordInfoDatabase,
        api: DictionaryApi
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(api, db.wordInfoDao())
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(
        app: Application
    ): WordInfoDatabase {
        return Room.databaseBuilder(
            app,
            WordInfoDatabase::class.java,
            "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(
        okHttpClient: OkHttpClient
    ): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DictionaryApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideHttpCacheSize(
        @ApplicationContext context: Context
    ) : Cache{
        val cacheSize = 10 * 1024 * 1024 //10MB
        return Cache(context.cacheDir, cacheSize.toLong()) 
    }

    @Provides
    @Singleton
    fun providerAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            try {
                return@Interceptor chain.proceed(requestBuilder.build())
            } catch (exception: Exception) {
                throw exception
            }
        }
    }

    @Provides
    @Singleton
    fun providerOkHttpClient(httpCache: Cache, interceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(httpCache)

        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.HEADERS)
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        client.apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(interceptor)
            addNetworkInterceptor(interceptor)
            addInterceptor(logging)
        }

        return client.build()
    }
}