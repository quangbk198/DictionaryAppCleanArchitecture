package com.example.dictionaryappcleanarchitecture.data.data_source.remote.dto

import com.example.dictionaryappcleanarchitecture.domain.model.Definition

data class DefinitionDto(
    var antonyms: List<String>? = null,
    var definition: String? = null,
    var example: String? = null,
    var synonyms: List<String>? = null
) {
    fun toDefinition(): Definition {
        return Definition(
            antonyms = antonyms,
            definition = definition,
            example = example,
            synonyms = synonyms
        )
    }
}