package com.example.jtkwibu.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jtkwibu.viewmodel.BookmarkViewModel
import com.example.jtkwibu.viewmodel.HomeViewModel

@Composable
fun BookmarkScreen(
    onAnimeClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val bookmarkedAnime by viewModel.bookmarkedAnime.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(bookmarkedAnime) { anime ->
            NetflixAnimeItem(anime = anime, onClick = { onAnimeClick(anime.malId) })
        }
    }
}
