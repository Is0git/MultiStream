package com.android.multistream.network.twitch

import com.android.multistream.network.twitch.models.new_twitch_api.top_games.Data
import com.android.multistream.network.twitch.models.v5.top_games.TopGamesV5
import com.squareup.moshi.FromJson

class TopGamesTwitchAdapter {
    @FromJson

    fun topGamesTwitch(topGames: TopGamesV5): List<Data>? {
        return topGames.top?.map {
            Data(
                it.game?.box?.large,
                it.game?._id.toString(),
                it.game?.name,
                "twitch",
                it.viewers!!
            )
        }
    }
}