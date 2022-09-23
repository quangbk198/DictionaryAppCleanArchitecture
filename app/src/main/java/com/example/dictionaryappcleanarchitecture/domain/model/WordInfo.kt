package com.example.dictionaryappcleanarchitecture.domain.model

data class WordInfo(
    var meanings: List<Meaning>? = listOf(),
    var phonetic: String? = "",
    var word: String? = ""
)
