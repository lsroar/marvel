package com.lsroar.data.remote.entity

import com.google.gson.annotations.SerializedName

class UrlDAO(
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String
)
