package com.example.myanimelist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.myanimelist.R
import com.example.myanimelist.data.repository.AnimeRepository
import com.example.myanimelist.ui.util.CustomItem

@Composable
fun WatchedScreen(navController : NavHostController) {

    val painter = rememberAsyncImagePainter(R.drawable.watched_screen)

    val animeRepository = AnimeRepository()
    val animeList = animeRepository.getAllData()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Anime Screen",
            contentScale = ContentScale.FillBounds
        )

        animeList.forEach { anime ->
            CustomItem(anime = anime)
        }
    }

}