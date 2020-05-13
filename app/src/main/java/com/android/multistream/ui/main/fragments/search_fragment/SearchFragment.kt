package com.android.multistream.ui.main.fragments.search_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.multistream.R
import com.android.multistream.utils.MIXER
import com.android.multistream.utils.TWITCH
import com.multistream.multistreamsearchview.data_source.DataSource
import com.multistream.multistreamsearchview.databinding.SearchLayoutBinding
import com.multistream.multistreamsearchview.filter.FilterSelection
import com.multistream.multistreamsearchview.search_view.LatestSearchedAdapter
import com.multistream.multistreamsearchview.search_view.SearchViewLayout


class SearchFragment : Fragment() {
    private lateinit var binding: SearchLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchLayoutBinding.inflate(inflater, container, false)
        val sourceDownloader = DataSource.Builder()
            .setIconDrawable(R.drawable.twitch_small_logo)
            .setName("Twitch")
            .build(SearchViewLayout.SearchData::class.java) {
                getData(TWITCH, SearchViewLayout.GAMES)
            }


        val sourceDownloader2 = DataSource.Builder()
            .setIconDrawable(R.drawable.mixer_small_logo)
            .setName("Mixer")
            .build(SearchViewLayout.SearchData::class.java) {
                getData2(
                    TWITCH,
                    SearchViewLayout.GAMES
                )
            }
        binding.searchLayout.addSourceDownloader(sourceDownloader)

