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

    // Variables para manejar la lista de películas y su adaptador.
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter

    // CREA LA VISTA POR PRIMERA VEZ
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)   // Define el layout que utilizará la actividad.

        // Inicializa la lista (RecyclerView) de películas.
        initRecyclerView()
    }

    // Inicializa el RecyclerView y su adaptador.
    private fun initRecyclerView() {
        // Encuentra el RecyclerView en el layout.
        moviesRecyclerView = findViewById(R.id.moviesRecyclerView)
        // Define el layout manager para el RecyclerView. En este caso, un LinearLayoutManager que muestra los elementos en forma de lista vertical.
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        // Inicializa el adaptador con una lista vacía y un callback que se ejecuta al hacer clic en un elemento.
        moviesAdapter = MoviesAdapter(emptyList()) { movie -> showMovieDetail(movie) } // Inicializa el adaptador del RecyclerView. Al principio, se pasa una lista vacía porque aún no se han cargado los datos. El segundo argumento es una función lambda que define lo que sucede cuando se hace clic en una película llama a showMovieDetail(movie) para mostrar los detalles de la película seleccionada.
        // Establece el adaptador al RecyclerView.
        moviesRecyclerView.adapter = moviesAdapter
    }

    // Muestra los detalles de una película.
    private fun showMovieDetail(movie: Movie) {
        // Crea un intent para iniciar DetailActivity.
        val intent = Intent(this, DetailActivity::class.java).apply {
            // Agrega datos extras al intent que se pasarán a DetailActivity.
            putExtra("TITLE", movie.title)
            putExtra("YEAR", movie.year)
            putExtra("Poster", movie.poster)
            putExtra("Director", movie.director)
            // Repite para los demás atributos de la película.
        }

        startActivity(intent) // Inicia DetailActivity.
    }

    // FUNCION QUE OBTIENE LAS PELICULAS DEL API
    private fun fetchMovies(searchQuery: String) {

        RetrofitClient.apiService.searchMovies(searchQuery, "fde4bf0f").enqueue(object : Callback<MovieResponse> { // Llama al método de búsqueda de películas de la API y maneja la respuesta o fallo.
            // En caso de respuesta exitosa.
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    // Obtiene las películas de la respuesta y actualiza el adaptador.
                    val movies = response.body()?.Search ?: emptyList()
                    moviesAdapter.updateMovies(movies)
                } else {
                    // Muestra un error en caso de respuesta no exitosa.
                    Toast.makeText(this@MainActivity, "Error al obtener las películas", Toast.LENGTH_SHORT).show()
                }
            }

            // En caso de fallo en la comunicación con la API.
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Muestra un mensaje de error.
                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Infla el menú de opciones y maneja el comportamiento de búsqueda.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Infla el menú.
        menuInflater.inflate(R.menu.menu_main, menu)
        // Configura la funcionalidad de búsqueda.
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Maneja el evento de envío de búsqueda.
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Verifica que la consulta no esté vacía y realiza la búsqueda.
                if (!query.isNullOrEmpty()) {
                    fetchMovies(query)
                    searchView.clearFocus() // Oculta el teclado al buscar.
                }
                return true
            }

            // Opcionalmente, maneja cambios en el texto de búsqueda en tiempo real. Se deja vacío por simplicidad.
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}

