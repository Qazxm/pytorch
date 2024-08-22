package com.example.pytorch

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pytorch.R
import com.example.pytorch.network.PredictionResponse
import com.example.pytorch.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result) // 결과를 표시할 레이아웃

        resultTextView = findViewById(R.id.tvStatus)
        progressBar = findViewById(R.id.ProgressBar)

        // API 호출을 통해 결과를 직접 가져오기
        fetchResult()
    }

    private fun fetchResult() {
        val apiService = RetrofitClient.apiService
        val call = apiService.getPrediction()

        call.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.prediction
                    Log.d("ResultActivity", "Received result: $prediction")
                    resultTextView.text = prediction
                    updateProgressBar(prediction)
                } else {
                    Toast.makeText(
                        this@ResultActivity,
                        "Error: ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Toast.makeText(this@ResultActivity, "Failure: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateProgressBar(result: String?) {
        Log.d("ResultActivity", "Updating ProgressBar with result: $result")

        progressBar.isIndeterminate = false

        when (result) {
            "danger" -> {
                progressBar.progress = 100
                progressBar.progressTintList =
                    ContextCompat.getColorStateList(this, android.R.color.holo_red_dark)
            }
            "safe" -> {
                progressBar.progress = 100
                progressBar.progressTintList =
                    ContextCompat.getColorStateList(this, R.color.safe_green)
            }
            "error" -> {
                progressBar.progress = 100
                progressBar.progressTintList =
                    ContextCompat.getColorStateList(this, android.R.color.darker_gray)
            }
            "warning" -> {
                progressBar.progress = 50
                progressBar.progressTintList =
                    ContextCompat.getColorStateList(this, android.R.color.holo_orange_light)
            }
            else -> {
                progressBar.progress = 0
                progressBar.progressTintList =
                    ContextCompat.getColorStateList(this, android.R.color.darker_gray)
            }
        }
    }
}
