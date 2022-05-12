package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.domStorageEnabled = true

        val self = this;

        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(
                view: WebView,
                url: String
            ) {
                view.loadUrl("javascript: localStorage.setItem('authToken', 'some-token-app');")
            }

            override fun onLoadResource(view: WebView, url: String) {
                println("cur url:")
                println(url)
                println("condition:")
                println(url.contains("auth/login"))
                if (url.contains("auth/login")) {
                    view.stopLoading()
                    val intent = Intent(self, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        myWebView.loadUrl("http://192.168.0.17:3000/")

//        val injection =
//            "<html><head><script type='javascript'>localStorage.setItem('authToken', 'some-token-app');</script></head><body>123jdsjds</body></html>"
//        myWebView.loadData(injection, "text/html", null)
    }
}