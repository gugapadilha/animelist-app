package com.example.myanimelist.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.myanimelist.R
import com.example.myanimelist.domain.model2.Data
import com.example.myanimelist.presentation.ui.bottomsheet.RemoveAnimeBottomSheet
import com.example.myanimelist.presentation.ui.viewmodel.SearchViewModel
import com.example.myanimelist.presentation.util.AnimeItem
import com.example.myanimelist.presentation.util.SearchBox
import com.example.myanimelist.presentation.util.preferences.FavoriteAnimeStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteScreen(navController: NavHostController) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(R.drawable.favorite_screen)
    val animeList = remember { mutableStateListOf<Data>() }
    val listState = rememberLazyListState()
    var selectedAnime by remember { mutableStateOf<Data?>(null) }
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var previousSearches by remember { mutableStateOf<List<String>>(listOf()) }
    val searchViewModel: SearchViewModel = viewModel()

    LaunchedEffect(Unit) {
        FavoriteAnimeStore.loadFavoriteAnimes(context)
        animeList.addAll(FavoriteAnimeStore.favoriteAnimeList)
        previousSearches = searchViewModel.loadPreviousSearchesFromStorage(context)
    }

    fun performSearch(query: String) {
        if (query.isBlank()) {
            animeList.clear()
            animeList.addAll(FavoriteAnimeStore.favoriteAnimeList)
        } else {
            animeList.clear()
            animeList.addAll(
                FavoriteAnimeStore.favoriteAnimeList.filter {
                    it.title.contains(query, ignoreCase = true)
                }
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedBorderCard(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 10.dp, end = 10.dp),
                shape = RoundedCornerShape(4.dp),
                borderWidth = 3.dp,
                gradient = Brush.linearGradient(
                    listOf(
                        Color(android.graphics.Color.rgb(117, 27, 16)),
                        Color(android.graphics.Color.rgb(219, 136, 81))
                    )
                )
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Here you can see all your favorite animes",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            SearchBox(
                onSearch = { query ->
                    performSearch(query)
                    searchViewModel.saveSearchQueryToStorage(query, context)
                    previousSearches = searchViewModel.loadPreviousSearchesFromStorage(context)
                },
                previousSearches = previousSearches
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                state = listState
            ) {
                items(animeList.chunked(3)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowItems.forEach { anime ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .clickable {
                                        selectedAnime = anime
                                        coroutineScope.launch {
                                            bottomSheetState.show()
                                        }
                                    }
                            ) {
                                AnimeItem(anime = anime)
                            }
                        }
                        repeat(3 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f).padding(4.dp))
                        }
                    }
                }
            }
        }
    }

    selectedAnime?.let {
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetContent = {
                RemoveAnimeBottomSheet(
                    anime = it,
                    onDismiss = { selectedAnime = null },
                    removeFromFavorite = true,
                    animeList = animeList
                )
            }
        ) {}
    }
}
