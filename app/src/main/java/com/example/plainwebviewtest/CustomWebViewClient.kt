package com.example.plainwebviewtest

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.webkit.HttpAuthHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.plainwebviewtest.databinding.DialogWebviewAuthBinding

class CustomGenericWebViewClient(val context: Context, val action: (WebViewClientStatus) -> Unit) :
        WebViewClient() {

    override fun onReceivedHttpAuthRequest(
        view: WebView?,
        handler: HttpAuthHandler?,
        host: String?,
        realm: String?
    ) {
        handler?.proceed("mediacorp#@cms","mediacorp\$#!1#")
    }

}

fun Context.showAuthDialog(
    handler: HttpAuthHandler?,
    closeAction: () -> Unit,
    okAction: () -> Unit
) {
    Dialog(this).apply {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = LayoutInflater.from(context)
        val binding = DialogWebviewAuthBinding.inflate(inflater)
        setContentView(binding.root)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        binding.apply {
            tvMessage.text = "Please enter username and password"
            btOk.setOnClickListener {
                handler?.proceed(etUsername.text.toString(), etPassword.text.toString())
                okAction.invoke()
                dismiss()
            }
            btCancel.setOnClickListener {
                closeAction.invoke()
                dismiss()
            }
        }
    }.show()
}

enum class WebViewClientStatus {
    ON_PAGE_STARTED,
    ON_PAGE_FINISHED,
    ON_RECEIVED_ERROR,
    ON_RECEIVED_HTTP_AUTH_REQUEST_CLOSE,
    ON_RECEIVED_HTTP_AUTH_REQUEST_SUCCESS,
    SHOULD_OVERRIDE_URL_LOADING
}