package com.example.moviefinder.movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RatingBar
import android.widget.Toast
import com.example.moviefinder.R
import com.example.moviefinder.favorites.DataBaseHandler
import com.example.moviefinder.favorites.Movie_Fav
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import com.google.firebase.database.*

class MovieDetails : AppCompatActivity() {

    private val picasso = Picasso.get()
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        database = FirebaseDatabase.getInstance()

        getDetails()
        getValueToggleBtn()
        addToFavourites()

        id_details_ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar: RatingBar, rating: Float, b: Boolean ->
                if (b && rating!= 0f) saveRatingBar()
            }

    }

    private fun getValueToggleBtn() {
        val value = DataBaseHandler(this).readDataById(intent.getIntExtra("KEY_EXTRA_ID", -1))
        id_like.isChecked = value != null
    }


    private fun addToFavourites() {
        id_like.setOnCheckedChangeListener(object : View.OnClickListener, CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    Toast.makeText(this@MovieDetails, "Add to Favorites", Toast.LENGTH_SHORT).show()
                    writeData()
                } else {
                    Toast.makeText(this@MovieDetails, "Removed to Favorites", Toast.LENGTH_SHORT).show()
                    deleteDataa()
                }
            }

            override fun onClick(p0: View?) {}
        })
    }

    fun writeData() {
        val poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + intent.getStringExtra("KEY_EXTRA5")


        val movieFav = Movie_Fav(
            intent.getIntExtra("KEY_EXTRA_ID", -1),
            id_details_title.text.toString(),
            id_details_release_date.text.toString(),
            poster
        )
        val db = DataBaseHandler(this)
        db.insertData(movieFav)
    }

    fun deleteDataa() {
        val movieId = intent.getIntExtra("KEY_EXTRA_ID", -1)
        val db = DataBaseHandler(this)
        db.deleteData(movieId)

    }

    fun saveRatingBar() {
        val movieId = intent.getIntExtra("KEY_EXTRA_ID", -1)
        val ref = database.getReference("RATING_BAR")

        val ratingBar = id_details_ratingBar.rating
        val ratingId = ref.child("$movieId").key.toString()
        val bar = RatingBarClass(ratingBar.toInt())
        ref.child(ratingId).setValue(bar)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(RatingBarClass::class.java)
                Log.d("taag", "Value is: " + user?.ratingBar)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("taag", databaseError.message)
            }
        }
        ref.addValueEventListener(valueEventListener)
    }

    fun getDetails() {
        val movieId = intent.getIntExtra("KEY_EXTRA_ID", -1).toString()

        id_details_description.text = "Description:\n\n" + intent.getStringExtra("KEY_EXTRA")
        id_details_rate.text = "Vote: " + intent.getStringExtra("KEY_EXTRA1")
        id_details_release_date.text = "Release date: " + intent.getStringExtra("KEY_EXTRA2")
        id_details_language.text = "Language: " + intent.getStringExtra("KEY_EXTRA3")
        id_details_title.text = intent.getStringExtra("KEY_EXTRA4")

        val poster = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + intent.getStringExtra("KEY_EXTRA5")
        picasso
            .load(poster)
            .fit()
            .into(id_details_image)

        database.getReference("RATING_BAR").child(movieId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(RatingBarClass::class.java) ?: return
                    id_details_ratingBar.rating = data.ratingBar.toFloat()
                }

            })
    }
}