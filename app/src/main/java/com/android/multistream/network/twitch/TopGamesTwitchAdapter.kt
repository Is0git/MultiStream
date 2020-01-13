package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.Data
import com.android.multistream.network.twitch.models.v5.TopGamesV5
import com.android.multistream.network.twitch.models.v5.TopItem
import com.squareup.moshi.FromJson

class TopGamesTwitchAdapter {
    @FromJson

    fun topGamesTwitch(topGames: TopGamesV5): List<Data>? {
        return topGames.top?.map { Data(it.game?.box?.large, it.game?.id.toString(), it.game?.name, "twitch", it.viewers!!) }
    }
}