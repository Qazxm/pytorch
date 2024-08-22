package com.example.pytorch.network
import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("prediction") val prediction: String
)
