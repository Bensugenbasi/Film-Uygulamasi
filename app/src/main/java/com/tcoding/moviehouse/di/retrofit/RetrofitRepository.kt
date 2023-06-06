package com.tcoding.moviehouse.di.retrofit

import androidx.lifecycle.MutableLiveData
import com.tcoding.moviehouse.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retrofitServiceInstance: RetrofitServiceInstance) {


    fun getPopularMovies(pageNumber: String, liveData: MutableLiveData<Movie>) {
        retrofitServiceInstance.getPopularVideos(pageNumber).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                liveData.postValue(null)
            }

        })
    }

    fun getRecentMovies(pageNumber: String, liveData: MutableLiveData<Movie>) {
        retrofitServiceInstance.getRecentVideos(pageNumber).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                liveData.postValue(null)
            }

        })
    }
}