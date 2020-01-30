package com.example.moviefinder.favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefinder.main.MainActivity
import com.example.moviefinder.R
import kotlinx.android.synthetic.main.activity_favourite_movies.*

class FavoriteMovies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_movies)

        setUpAdapter()
    }

    private fun setUpAdapter() {
        id_favourites_recyler_view.layoutManager = LinearLayoutManager(this)
        id_favourites_recyler_view.adapter = FavouriteMovieAdapter(applicationContext)
    }

    //options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.Home -> {
                Toast.makeText(this,"Home selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            R.id.Fav -> {
                Toast.makeText(this,"Favourites selected", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
