package com.example.dictionaryappcleanarchitecture.domain.model

data class Meaning(
    val definitions: List<Definition>? = listOf(),
    val partOfSpeech: String? = null
)