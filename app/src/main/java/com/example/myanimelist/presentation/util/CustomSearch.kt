package com.example.myanimelist.presentation.util

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.myanimelist.domain.model2.Data
import com.example.myanimelist.presentation.ui.AnimatedBorderCard

@Composable
fun SearchBox(
    onSearch: (String) -> Unit,
    previousSearches: List<String>
) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var suggestions by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, start = 10.dp, end = 10.dp, bottom = 10.dp),

            ) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    suggestions = previousSearches.filter { search ->
                        search.startsWith(text, ignoreCase = true)
                    }
                },
                placeholder = { Text(text = "eg: Fate Zero") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSearch(text)
                        keyboardController?.hide()
                        text = ""
                        suggestions = emptyList()
                    }

                )
            )
            AnimeCard(onSearchClick = {
                onSearch(text)
                keyboardController?.hide()
                text = ""
                suggestions = emptyList()
            })
        }

        // Display suggestions
        LazyColumn {
            items(suggestions) { suggestion ->
                Text(
                    text = suggestion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .clickable {
                            text = suggestion
                            onSearch(suggestion)
                            keyboardController?.hide()
                            suggestions = emptyList()
                        }
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

// Helper functions to save and load search queries
fun saveSearchQuery(query: String, context: Context) {
    // Aqui você pode salvar a pesquisa em SharedPreferences, uma base de dados local, etc.
    // Exemplo simples usando SharedPreferences:
    val sharedPref = context.getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
    val savedQueries = sharedPref.getStringSet("queries", mutableSetOf()) ?: mutableSetOf()
    savedQueries.add(query)
    sharedPref.edit().putStringSet("queries", savedQueries).apply()
}

fun loadPreviousSearches(context: Context): List<String> {
    // Carregar pesquisas anteriores de SharedPreferences, uma base de dados local, etc.
    // Exemplo simples usando SharedPreferences:
    val sharedPref = context.getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
    val savedQueries = sharedPref.getStringSet("queries", mutableSetOf()) ?: mutableSetOf()
    return savedQueries.toList()
}
@Composable
fun AnimeItem(anime: Data) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = rememberImagePainter(data = anime.images.jpg.imageUrl),
                contentDescription = "Anime picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(4.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = anime.title,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}

@Composable
fun AnimeCard(onSearchClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .padding(start = 10.dp)
    ) {
        AnimatedBorderCard(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(24.dp),
            borderWidth = 3.dp,
            gradient = Brush.linearGradient(
                listOf(
                    Color(android.graphics.Color.rgb(117, 27, 16)),
                    Color(
                        android.graphics.Color.rgb(219, 136, 81)
                    ))
            ),
            onCardClick = { onSearchClick() }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                text = "Search",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
