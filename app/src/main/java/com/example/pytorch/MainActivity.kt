package com.example.pytorch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pytorch.network.ApiService
import com.example.pytorch.network.PredictionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var fetchResultButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        resultTextView = findViewById(R.id.resultTextView)
        fetchResultButton = findViewById(R.id.fetchResultButton)

        // Set up button click listener
        fetchResultButton.setOnClickListener {
            fetchResult()
        }
    }

    private fun fetchResult() {
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.getPrediction()

        call.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.prediction
                    resultTextView.text = "Prediction: $prediction"

                    // 결과를 ResultActivity로 전달
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("RESULT", prediction)
                    startActivity(intent)
                    finish() // 현재 액티비티 종료
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failure: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
