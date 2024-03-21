package com.example.movieexamen.activities

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.movieexamen.R
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Recuperar datos del Intent
        val title = intent.getStringExtra("TITLE")
        Log.d("DetailActivity", "Title: $title")
        val year = intent.getStringExtra("YEAR")
        val poster = intent.getStringExtra("POSTER")
        val synopsis = intent.getStringExtra("SYNOPSIS")
        val duration = intent.getStringExtra("DURATION")
        val director = intent.getStringExtra("DIRECTOR")
        val genre = intent.getStringExtra("GENRE")
        val country = intent.getStringExtra("COUNTRY")

        // Asignar los datos a las vistas
        findViewById<TextView>(R.id.detailTitleTextView).text = title
        findViewById<TextView>(R.id.detailYearTextView).text = getString(R.string.year_format, year)
        Picasso.get().load(poster).into(findViewById<ImageView>(R.id.detailPosterImageView))
        findViewById<TextView>(R.id.detailSynopsisTextView).text = getString(R.string.synopsis_format, synopsis)
        findViewById<TextView>(R.id.detailDurationTextView).text = getString(R.string.duration_format, duration)
        findViewById<TextView>(R.id.detailDirectorTextView).text = getString(R.string.director_format, director)
        findViewById<TextView>(R.id.detailGenreTextView).text = getString(R.string.genre_format, genre)
        findViewById<TextView>(R.id.detailCountryTextView).text = getString(R.string.country_format, country)
    }
}
