package com.example.surveyheartassignment.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location (
    @SerializedName("city")
    var city: String,

    @SerializedName("state")
    var state: String

): Serializable