package com.example.dictionaryappcleanarchitecture.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryappcleanarchitecture.domain.model.Meaning
import com.example.dictionaryappcleanarchitecture.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    var word: String? = null,
    var phonetic: String? = null,
    var meanings: List<Meaning>,
    @PrimaryKey var id: Int? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            word = word,
            phonetic = phonetic
        )
    }
}
