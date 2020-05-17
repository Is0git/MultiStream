package com.android.multistream.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.databinding.LoginLayoutBinding
import com.android.multistream.network.mixer.constants.MIXER_AUTH_URL
import com.android.multistream.network.mixer.constants.MIXER_URL
import com.android.multistream.network.twitch.constants.REDIRECT_URI
import com.android.multistream.network.twitch.constants.TWITCH_AUTH_PAGE
import com.android.multistream.network.twitch.constants.TWITCH_URL
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel

class LoginFragment : Fragment() {

    lateinit var binding: LoginLayoutBinding
    val args: LoginFragmentArgs by navArgs()
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        var url: String = when (args.platform) {
            0 -> TWITCH_AUTH_PAGE
            1 -> MIXER_AUTH_URL
            else -> TWITCH_URL
        }

        binding = LoginLayoutBinding.inflate(inflater, container, false).apply {
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.let {
                        return when {
                            it.toString().startsWith(REDIRECT_URI) -> {
                                mainActivityViewModel.getAndSaveToken(
                                    it,
                                    TwitchPlatform::class.java
                                )
                                findNavController().popBackStack()
                                true
                            }
                            it.toString().contains(MIXER_URL) -> {
                                true
                            }
                            else -> false
                        }
                    }
                    return false
                }
            }
            webView.settings.apply {
                javaScriptEnabled = true
            }
            webView.loadUrl(url)
        }
        return binding.root
    }

}