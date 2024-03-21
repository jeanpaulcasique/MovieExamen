package com.example.movieexamen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieexamen.R
import com.example.movieexamen.model.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter(
    private var movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(itemView, onMovieClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    class ViewHolder(itemView: View, private val onMovieClick: (Movie) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            Picasso.get().load(movie.poster).into(posterImageView)

            itemView.setOnClickListener { onMovieClick(movie) }
        }
    }
}
