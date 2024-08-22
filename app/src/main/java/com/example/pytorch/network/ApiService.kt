package com.example.pytorch.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/result") // 결과 JSON 파일의 엔드포인트
    fun getPrediction(): Call<PredictionResponse>
}
