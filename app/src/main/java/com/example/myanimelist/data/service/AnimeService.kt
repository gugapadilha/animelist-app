package com.example.myanimelist.data.service


import com.example.myanimelist.domain.model2.SearchedAnime
import com.example.myanimelist.domain.model2.TopAnime
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {

    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int): TopAnime

    @GET("anime")
    suspend fun getSearchedAnime(@Query("q") queryString: String): SearchedAnime

    companion object {
        private const val BASE_URL = "https://api.jikan.moe/v4/"

        fun create(): AnimeService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(AnimeService::class.java)
        }
    }
}