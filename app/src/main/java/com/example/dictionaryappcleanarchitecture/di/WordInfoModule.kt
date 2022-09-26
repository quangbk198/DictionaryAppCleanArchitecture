package com.example.dictionaryappcleanarchitecture.di

import android.app.Application
import androidx.room.Room
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
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        ).addTypeConverter(GsonParser(Gson()))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}