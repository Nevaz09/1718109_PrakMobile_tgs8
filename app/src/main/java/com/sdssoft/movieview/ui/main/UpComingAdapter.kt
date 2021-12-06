package com.sdssoft.movieview.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsUpComingBinding
import com.sdssoft.movieview.model.UpComingResultsItem

class UpComingAdapter : RecyclerView.Adapter<UpComingAdapter.UpComingViewHolder>() {

    private val listMovie = ArrayList<UpComingResultsItem>()
    private var onItemClickCallback: OnClickUpComingCallback? = null

    fun setData(topRated: List<UpComingResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(topRated)
        notifyDataSetChanged()
    }

    fun onItemClickCallback(onClickUpComingCallback: OnClickUpComingCallback) {
        this.onItemClickCallback = onClickUpComingCallback
    }

    inner class UpComingViewHolder(private val binding: ItemsUpComingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(upComing: UpComingResultsItem) {
            val imgUrl = "https://image.tmdb.org/t/p/w500"
            binding.tvRating.text = upComing.voteAverage.toString()
            Glide.with(itemView.context)
                .load(imgUrl + upComing.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .transform(RoundedCorners(40))
                .error(R.drawable.ic_error)
                .into(binding.imgMovie)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(upComing) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpComingAdapter.UpComingViewHolder {
        val view = ItemsUpComingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpComingViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpComingAdapter.UpComingViewHolder, position: Int) =
        holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnClickUpComingCallback {
    fun onItemClicked(upComing: UpComingResultsItem)
}