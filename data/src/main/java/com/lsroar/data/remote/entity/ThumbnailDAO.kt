package com.lsroar.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ThumbnailDAO(
    @SerializedName("path") val path: String?,
    @SerializedName("extension") val extension: String?
)
