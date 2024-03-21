package com.example.movieexamen.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieexamen.R
import com.example.movieexamen.adapters.MoviesAdapter
import com.example.movieexamen.data.MovieResponse
import com.example.movieexamen.data.RetrofitClient
import com.example.movieexamen.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        moviesRecyclerView = findViewById(R.id.moviesRecyclerView)
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        // Inicializa el adaptador con un callback de click que inicia DetailActivity
        moviesAdapter = MoviesAdapter(emptyList()) { movie -> showMovieDetail(movie) }
        moviesRecyclerView.adapter = moviesAdapter
    }

    private fun showMovieDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("TITLE", movie.title)
            putExtra("YEAR", movie.year)
            putExtra("POSTER", movie.poster)
            putExtra("SYNOPSIS", movie.plot)
            putExtra("DURATION", movie.runtime)
            putExtra("DIRECTOR", movie.director)
            putExtra("GENRE", movie.genre)
            putExtra("COUNTRY", movie.country)
        }
        startActivity(intent)
    }

    private fun fetchMovies(searchQuery: String) {
        RetrofitClient.apiService.searchMovies(searchQuery, "fde4bf0f").enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.Search ?: emptyList()
                    moviesAdapter.updateMovies(movies)
                } else {
                    Toast.makeText(this@MainActivity, "Error al obtener las películas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    fetchMovies(query)
                    searchView.clearFocus() // Clear focus from search view to hide the keyboard
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Este espacio podría usarse para sugerencias de búsqueda en tiempo real, pero por simplicidad, lo dejaremos vacío.
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}
