package com.example.jtkwibu.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.jtkwibu.data.AnimeEntity
import com.example.jtkwibu.R
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jtkwibu.viewmodel.HomeViewModel
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun HomeScreen(
    onAnimeClick: (Int) -> Unit,
    viewModel: com.example.jtkwibu.viewmodel.HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    // Collect the paging items from view Model.
    val animeList = viewModel.animeList.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(items = List(animeList.itemCount) { it }) { index, _ ->
            animeList[index]?.let { anime ->
                Log.d("HomeScreen", "Menampilkan Anime: ${anime.title} (ID: ${anime.malId})")
                NetflixAnimeItem(anime = anime, onClick = { onAnimeClick(anime.malId) })
            }
        }
    }
}

@Composable
fun NetflixAnimeItem(anime: AnimeEntity, onClick: () -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    val isBookmarked = remember { mutableStateOf(anime.isBookmarked) }

    // Pastikan state diperbarui saat data berubah
    LaunchedEffect(anime.isBookmarked) {
        isBookmarked.value = anime.isBookmarked
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = anime.imageUrl ?: "",
                contentDescription = anime.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black)
                        )
                    )
            )

            Text(
                text = anime.title,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(4.dp),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )

            IconButton(
                onClick = {
                    isBookmarked.value = !isBookmarked.value
                    viewModel.toggleBookmark(anime.malId, isBookmarked.value)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked.value) Color.Yellow else Color.White
                )
            }
        }
    }
}

