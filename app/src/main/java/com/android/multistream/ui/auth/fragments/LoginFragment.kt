package com.android.multistream.ui.auth.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Message
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.multistream.R
import com.android.multistream.auth.platforms.TwitchPlatform
import com.android.multistream.databinding.LoginLayoutBinding
import com.android.multistream.network.mixer.constants.MIXER_AUTH_URL
import com.android.multistream.network.mixer.constants.MIXER_URL
import com.android.multistream.network.twitch.constants.REDIRECT_URI
import com.android.multistream.network.twitch.constants.TWITCH_AUTH_PAGE
import com.android.multistream.network.twitch.constants.TWITCH_URL
import com.android.multistream.ui.intro.fragments.IntroPageTwo
import com.android.multistream.ui.main_activity.MainActivityViewModel
import com.android.multistream.utils.MIXER
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.login_layout.*
import kotlin.properties.Delegates


class LoginFragment : DialogFragment() {

    lateinit var binding: LoginLayoutBinding
    lateinit var mainActivityViewModel: MainActivityViewModel
    var position: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivityViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
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
                                mainActivityViewModel.getAndSaveToken(
                                    it,
                                    TwitchPlatform::class.java
                                )
                                dialog?.dismiss()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(MATCH_PARENT, MATCH_PARENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

    }

}