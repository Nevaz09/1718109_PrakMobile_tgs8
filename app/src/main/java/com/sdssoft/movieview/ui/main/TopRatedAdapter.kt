package com.sdssoft.movieview.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsTopRatedBinding
import com.sdssoft.movieview.model.TopRatedResultsItem

class TopRatedAdapter : RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>() {

    private val listMovie = ArrayList<TopRatedResultsItem>()
    private var onItemClickCallback: OnClickTopRatedCallback? = null

    fun setData(topRated: List<TopRatedResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(topRated)
        notifyDataSetChanged()
    }

    fun onItemClickCallback(onClickTopRatedCallback: OnClickTopRatedCallback) {
        this.onItemClickCallback = onClickTopRatedCallback
    }

    inner class TopRatedViewHolder(private val binding: ItemsTopRatedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topRated: TopRatedResultsItem) {
            val imgUrl = "https://image.tmdb.org/t/p/w500"
            binding.tvRating.text = topRated.voteAverage.toString()
            Glide.with(itemView.context)
                .load(imgUrl + topRated.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .transform(RoundedCorners(40))
                .error(R.drawable.ic_error)
                .into(binding.imgMovie)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(topRated) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopRatedAdapter.TopRatedViewHolder {
        val view = ItemsTopRatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopRatedViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopRatedAdapter.TopRatedViewHolder, position: Int) =
        holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnClickTopRatedCallback {
    fun onItemClicked(topRated: TopRatedResultsItem)
}