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
import com.android.multistream.databinding.LoginLayoutBinding
import com.android.multistream.network.twitch.constants.REDIRECT_URI
import com.android.multistream.network.twitch.constants.TWITCH_AUTH_PAGE
import com.android.multistream.ui.main.activities.main_activity.MainActivityViewModel

class LoginFragment : Fragment() {

    lateinit var binding: LoginLayoutBinding

    lateinit var mainActivityViewModel: MainActivityViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        binding = LoginLayoutBinding.inflate(inflater, container, false).apply {
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.let {
                        return if (it.toString().startsWith(REDIRECT_URI)) {
                            mainActivityViewModel.getAndSaveToken(it)
                            findNavController().popBackStack()
                            true
                        } else false
                    }
                    return false
                }
            }
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(TWITCH_AUTH_PAGE)
        }
        return binding.root
    }

}