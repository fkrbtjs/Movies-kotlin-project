package com.kyuseon.movies.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.kyuseon.movies.databinding.ActivityWebviewBinding

class WebviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** 리스트에서 받아온 link(url)로 이동 */
        val link = intent.getStringExtra("link")
        binding.webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled=true
        }

        /** link값이 null일 수 있으므로 nullsafe */
        binding.webview.loadUrl(link!!)
    }


    /** 브라우저에서 뒤로갈 페이지가 존재할 경우 앱을 종료하지않고 뒤로감 */
    override fun onBackPressed() {
       if (binding.webview.canGoBack()){
           binding.webview.goBack()
       }else{
           finish()
       }
    }
}