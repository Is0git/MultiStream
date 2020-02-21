package com.android.multistream.utils.twitchAPI
const val scope = "user:edit+user:read:email+analytics:read:games+bits:read+channel:read:subscriptions+clips:edit+user:edit"
const val CLIENT_ID = "f0dmag7h9n8tj4710up57pjyooo46q"
const val CLIENT_SECRET = "e9i2l46agsjcl1d2ycmxxfs5ho5pqj"
const val REDIRECT_URI = "schema://com.android.multistream/twitch"
const val twitchAuthPage = "https://id.twitch.tv/oauth2/authorize?client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&response_type=code&scope=$scope"
const val URL = "https://api.twitch.tv/"