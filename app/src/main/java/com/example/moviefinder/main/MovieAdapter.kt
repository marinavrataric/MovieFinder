package com.example.moviefinder.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinder.R
import com.example.moviefinder.model.Movies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_row.view.*

typealias MovieClickListener = (movie: Movies) -> Unit

class MovieAdapter(private val movieClickListener: MovieClickListener): RecyclerView.Adapter<CustomHolder>(){

    val movies: MutableList<Movies> = mutableListOf()

    override fun getItemCount(): Int {
        return movies.size
    }
    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.bind(movies[position], movieClickListener)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow =  layoutInflater.inflate(R.layout.activity_movie_row, parent, false)
        return CustomHolder(cellForRow)
    }
    fun setData(data: List<Movies>) {
        movies.clear()
        movies.addAll(data)
        notifyDataSetChanged()
    }
}
class CustomHolder(view: View): RecyclerView.ViewHolder(view){
    fun bind(result: Movies, movieClickListener: MovieClickListener){
        itemView.id_movie_title.text = result.title

        itemView.id_movie_release_date.text = result.releaseDate!!.split("-")[0]

        val picture = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + result.posterPath
        Picasso
            .get()
            .load(picture)
            .into(itemView.id_movie_image)

        itemView.setOnClickListener { movieClickListener(result) }
    }
}