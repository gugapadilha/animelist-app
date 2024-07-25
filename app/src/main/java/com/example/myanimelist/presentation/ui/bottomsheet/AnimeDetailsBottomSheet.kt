package com.example.myanimelist.presentation.ui.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.myanimelist.domain.model2.Data
import java.text.SimpleDateFormat

@Composable
fun AnimeDetailsBottomSheet(anime: Data) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = anime.images.jpg.imageUrl),
            contentDescription = "Anime picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .width(130.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = anime.title,
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Score: ${anime.score}",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Rating: ${anime.rating}",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "${anime.episodes} episodes",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = anime.synopsis,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = anime.url,
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = if (anime.aired.to.isEmpty()) {
                    "${formatDate(anime.aired.from)} - ongoing"
                } else {
                    "${formatDate(anime.aired.from)} - ${formatDate(anime.aired.to)}"
                },
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start
            )
        }
    }
}

private fun formatDate(date: String): String {
    return if (date.contains("-")) {
        val newDate = date.substring(0, date.lastIndexOf("-"))
        val _date = SimpleDateFormat("yyyy-MM").parse(newDate)
        SimpleDateFormat("MMM-yyyy").format(_date)
    } else {
        date
    }
}
