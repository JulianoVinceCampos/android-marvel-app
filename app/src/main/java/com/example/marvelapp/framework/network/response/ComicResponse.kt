package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class ComicResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)
