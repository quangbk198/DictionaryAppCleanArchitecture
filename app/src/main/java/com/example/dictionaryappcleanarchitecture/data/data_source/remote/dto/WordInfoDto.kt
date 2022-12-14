package com.example.dictionaryappcleanarchitecture.data.data_source.remote.dto

import com.example.dictionaryappcleanarchitecture.data.data_source.local.entity.WordInfoEntity

data class WordInfoDto(
    var license: LicenseDto? = LicenseDto(),
    var meanings: List<MeaningDto>? = listOf(),
    var phonetic: String? = "",
    var phonetics: List<PhoneticDto>? = listOf(),
    var sourceUrls: List<String>? = listOf(),
    var word: String? = ""
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings?.map { it.toMeaning() }!!,
            phonetic = phonetic,
            word = word
        )
    }
}