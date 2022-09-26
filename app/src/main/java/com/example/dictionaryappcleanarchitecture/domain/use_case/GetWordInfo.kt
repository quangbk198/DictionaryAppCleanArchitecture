package com.example.dictionaryappcleanarchitecture.domain.use_case

import com.example.dictionaryappcleanarchitecture.core.util.Resource
import com.example.dictionaryappcleanarchitecture.domain.model.WordInfo
import com.example.dictionaryappcleanarchitecture.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo(
    private val repository: WordInfoRepository
) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow {  }
        }

        return repository.getWordInfo(word)
    }

}