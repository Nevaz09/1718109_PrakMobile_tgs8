package com.sdssoft.movieview.api

import com.sdssoft.movieview.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopular(
        @Query("api_key") token: String
    ): Call<PopularResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("api_key") token: String): Call<NowPlayingResponse>

    @GET("movie/top_rated")
    fun getTopRated(@Query("api_key") token: String): Call<TopRatedResponse>

    @GET("movie/upcoming")
    fun getUpComing(@Query("api_key") token: String): Call<UpComingResponse>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") token: String,
        @Query("query") query: String
    ): Call<SearchResponse>

    @GET("movie/{movie_id}")
    fun getDetail(@Path("movie_id") id: Int, @Query("api_key") token: String): Call<DetailResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilar(
        @Path("movie_id") id: Int,
        @Query("api_key") token: String
    ): Call<SimilarResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendations(
        @Path("movie_id") id: Int,
        @Query("api_key") token: String
    ): Call<RecommendationResponse>
}