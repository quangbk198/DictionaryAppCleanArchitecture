package com.example.dictionaryappcleanarchitecture.domain.model

data class Meaning(
    var definitions: List<Definition>? = listOf(),
    var partOfSpeech: String? = null
)