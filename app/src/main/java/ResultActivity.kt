package com.example.pytorch

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ResultActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result) // 결과를 표시할 레이아웃

        resultTextView = findViewById(R.id.tvStatus)
        progressBar = findViewById(R.id.ProgressBar)

        // Intent에서 결과를 받아오기
        val result = intent.getStringExtra("RESULT")
        Log.d("ResultActivity", "Received result: $result") // 로그 추가
        resultTextView.text = "$result"
        updateProgressBar(result)
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
