package com.android.multistream.network.mixer

import com.android.multistream.network.mixer.models.MixerTopGames
import com.android.multistream.network.twitch.models.Data
import com.squareup.moshi.FromJson


class TopGamesMixerAdapter {

    @FromJson
    fun fromJson(
      topGames: MixerTopGames
    ): Data {
        return Data(topGames.backgroundUrl, topGames.id.toString(), topGames.name, "mixer")
    }
}