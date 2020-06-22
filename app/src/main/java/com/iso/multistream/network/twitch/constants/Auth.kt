package com.iso.multistream.network.twitch.constants

const val SCOPE =
    "user:edit+user:read:email+analytics:read:games+bits:read+channel:read:subscriptions+clips:edit+user:edit+user_read+user_blocks_read+user_follows_edit+chat:edit+chat:read+whispers:read+channel:moderate+whispers:edit"
const val CLIENT_ID = "f0dmag7h9n8tj4710up57pjyooo46q"
const val CLIENT_SECRET = "ps5aq1pygaf68dz4szsctw7wjz349z"
const val REDIRECT_URI = "schema://com.android.multistream/twitch"
const val TWITCH_AUTH_PAGE =
    "https://id.twitch.tv/oauth2/authorize?client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&response_type=code&scope=$SCOPE"
const val URL = "https://api.twitch.tv/"