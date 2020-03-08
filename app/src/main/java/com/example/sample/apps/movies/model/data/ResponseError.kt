package com.example.sample.apps.movies.model.data

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @field:SerializedName("status_message")
    val statusMessage: String? = null,

    @field:SerializedName("status_code")
    val statusCode: String? = null
)