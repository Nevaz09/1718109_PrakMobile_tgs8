package com.sdssoft.movieview.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdssoft.movieview.api.ApiConfig
import com.sdssoft.movieview.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listMovie = MutableLiveData<List<ResultsItem>>()
    val listMovie: LiveData<List<ResultsItem>> = _listMovie

    private val _listNowPlaying = MutableLiveData<List<NowPlayingResultsItem>>()
    val listNowPlaying: LiveData<List<NowPlayingResultsItem>> = _listNowPlaying

    private val _listTopRated = MutableLiveData<List<TopRatedResultsItem>>()
    val listTopRated: LiveData<List<TopRatedResultsItem>> = _listTopRated

    private val _listUpComing = MutableLiveData<List<UpComingResultsItem>>()
    val listUpComing: LiveData<List<UpComingResultsItem>> = _listUpComing

    private val _listSearch = MutableLiveData<List<SearchResultsItem>>()
    val listSearch: LiveData<List<SearchResultsItem>> = _listSearch

    private val _isLoadingPopular = MutableLiveData<Boolean>()
    val isLoadingPopular: LiveData<Boolean> = _isLoadingPopular


    private val _isLoadingTopRated = MutableLiveData<Boolean>()
    val isLoadingTopRated: LiveData<Boolean> = _isLoadingTopRated

    private val _isLoadingUpComing = MutableLiveData<Boolean>()
    val isLoadingUpComing: LiveData<Boolean> = _isLoadingUpComing

    private val _isLoadingSearch = MutableLiveData<Boolean>()
    val isLoadingSearch: LiveData<Boolean> = _isLoadingSearch


    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    fun getPopular(token: String) {
        _isLoadingPopular.value = true
        val client = ApiConfig.getApiService().getPopular(token)
        client.enqueue(object : Callback<PopularResponse> {
            override fun onResponse(
                call: Call<PopularResponse>,
                response: Response<PopularResponse>
            ) {
                try {
                    _isLoadingPopular.value = false
                    _listMovie.value = response.body()?.results
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call<PopularResponse>, t: Throwable) {
                _isLoadingPopular.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getNowPlaying(token: String) {
        val client = ApiConfig.getApiService().getNowPlaying(token)
        client.enqueue(object : Callback<NowPlayingResponse> {
            override fun onResponse(
                call: Call<NowPlayingResponse>,
                response: Response<NowPlayingResponse>
            ) {
                try {
                    val show = response.body()?.results?.slice(0..4)
                    _listNowPlaying.value = show
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getTopRated(token: String) {
        _isLoadingTopRated.value = true
        val client = ApiConfig.getApiService().getTopRated(token)
        client.enqueue(object : Callback<TopRatedResponse> {
            override fun onResponse(
                call: Call<TopRatedResponse>,
                response: Response<TopRatedResponse>
            ) {
                try {
                    _isLoadingTopRated.value = false
                    _listTopRated.value = response.body()?.results
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call<TopRatedResponse>, t: Throwable) {
                _isLoadingTopRated.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getUpComing(token: String) {
        _isLoadingUpComing.value = true
        val client = ApiConfig.getApiService().getUpComing(token)
        client.enqueue(object : Callback<UpComingResponse> {
            override fun onResponse(
                call: Call<UpComingResponse>,
                response: Response<UpComingResponse>
            ) {
                try {
                    _isLoadingUpComing.value = false
                    _listUpComing.value = response.body()?.results
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call<UpComingResponse>, t: Throwable) {
                _isLoadingUpComing.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getSearch(token: String, query: String) {
        _isLoadingSearch.value = true

        val client = ApiConfig.getApiService().searchMovie(token, query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                try {
                    _isLoadingSearch.value = false
                    _listSearch.value = response.body()?.results
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoadingSearch.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}