package com.iso.multistream.ui.auth_activity.fragments.login

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.iso.multistream.R
import com.iso.multistream.auth.platforms.Platform
import com.iso.multistream.auth.platforms.TwitchPlatform
import com.iso.multistream.databinding.LoginLayoutBinding
import com.iso.multistream.network.mixer.constants.MIXER_AUTH_URL
import com.iso.multistream.network.mixer.constants.MIXER_URL
import com.iso.multistream.network.twitch.constants.REDIRECT_URI
import com.iso.multistream.network.twitch.constants.TWITCH_AUTH_PAGE
import com.iso.multistream.network.twitch.constants.TWITCH_URL
import com.iso.multistream.ui.auth_activity.fragments.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : DialogFragment() {

    lateinit var binding: LoginLayoutBinding
    lateinit var authViewModel: AuthViewModel
    var authJob: Job? = null
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AuthDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel =
            ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        position = arguments?.getInt("position") ?: 0
        val url: String = when (arguments?.getInt("platform")) {
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
                                authJob = lifecycleScope.launch {
                                    authViewModel.getAndSaveToken(
                                        it,
                                        TwitchPlatform::class.java
                                    )
                                    withContext(Dispatchers.Main) {
                                        dialog?.dismiss()
                                    }
                                }
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
            CookieManager.getInstance().removeAllCookies(null)
            webView.settings.apply {
                javaScriptEnabled = true
                databaseEnabled = false
                this.setAppCacheEnabled(false)
                cacheMode = WebSettings.LOAD_NO_CACHE
            }
            webView.loadUrl(url)
        }
        return binding.root
    }


    override fun onDismiss(dialog: DialogInterface) {
        if (authJob != null && !authJob?.isActive!!) {
            super.onDismiss(dialog)
        } else {
            authViewModel.statesLiveData.postValue(Platform.AuthState.Canceled)
            super.onDismiss(dialog)
        }
    }

}