package com.kyuseon.movies.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyuseon.movies.adapter.RecentAdapter
import com.kyuseon.movies.dataClass.RecentVO
import com.kyuseon.movies.databinding.ActivityRecentBinding
import com.kyuseon.movies.sqlite.DBHelper

class RecentActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecentBinding
    lateinit var recentAdapter: RecentAdapter

    var recentList: MutableList<RecentVO> = mutableListOf<RecentVO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DBHelper(this, MainActivity.DB_NAME, MainActivity.VERSION)

        dbHelper.selectRecent()?.let { recentList.addAll(it) }

        /** 리사이클러뷰 연결  */
        val linearLayoutManager = LinearLayoutManager(this)
        recentAdapter = RecentAdapter(recentList)
        binding.recyclerview.layoutManager = linearLayoutManager
        binding.recyclerview.adapter = recentAdapter



    }
}