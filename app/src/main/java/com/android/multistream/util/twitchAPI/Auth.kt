package com.android.multistream.util.twitchAPI
const val scope = "user:edit+user:read:email+analytics:read:games+bits:read+channel:read:subscriptions+clips:edit+user:edit"
const val client_id = "f0dmag7h9n8tj4710up57pjyooo46q"
const val redirect_uri = "schema://com.android.multistream/twitch"
const val twitchAuthPage = "https://id.twitch.tv/oauth2/authorize?client_id=$client_id&redirect_uri=$redirect_uri&response_type=token&scope=$scope"
