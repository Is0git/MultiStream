package com.android.multistream.ui.main.fragments.video_player_fragment

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import com.android.multistream.R
import com.android.multistream.databinding.StreamPlayerFragmentBinding

class VideoPlayerFragment : Fragment(){
    lateinit var binding: StreamPlayerFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StreamPlayerFragmentBinding.inflate(inflater, container, false)
//        val resourceId = R.raw.test
//        val uri = Uri.Builder()
//            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
//            .authority(resources.getResourcePackageName(resourceId))
//            .appendPath(resources.getResourceTypeName(resourceId))
//            .appendPath(resources.getResourceEntryName(resourceId))
//            .build()
//            val metrics = resources.displayMetrics
//        binding.webViewPlayer.apply {
//
//            settings.javaScriptEnabled = true
//            settings.javaScriptCanOpenWindowsAutomatically = true
//            settings.pluginState = WebSettings.PluginState.ON_DEMAND
//            settings.mediaPlaybackRequiresUserGesture = false
//            loadData("<iframe\n" +
//                    "    src=\"https://player.twitch.tv/?channel=dakotaz\"\n" +
//                    "    height=\"${layoutParams.height}\"\n" +
//                    "    width=\"${layoutParams.width}\"\n" +
//                    "    frameborder=\"0\"\n" +
//                    "    scrolling=\"no\"\n" +
//                    "    allowfullscreen=\"true\">\n" +
//                    "</iframe>", "text/html", "utf-8")
//        }
        return binding.root
    }
}