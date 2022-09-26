package com.example.dictionaryappcleanarchitecture.domain.repository

import com.example.dictionaryappcleanarchitecture.core.util.Resource
import com.example.dictionaryappcleanarchitecture.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>

}