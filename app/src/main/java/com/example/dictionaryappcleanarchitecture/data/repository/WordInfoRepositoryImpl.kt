package com.example.dictionaryappcleanarchitecture.data.repository

import com.example.dictionaryappcleanarchitecture.core.util.Resource
import com.example.dictionaryappcleanarchitecture.data.data_source.local.WordInfoDao
import com.example.dictionaryappcleanarchitecture.data.data_source.remote.DictionaryApi
import com.example.dictionaryappcleanarchitecture.domain.model.WordInfo
import com.example.dictionaryappcleanarchitecture.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
): WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfos))

        try {
            // Get list word from api
            val remoteWordInfo = api.getWordInfo(word)

            // Delete old data in local database
            remoteWordInfo.map { it.word.toString() }.let { listWord ->
                dao.deleteWordInfos(listWord)
            }

            // Insert latest data into local database
            dao.insertWordInfos(remoteWordInfo.map { it.toWordInfoEntity() })

        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = wordInfos
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection",
                data = wordInfos
            ))
        }

        // Emit latest data from local database to update UI
        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}