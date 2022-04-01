package com.lsroar.data.remote.entity

import com.google.gson.annotations.SerializedName

data class CharacterDataWrapperDAO(
    @SerializedName("code") val code: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("copyright") val copyright: String?,
    @SerializedName("attributionText") val attributionText: String?,
    @SerializedName("attributionHTML") val attributionHTML: String?,
    @SerializedName("data") val data: CharacterDataContainerDAO?,
    @SerializedName("etag") val etag: String?
)
