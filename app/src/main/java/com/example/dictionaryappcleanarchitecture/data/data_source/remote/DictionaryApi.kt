package com.example.dictionaryappcleanarchitecture.data.data_source.remote

import com.example.dictionaryappcleanarchitecture.data.data_source.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfo(
        @Path("word") word: String
    ): List<WordInfoDto>
}