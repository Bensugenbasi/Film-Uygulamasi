package com.tcoding.moviehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tcoding.moviehouse.adapter.MovieAdapter
import com.tcoding.moviehouse.adapter.RecentMovieAdapter
import com.tcoding.moviehouse.databinding.ActivityMainBinding
import com.tcoding.moviehouse.models.Movie
import com.tcoding.moviehouse.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var movieAdapter : MovieAdapter
    lateinit var recentMovieAdapter : RecentMovieAdapter

    val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(HomePageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        observerListener()

        fetchData()

    }

    fun observerListener() {

        viewModel.getObserverLiveData(true).observe(this, object: Observer<Movie>{
            override fun onChanged(t: Movie?) {
                if(t != null) {
                    movieAdapter.setList(t.results)
                }
            }

        })
        viewModel.getObserverLiveData(false).observe(this, object: Observer<Movie>{
            override fun onChanged(t: Movie?) {
                if(t != null) {
                    recentMovieAdapter.setList(t.results)
                }
            }

        })
    }

    fun initRecyclerView() {
        var lmHorizontal = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        var lmVertical = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        binding.rvPopularMovies.layoutManager = lmHorizontal
        binding.rvRecentMovies.layoutManager = lmVertical

        movieAdapter = MovieAdapter()
        recentMovieAdapter = RecentMovieAdapter()

        binding.rvRecentMovies.adapter = recentMovieAdapter
        binding.rvPopularMovies.adapter = movieAdapter
    }

    fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            val job1 : Deferred<Unit> = async {
                viewModel.loadData("1", true)
            }
            val job2 : Deferred<Unit> = async {
                viewModel.loadData("1", false)
            }

            job1.await()
            job2.await()
        }
    }

}