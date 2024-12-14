package com.example.examenjugetes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examenjugetes.adapter.MovieAdapter
import com.example.examenjugetes.model.Movie
import com.example.examenjugetes.network.ApiClient
import com.example.examenjugetes.network.ApiMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.rv_movies)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        movieAdapter = MovieAdapter(emptyList(), this)
        recyclerView.adapter = movieAdapter


        progressBar.visibility = View.VISIBLE
        showMovies()


        val fakeMovies = listOf(
            Movie().apply {
                id = "1"
                nombre = "Superman"
                logo = "https://images-na.ssl-images-amazon.com/images/I/71BSoOgjRCL.jpg"
            },
            Movie().apply {
                id = "2"
                nombre = "Buzz Lightyear"
                logo = "https://http2.mlstatic.com/D_NQ_NP_967684-MLU70320451465_072023-O.webp"
            },
            Movie().apply {
                id = "3"
                nombre = "Mickey Mouse"
                logo = "https://www.aceroymagia.com/Images/articulo/figura-classic-mickey-mouse-the-original-disney/01-figura-classic-mickey-mouse-the-original-disney.jpg"
            },
            Movie().apply {
                id = "4"
                nombre = "Sonic"
                logo = "https://drimjuguetes.vtexassets.com/arquivos/ids/848260-800-720?v=638201582928030000&width=800&height=720&aspect=true"
            },
            Movie().apply {
                id = "5"
                nombre = "Spider-Man"
                logo = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPm4bT6AI5lq1pen9oQv_vTRf4NZRue0-Ykw&s"
            },
            Movie().apply {
                id = "6"
                nombre = "Elsa 2"
                logo = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUoFni79QtBpB4b2Mb2yHeqwG-HV-IxHafZg&s"
            },




        )

        movieAdapter.updateMovies(fakeMovies)

    }



    private fun showMovies() {
        val apiService = ApiClient.getClient().create(ApiMovie::class.java)
        val call: Call<List<Movie>> = apiService.getMovies()

        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val movies = response.body() ?: emptyList()
                    Log.d("MainActivity", "Movies fetched: ${movies.size}")
                    movies.forEach {
                        Log.d("MainActivity", "Movie: ID=${it.id}, Name=${it.nombre}, Logo=${it.logo}")
                    }
                    movieAdapter.updateMovies(movies)
                } else {
                    Log.e("MainActivity", "Error Code: ${response.code()}")
                    Log.e("MainActivity", "Error Body: ${response.errorBody()?.string()}")
                    Toast.makeText(this@MainActivity, "Error al cargar las películas.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("MainActivity", "API call failed: ${t.message}")
                Toast.makeText(this@MainActivity, "No se pudo conectar con el servidor.", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
