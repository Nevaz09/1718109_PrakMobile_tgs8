package com.sdssoft.movieview.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsPopularBinding
import com.sdssoft.movieview.model.ResultsItem

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    private var onItemClickCallback: OnItemClickPopularCallback? = null
    private val popular = ArrayList<ResultsItem>()

    fun setData(popular: List<ResultsItem>) {
        this.popular.clear()
        this.popular.addAll(popular)
        notifyDataSetChanged()
    }

    fun onItemClickCallback(onItemClickCallback: OnItemClickPopularCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class PopularViewHolder(private val binding: ItemsPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popular: ResultsItem) {
            val imgUrl = "https://image.tmdb.org/t/p/w500"
            binding.tvRating.text = popular.voteAverage.toString()
            Glide.with(itemView.context)
                .load(imgUrl + popular.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .transform(RoundedCorners(40))
                .error(R.drawable.ic_error)
                .into(binding.imgMovie)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(popular) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularAdapter.PopularViewHolder {
        val view = ItemsPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularAdapter.PopularViewHolder, position: Int) =
        holder.bind(popular[position])

    override fun getItemCount(): Int = popular.size
}

interface OnItemClickPopularCallback {
    fun onItemClicked(popular: ResultsItem)
}