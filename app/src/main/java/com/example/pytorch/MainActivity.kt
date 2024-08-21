package com.example.pytorch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview) // 웹뷰가 있는 레이아웃

        webView = findViewById(R.id.webView)

        // WebView 설정
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                // 페이지 로드 완료 후 자동으로 결과를 처리
                view.evaluateJavascript(
                    "(function() { return document.getElementById('result').innerText; })();"
                ) { result ->
                    Log.d("MainActivity", "Evaluated JavaScript result: $result")
                    if (result.isNotEmpty()) {
                        processResult(result.trim('"')) // 결과에서 불필요한 따옴표 제거
                    }
                }
            }
        }

        // JavaScript Interface 설정
        webView.addJavascriptInterface(JavaScriptInterface(), "AndroidInterface")

        // 웹 페이지 로드
        webView.loadUrl("http://192.168.123.111:8000/") // 로컬 IP 주소와 포트
    }

    private fun processResult(result: String) {
        Log.d("MainActivity", "Processing result: $result") // 로그 추가
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("RESULT", result)
        startActivity(intent)
        finish() // 현재 액티비티 종료
    }

    // JavaScript Interface 클래스 정의
    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun showResult(result: String) {
            Log.d("MainActivity", "JavaScript Interface received result: $result")
            processResult(result)
        }
    }
}
