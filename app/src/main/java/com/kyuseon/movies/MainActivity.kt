package com.kyuseon.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyuseon.movies.databinding.ActivityMainBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    companion object {
        val DB_NAME = "MoviesDB"
        var VERSION = 30
    }

    lateinit var customAdapter: CustomAdapter
    lateinit var moviesVO: MoviesVO
    lateinit var dbHelper: DBHelper
    var apiClient = APIClient()
    var moviesList: MutableList<MoviesVO> = mutableListOf<MoviesVO>()
    var recentVO : RecentVO = RecentVO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /** 프로그레스바 초기값  */
        binding.progressBar.visibility = View.INVISIBLE


        /** db생성  */
        dbHelper = DBHelper(this, DB_NAME, VERSION)


        /** RecentActivity(최근 검색 이력 화면)에서 intent값(검색어)을 받았다면 검색리스트를 띄우고 아니라면 전체리스트를 띄움  */
        if(intent.hasExtra("word")){
            val word = intent.getStringExtra("word").toString()
            moviesList.clear()
            binding.searchview.setQuery(word,false)
            dbHelper.searchMovies(word)?.let{ moviesList.addAll(it)}
        }else{
            dbHelper.selectMovies()?.let { moviesList.addAll(it) }
        }


        /** 리사이클러뷰 연결  */
        val linearLayoutManager = LinearLayoutManager(this)
        customAdapter = CustomAdapter(moviesList)
        binding.recyclerview.layoutManager = linearLayoutManager
        binding.recyclerview.adapter = customAdapter


        /** db에 저장된값이 없거나 RecentActivity(최근 검색 이력 화면)에서 intent값(검색어)을 받지않았다면 실행  */
        if(!intent.hasExtra("word") && moviesList.size==0){

            /** retrofit객체 생성  */
            val retrofit = Retrofit.Builder()
                .baseUrl(apiClient.BASE_URL_NAVER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create(NaverApi::class.java)
            val callGetSearchMovies = api.getSearchMovies(apiClient.CLIENT_ID, apiClient.CLIENT_SECRET, "test")


            /** api를 받아오는 동안 프로그레스바 실행  */
            binding.progressBar.visibility = View.VISIBLE


            /** api를 받아온 후 moviesVO객체에 값을 넣고 , 객체를 moviesList에 넣는다 */
            callGetSearchMovies.enqueue(object : Callback<ResultGetSearchMovies> {
                override fun onResponse(
                    call: Call<ResultGetSearchMovies>,
                    response: Response<ResultGetSearchMovies>
                ) {
                    val data = response.body()!!.items
                    data.forEach {
                        val title = it.title
                        val image = it.image
                        val pubDate = it.pubDate
                        val userRating = it.userRating
                        val link = it.link

                        moviesVO = MoviesVO(
                            title, image, pubDate, userRating, link
                        )
                        dbHelper.insertMovies(moviesVO)
                        moviesList.add(moviesVO)
                    }
                        /** db에 저장된 값들을 리스트에 띄움  */
                        dbHelper.selectMovies()?.let { moviesList.addAll(it) }
                        customAdapter.notifyDataSetChanged()
                        /** 프로그레스바 종료  */
                        binding.progressBar.visibility = View.INVISIBLE
                }
                override fun onFailure(call: Call<ResultGetSearchMovies>, t: Throwable) {

                }
            })
        }


        /** 검색버튼을 눌렀을 때 검색어를 db에 저장, 검색어를 입력하지않고 누를 시 전체리스트 띄움 */
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query.equals("")){
                    moviesList.clear()
                    dbHelper.selectMovies()?.let { moviesList.addAll(it) }
                    customAdapter.notifyDataSetChanged()
                }else{
                    moviesList.clear()
                    dbHelper.searchMovies(query)?.let{ moviesList.addAll(it)}
                    recentVO = RecentVO(query)
                    dbHelper.insertRecent(recentVO)
                    customAdapter.notifyDataSetChanged()
                }
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }

        })


        /** 검색버튼을 눌렀을 때 검색어를 db에 저장, 검색어를 입력하지않고 누를 시 전체리스트 띄움 */
        binding.btnSearch.setOnClickListener {
            val query = binding.searchview.query.toString()
            val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.searchview.windowToken, 0)
            if (query.equals("")){
                moviesList.clear()
                dbHelper.selectMovies()?.let { moviesList.addAll(it) }
                customAdapter.notifyDataSetChanged()
            }else{
                moviesList.clear()
                dbHelper.searchMovies(query)?.let{ moviesList.addAll(it)}
                recentVO = RecentVO(query)
                dbHelper.insertRecent(recentVO)
                customAdapter.notifyDataSetChanged()
            }
        }


        /** RecentActivity(최근 검색 이력 화면)로 이동 */
        binding.btnRecent.setOnClickListener {
            val intent = Intent(this@MainActivity, RecentActivity::class.java)
            startActivity(intent)
        }
    }
}