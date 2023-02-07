package com.kyuseon.movies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kyuseon.movies.databinding.ItemMainBinding
import com.kyuseon.movies.databinding.ItemRecentBinding

class RecentAdapter(val recentList:MutableList<RecentVO>): RecyclerView.Adapter<RecentAdapter.CustomViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        context = parent.context
        val binding = ItemRecentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val customViewHolder = CustomViewHolder(binding)

        return customViewHolder
    }

    override fun onBindViewHolder(customViewHolder: CustomViewHolder, position: Int) {
        val binding = (customViewHolder as CustomViewHolder).binding
        val recentVO = recentList.get(position)
        binding.tvWord.text= recentVO.word

        /** 해당 검색어를 MainActivity로 넘겨줌 */
        customViewHolder.itemView.setOnClickListener {
            val word = recentVO.word
            val intent = Intent(context,MainActivity::class.java)
            intent.putExtra("word",word)
            context.startActivity(intent)
            (context as RecentActivity).finish()
        }
    }

    override fun getItemCount(): Int {
        return recentList.size
    }

    class CustomViewHolder(val binding: ItemRecentBinding): RecyclerView.ViewHolder(binding.root)
}