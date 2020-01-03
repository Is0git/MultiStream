package com.android.multistream

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.android.multistream.util.twitchAPI.twitchAuthPage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(Intent.ACTION_VIEW, twitchAuthPage.toUri())
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val uri = intent.data?.getQueryParameter("code")
    }
}
