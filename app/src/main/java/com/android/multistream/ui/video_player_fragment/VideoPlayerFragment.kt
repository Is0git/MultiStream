package com.android.multistream.ui.video_player_fragment

import android.content.ContentResolver
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.android.multistream.R
import com.android.multistream.databinding.StreamPlayerFragmentBinding
import java.util.jar.Manifest

class VideoPlayerFragment : Fragment(){
    lateinit var binding: StreamPlayerFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StreamPlayerFragmentBinding.inflate(inflater, container, false)
        val resourceId = R.raw.test
        val uri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(resourceId))
            .appendPath(resources.getResourceTypeName(resourceId))
            .appendPath(resources.getResourceEntryName(resourceId))
            .build()
        binding.videoPlayer.apply {
            setVideoURI(uri)
            start()
        }

        return binding.root
    }
}