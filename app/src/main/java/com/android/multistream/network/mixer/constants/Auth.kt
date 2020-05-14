package com.android.multistream.network.mixer.constants

const val CLIENT_ID = "c1518fb9ce0fc94c43dbdcc4f71b6fc0a30c8782cb46f182"
const val SCOPE = "user:act_as+achievement:view:self+channel:analytics:self+channel:clip:create:self+channel:clip:delete:self+channel:costream:self+channel:deleteBanner:self+channel:details:self+channel:follow:self+chat:connect"
const val CLIENT_SECRET = "7cab80351ec661b21106e27d60af7024b7d59cc4adb3f311684232040f388360"
const val MIXER_AUTH_URL = "https://mixer.com/oauth/authorize?client_id=$CLIENT_ID&response_type=code&redirect_uri=http://localhost:5000&scope=$SCOPE"
const val REDIRECT_URL = "http://localhost:5000/"