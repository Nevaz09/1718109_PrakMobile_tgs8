package com.sdssoft.movieview.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdssoft.movieview.api.ApiConfig
import com.sdssoft.movieview.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailMovie = MutableLiveData<DetailResponse>()
    val detailMovie: LiveData<DetailResponse> = _detailMovie

    private val _isDataShow = MutableLiveData<Boolean>()
    val isDataShow: LiveData<Boolean> = _isDataShow

    private val _similarMovie = MutableLiveData<List<SimilarResultsItem>>()
    val similarMovie: LiveData<List<SimilarResultsItem>> = _similarMovie

    private val _recommendationResponseMovie = MutableLiveData<List<RecommendationResultsItem>>()
    val recommendationResponseMovie: LiveData<List<RecommendationResultsItem>> =
        _recommendationResponseMovie

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }

    fun getDetail(token: String, id: Int) {
        _isDataShow.value = true
        val client = ApiConfig.getApiService().getDetail(id, token)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                try {
                    _isDataShow.value = false
                    _detailMovie.value = response.body()
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isDataShow.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getSimilar(token: String, id: Int) {
        val client = ApiConfig.getApiService().getSimilar(id, token)
        client.enqueue(object : Callback<SimilarResponse> {
            override fun onResponse(
                call: Call<SimilarResponse>,
                response: Response<SimilarResponse>
            ) {
                try {
                    _similarMovie.value = response.body()?.results
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call<SimilarResponse>, t: Throwable) {
                _isDataShow.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getRecommendation(token: String, id: Int) {
        val client = ApiConfig.getApiService().getRecommendations(id, token)
        client.enqueue(object : Callback<RecommendationResponse> {
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>
            ) {
                try {
                    _recommendationResponseMovie.value = response.body()?.results
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                _isDataShow.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

}