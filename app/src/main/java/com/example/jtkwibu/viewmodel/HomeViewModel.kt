package com.example.jtkwibu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jtkwibu.data.AnimeEntity
import com.example.jtkwibu.data.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    val animeList = repository.getTopAnime().cachedIn(viewModelScope)

    val bookmarkedAnime = repository.getBookmarkedAnime().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    fun toggleBookmark(animeId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            repository.setBookmark(animeId, isBookmarked)
        }
    }
}
