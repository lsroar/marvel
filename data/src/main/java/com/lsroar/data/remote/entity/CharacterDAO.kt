package com.lsroar.data.remote.entity

import com.google.gson.annotations.SerializedName

data class CharacterDAO(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("modified") val modified: String,
    @SerializedName("thumbnail") val thumbnail: ThumbnailDAO?,
    @SerializedName("resourceURI") val resourceURI: String?,
    @SerializedName("comics") val comics: ComicsDAO?,
    @SerializedName("series") val series: SeriesDAO?,
    @SerializedName("stories") val stories: StoriesDAO?,
    @SerializedName("events") val events: EventsDAO?,
    @SerializedName("urls") val urls: List<UrlDAO?>
)
