package com.android.multistream.network.mixer.constants

const val CLIENT_ID = "c1518fb9ce0fc94c43dbdcc4f71b6fc0a30c8782cb46f182"
const val SCOPE =
    "user:act_as+achievement:view:self+channel:analytics:self+channel:clip:create:self+channel:clip:delete:self+channel:costream:self+channel:deleteBanner:self+channel:details:self+channel:follow:self+chat:connect"
const val CLIENT_SECRET = "813771cca4532ffb2885a7204b395b6dae5296a4edad1d8902cdf388e0e88a9e"
const val MIXER_AUTH_URL =
    "https://mixer.com/oauth/authorize?client_id=$CLIENT_ID&response_type=code&redirect_uri=http://localhost:5000&scope=$SCOPE"
const val REDIRECT_URL = "http://localhost:5000"