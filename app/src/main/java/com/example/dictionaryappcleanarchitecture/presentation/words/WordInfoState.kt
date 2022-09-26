package com.example.dictionaryappcleanarchitecture.presentation.words

import com.example.dictionaryappcleanarchitecture.domain.model.WordInfo

data class WordInfoState(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)
