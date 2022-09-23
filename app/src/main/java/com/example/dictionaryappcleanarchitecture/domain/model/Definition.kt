package com.example.dictionaryappcleanarchitecture.domain.model

data class Definition(
    var antonyms: List<String>? = null,
    var definition: String? = null,
    var example: String? = null,
    var synonyms: List<String>? = null
)
