package com.lsroar.data.remote.entity

import com.google.gson.annotations.SerializedName

class EventsDAO(
    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<SummaryDAO>,
    @SerializedName("returned") val returned: Int
)
