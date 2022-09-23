package com.example.dictionaryappcleanarchitecture.data.data_source.remote.dto

data class PhoneticDto(
    var audio: String? = "",
    var license: LicenseDto? = LicenseDto(),
    var sourceUrl: String? = "",
    var text: String? = ""
)