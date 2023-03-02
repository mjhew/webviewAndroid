package com.example.plainwebviewtest

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.webkit.HttpAuthHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.plainwebviewtest.databinding.DialogWebviewAuthBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)

        webView.webViewClient = CustomGenericWebViewClient(applicationContext) {
            when(it){
                WebViewClientStatus.ON_PAGE_STARTED -> showLoader()
                WebViewClientStatus.ON_PAGE_FINISHED -> hideLoader()
                WebViewClientStatus.ON_RECEIVED_ERROR -> hideLoader()
                WebViewClientStatus.ON_RECEIVED_HTTP_AUTH_REQUEST_CLOSE -> {
                    hideLoader()
                }
                WebViewClientStatus.ON_RECEIVED_HTTP_AUTH_REQUEST_SUCCESS -> showLoader()
                else -> {}
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.userAgentString = USER_AGENT+"Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36"

        webView.loadUrl("https://stg-www.8world.com/budget-2023")
    }

    private fun hideLoader() {}
    private fun showLoader() {}

    companion object {
        const val USER_AGENT= "8world-mobileapp-Android/3.1.7-staging-156"
    }
}