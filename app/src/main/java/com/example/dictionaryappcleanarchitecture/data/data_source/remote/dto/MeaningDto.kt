package com.example.dictionaryappcleanarchitecture.data.data_source.remote.dto

import com.example.dictionaryappcleanarchitecture.domain.model.Meaning

data class MeaningDto(
    var definitions: List<DefinitionDto>? = null,
    var partOfSpeech: String? = null,
) {
    fun toMeaning(): Meaning {
        return Meaning(
            definitions = definitions?.map { it.toDefinition() },
            partOfSpeech = partOfSpeech
        )
    }
}