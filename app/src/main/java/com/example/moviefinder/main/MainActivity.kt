package com.example.moviefinder.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefinder.R
import com.example.moviefinder.favorites.FavoriteMovies
import com.example.moviefinder.model.*
import com.example.moviefinder.movie_details.MovieDetails
import com.example.moviefinder.networking.BackendFactory
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { MovieAdapter(::onMovieClicked) }
    private val interactor = BackendFactory.getInteractor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAdapter()
        getAllMovies()

        id_imageButton.setOnClickListener {
            searchAllMovies()
        }
    }

    fun onMovieClicked(movie: Movies) {
        val myIntent = Intent(this, MovieDetails::class.java)

        val overview = movie.overview
        val vote = movie.voteavarage.toString()
        val releaseDate = movie.releaseDate.toString()
        val language = movie.original_language
        val title = movie.title
        val poster = movie.posterPath

        myIntent.putExtra("KEY_EXTRA_ID", movie.id)
        myIntent.putExtra("KEY_EXTRA", overview)
        myIntent.putExtra("KEY_EXTRA1", vote)
        myIntent.putExtra("KEY_EXTRA2", releaseDate)
        myIntent.putExtra("KEY_EXTRA3", language)
        myIntent.putExtra("KEY_EXTRA4", title)
        myIntent.putExtra("KEY_EXTRA5", poster)

        startActivity(myIntent)

        Log.d("taag", "* * * You clicked on movie: '" + movie.title + "'")
    }

    private fun setUpAdapter() {
        id_recyclerView.layoutManager = LinearLayoutManager(this)
        id_recyclerView.adapter = adapter
    }

    //all movies
    private fun getAllMovies() {
        interactor.getMovie(getCallback())
    }

    private fun getCallback(): Callback<MovieResponse> = object : Callback<MovieResponse> {

        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("taag", t.message.toString())
        }

        override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
            if (response.isSuccessful) {
                handleOkResponse(response)
            }
        }
    }

    private fun handleOkResponse(response: Response<MovieResponse>) {
        val movies: MutableList<Movies> = mutableListOf()
        val data = response.body()!!.results

        for (movie in data) {
            movies.add(
                Movies(
                    movie.movieId,
                    movie.title ?: "",
                    movie.name ?: "",
                    movie.releaseDate,
                    movie.posterPath,
                    movie.originalLanguage,
                    movie.overview,
                    movie.voteAverage
                )
            )
        }
        adapter.setData(movies)
    }

    // search movies
    private fun searchAllMovies() {
        interactor.searchMovies(getSearchCallback(), shownameInput.text.toString())
        if(shownameInput.text.toString().isEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            finish()
            overridePendingTransition( 0, 0)
            startActivity(intent)
            overridePendingTransition( 0, 0)        }
    }

    private fun getSearchCallback(): Callback<MovieResponse> = object : Callback<MovieResponse> {
        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("taag", t.message.toString())
        }

        override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
            if (response.isSuccessful) {
                Log.d("taag", response.body().toString())
                handleOkResponse(response)
            }
        }
    }

    //options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.Home -> {
                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                finish()
                overridePendingTransition( 0, 0)
                startActivity(intent)
                overridePendingTransition( 0, 0)
                return super.onOptionsItemSelected(item)
            }
            R.id.Fav -> {
                Toast.makeText(this, "Favorites selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, FavoriteMovies::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}












