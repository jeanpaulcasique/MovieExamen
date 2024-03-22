package com.example.movieexamen.activities

import android.os.Bundle

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
        val title = intent.getStringExtra("TITLE") ?: "Título no disponible"
        val year = intent.getStringExtra("YEAR") ?: "Año no disponible"
        val poster = intent.getStringExtra("POSTER")
        val synopsis = intent.getStringExtra("SYNOPSIS") ?: "Sinopsis no disponible"
        val duration = intent.getStringExtra("DURATION") ?: "Duración no disponible"
        val director = intent.getStringExtra("DIRECTOR") ?: "Director no disponible"
        val genre = intent.getStringExtra("GENRE") ?: "Género no disponible"
        val country = intent.getStringExtra("COUNTRY") ?: "País no disponible"

        // Asignar los datos a las vistas
        findViewById<TextView>(R.id.detailTitleTextView).text = title
        findViewById<TextView>(R.id.detailYearTextView).text = year

        // Cargar imagen del póster con Picasso
        if (!poster.isNullOrEmpty()) {
            Picasso.get().load(poster).into(findViewById<ImageView>(R.id.detailPosterImageView))
        } else {
            // Aquí puedes poner un placeholder en caso de que la URL del póster esté vacía
            Picasso.get().load(R.drawable.imagen_placeholder_background).into(findViewById<ImageView>(R.id.detailPosterImageView))
        }

        findViewById<TextView>(R.id.detailSynopsisTextView).text = synopsis
        findViewById<TextView>(R.id.detailDirectorTextView).text = director
        findViewById<TextView>(R.id.detailDurationTextView).text = duration
        findViewById<TextView>(R.id.detailGenreTextView).text = genre
        findViewById<TextView>(R.id.detailCountryTextView).text = country
    }

}
