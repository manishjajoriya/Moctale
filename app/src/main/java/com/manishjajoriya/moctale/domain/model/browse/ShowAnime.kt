package com.manishjajoriya.moctale.domain.model.browse

import com.manishjajoriya.moctale.R

enum class ShowAnime(val value: String?, val displayName: String, val icon: Int) {
  SHOW_ANIME(null, "Show Anime", R.drawable.ic_show_anime_icon),
  HIDE_ANIME("NO", "Hide Anime", R.drawable.ic_hide_anime_icon),
  ONLY_ANIME("ONLY", "Only Anime", R.drawable.ic_only_anime_icon),
}