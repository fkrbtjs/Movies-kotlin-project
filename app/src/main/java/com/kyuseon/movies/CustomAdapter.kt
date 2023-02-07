package com.kyuseon.movies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kyuseon.movies.databinding.ItemMainBinding

class CustomAdapter(val moviesList:MutableList<MoviesVO>): RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        context = parent.context
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val customViewHolder = CustomViewHolder(binding)

        return customViewHolder
    }

    override fun onBindViewHolder(customViewHolder: CustomViewHolder, position: Int) {
        val binding = (customViewHolder as CustomViewHolder).binding
        val moviesVO = moviesList.get(position)
        val defaultImage = R.drawable.empty
        Glide.with(binding.root)
            .load(moviesVO.image) // 불러올 이미지 url
            .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
            .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
            .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
            .into(binding.ivImage) // 이미지를 넣을 뷰

        binding.tvTitle.text= moviesVO.title
        binding.tvPubDate.text= moviesVO.pubDate
        binding.tvUserRating.text= moviesVO.userRating.toString()

        /** 해당 영화의 link(url)정보를 갖고 WebviewActivity(웹브라우저 화면)로 이동 */
        customViewHolder.itemView.setOnClickListener {
            val link = moviesVO.link
            val intent = Intent(context,WebviewActivity::class.java)
            intent.putExtra("link",link)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    class CustomViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)
}