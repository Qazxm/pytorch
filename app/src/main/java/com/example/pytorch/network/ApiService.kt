package com.example.pytorch.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/result") // 수정된 서버 엔드포인트
    fun getPrediction(): Call<PredictionResponse>
}
