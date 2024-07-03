package com.example.newsscroll

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.Builder
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.newsscroll.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsListAdapter

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.newsRecyview.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        //Linking adapter to recycle view:
        binding.newsRecyview.adapter = mAdapter
    }

    private fun fetchData(){
        // Instantiate the RequestQueue.
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            {
                val newsJSONArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJSONArray.length()){
                    val newsJsonObject = newsJSONArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                Log.d("API_Response","News Array: $newsArray")
                mAdapter.updateNews(newsArray)
            },
            {

            })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val intent: CustomTabsIntent = Builder().build()
        intent.launchUrl(this@MainActivity, Uri.parse(item.url))

    }
}