        binding.searchLayout.latestSearchesAdapter.setRecentSearchData(
            listOf(
                LatestSearchedAdapter.LatestSearch(
                    "VALORANT",
                    1,
                    "GAMES",
                    5L,
                    "TODAY",
                    0,
                    R.drawable.twitch_small_logo,
                    "https://static-cdn.jtvnw.net/ttv-boxart/VALORANT-285x380.jpg"
                ),
                LatestSearchedAdapter.LatestSearch(
                    "Call of Duty: Modern Warfare",
                    1,
                    "GAMES",
                    5L,
                    "YESTERDAY",
                    0,
                    R.drawable.twitch_small_logo,
                    "https://static-cdn.jtvnw.net/ttv-boxart/Call%20of%20Duty:%20Modern%20Warfare-144x192.jpg"
                ),
                LatestSearchedAdapter.LatestSearch(
                    "xTheSolutionTV",
                    1,
                    "CHANNELS",
                    5L,
                    "YESTERDAY",
                    0,
                    R.drawable.mixer_small_logo,
                    "https://static-cdn.jtvnw.net/ttv-boxart/Call%20of%20Duty:%20Modern%20Warfare-144x192.jpg"
                ),
                LatestSearchedAdapter.LatestSearch(
                    "VALORANT",
                    1,
                    "GAMES",
                    5L,
                    "TODAY",
                    0,
                    R.drawable.twitch_small_logo,
                    "https://static-cdn.jtvnw.net/ttv-boxart/VALORANT-285x380.jpg"
                ),
                LatestSearchedAdapter.LatestSearch(
                    "Call of Duty: Modern Warfare",
                    1,
                    "GAMES",
                    5L,
                    "YESTERDAY",
                    0,
                    R.drawable.twitch_small_logo,
                    "https://static-cdn.jtvnw.net/ttv-boxart/Call%20of%20Duty:%20Modern%20Warfare-144x192.jpg"
                ),
                LatestSearchedAdapter.LatestSearch(
                    "xTheSolutionTV",
                    1,
                    "CHANNELS",
                    5L,
                    "YESTERDAY",
                    0,
                    R.drawable.mixer_small_logo,
                    "https://static-cdn.jtvnw.net/ttv-boxart/Call%20of%20Duty:%20Modern%20Warfare-144x192.jpg"
                ),
                LatestSearchedAdapter.LatestSearch(
                    "VALORANT",
                    1,
                    "GAMES",
                    5L,
                    "TODAY",
                    0,
                    R.drawable.twitch_small_logo,
                    "https://static-cdn.jtvnw.net/ttv-boxart/VALORANT-285x380.jpg"
                ),
                LatestSearchedAdapter.LatestSearch(
                    "Call of Duty: Modern Warfare",
                    1,
                    "GAMES",
                    5L,
                    "YESTERDAY",
                    0,
                    R.drawable.twitch_small_logo,
                    "https://static-cdn.jtvnw.net/ttv-boxart/Call%20of%20Duty:%20Modern%20Warfare-144x192.jpg"
                ),
                LatestSearchedAdapter.LatestSearch(
                    "xTheSolutionTV",
                    1,
                    "CHANNELS",
                    5L,
                    "YESTERDAY",
                    0,
                    R.drawable.mixer_small_logo,
                    "https://static-cdn.jtvnw.net/ttv-boxart/Call%20of%20Duty:%20Modern%20Warfare-144x192.jpg"
                )
            )
        )
        binding.searchLayout.addSourceDownloader(sourceDownloader2)
        val platform = FilterSelection.Builder()
            .setFilterSelectionName("Twitch")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.platform == TWITCH } }
        val platform2 = FilterSelection.Builder()
            .setFilterSelectionName("Mixer")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.platform == MIXER } }
        val listOfSelectionData = listOf(platform, platform2)
        val category = FilterSelection.Builder()
            .setFilterSelectionName("Games")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.category == SearchViewLayout.GAMES } }
        val category2 = FilterSelection.Builder()
            .setFilterSelectionName("Channels")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.category == SearchViewLayout.CHANNELS } }
        val category3 = FilterSelection.Builder()
            .setFilterSelectionName("Streams")
            .build(SearchViewLayout.SearchData::class.java) { list -> list.filter { it.category == SearchViewLayout.STREAMS } }
        val listOfSelectionData1 = listOf(category, category2, category3)
        binding.searchLayout.addFilter("Choose category", listOfSelectionData1, false, true)
        binding.searchLayout.initSearchView()
        return binding.root
    }

    fun getData(category: Int, platform: Int): List<SearchViewLayout.SearchData> {
        return listOf(
            SearchViewLayout.SearchData(
                "FORTNITE",
                "https://static-cdn.jtvnw.net/ttv-boxart/Fortnite-285x380.jpg",
                SearchViewLayout.GAMES,
                R.string.games_category,
                TWITCH,
                R.drawable.twitch_small_logo
            ),
            SearchViewLayout.SearchData(
                "Andy",
                "https://static-cdn.jtvnw.net/previews-ttv/live_user_thekairi78-440x248.jpg",
                SearchViewLayout.STREAMS,
                R.string.streams_category,
                TWITCH,
                R.drawable.twitch_small_logo
            ),
            SearchViewLayout.SearchData(
                "Call of Duty: Modern Warfare",
                "https://static-cdn.jtvnw.net/ttv-boxart/Call%20of%20Duty:%20Modern%20Warfare-285x380.jpg",
                SearchViewLayout.GAMES,
                R.string.games_category,
                TWITCH,
                R.drawable.twitch_small_logo
            ),
            SearchViewLayout.SearchData(
                "League of legends",
                "https://static-cdn.jtvnw.net/ttv-boxart/League%20of%20Legends-285x380.jpg",
                SearchViewLayout.CHANNELS,
                R.string.channels_category,
                TWITCH,
                R.drawable.twitch_small_logo
            )
        )
    }

    fun getData2(category: Int, platform: Int): List<SearchViewLayout.SearchData> {
        return listOf(
            SearchViewLayout.SearchData(
                "Valorant",
                "https://static-cdn.jtvnw.net/jtv_user_pictures/774f1524-f873-4e60-b767-b17653a74ab5-profile_image-70x70.png",
                SearchViewLayout.GAMES,
                R.string.games_category,
                MIXER,
                R.drawable.mixer_small_logo
            ),
            SearchViewLayout.SearchData(
                "Fortnite",
                "https://static-cdn.jtvnw.net/jtv_user_pictures/774f1524-f873-4e60-b767-b17653a74ab5-profile_image-70x70.png",
                SearchViewLayout.GAMES,
                R.string.games_category,
                MIXER,
                R.drawable.mixer_small_logo
            ),
            SearchViewLayout.SearchData(
                "Drd",
                "https://static-cdn.jtvnw.net/jtv_user_pictures/cdnthe3rd-profile_image-8246eb11f7f4b215-70x70.jpeg",
                SearchViewLayout.CHANNELS,
                R.string.channels_category,
                MIXER,
                R.drawable.mixer_small_logo
            ),
            SearchViewLayout.SearchData(
                "Shr",
                "https://static-cdn.jtvnw.net/jtv_user_pictures/bobross-profile_image-0b9dd167a9bb16b5-70x70.jpeg",
                SearchViewLayout.CHANNELS,
                R.string.channels_category,
                MIXER,
                R.drawable.mixer_small_logo
            )
        )
    }
}