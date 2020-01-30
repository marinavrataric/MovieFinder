package com.example.moviefinder.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinder.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_favourite_row.view.*

class FavouriteMovieAdapter(val context: Context) : RecyclerView.Adapter<FavouriteMovieAdapter.CustomHolder>() {

    var db = DataBaseHandler(context)
    var data = db.readData()

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.bind(data[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.activity_favourite_row, parent, false)
        return CustomHolder(cellForRow)
    }

    fun delete(movieId: Int) {
        val moviePosition = data.indexOf(data.firstOrNull { it.id == movieId })

        data.removeAt(moviePosition)
        notifyItemRemoved(moviePosition)
    }


   inner class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(result: Movie_Fav, context: Context) {
            itemView.id_fav_title.text = result.titleFav
            itemView.id_fav_release_date.text = result.releaseFav!!.split("-")[0]

            var db = DataBaseHandler(context)
            itemView.id_fav.setOnClickListener {
                Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show()
                db.deleteData(result.id)
                delete(result.id)
            }

        Picasso
            .get()
            .load(result.posterPathFav)
            .into(itemView.id_fav_image)
        }
    }
}