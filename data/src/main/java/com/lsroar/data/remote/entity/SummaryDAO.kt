package com.lsroar.data.remote.entity

import com.google.gson.annotations.SerializedName

data class SummaryDAO(
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("type") val string: String?
)
