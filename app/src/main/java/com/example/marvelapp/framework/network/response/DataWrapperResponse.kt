package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

class DataWrapperResponse(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("data")
    val data: DataContainerResponse
)