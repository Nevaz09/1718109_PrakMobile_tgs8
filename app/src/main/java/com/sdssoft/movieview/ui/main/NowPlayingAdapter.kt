package com.sdssoft.movieview.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsNowPlayingBinding
import com.sdssoft.movieview.model.NowPlayingResultsItem

class NowPlayingAdapter :
    RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>() {

    private val listMovie = ArrayList<NowPlayingResultsItem>()

    private var onItemClickCallback: OnItemClickNowPlayingCallback? = null

    fun onItemClickCallback(onItemClickNowPlayingCallback: OnItemClickNowPlayingCallback) {
        this.onItemClickCallback = onItemClickNowPlayingCallback
    }

    fun setData(nowPlaying: List<NowPlayingResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(nowPlaying)
        notifyDataSetChanged()
    }

    inner class NowPlayingViewHolder(private val binding: ItemsNowPlayingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nowPlaying: NowPlayingResultsItem) {
            val imgUrl = "https://image.tmdb.org/t/p/w500"
            Glide.with(itemView.context)
                .load(imgUrl + nowPlaying.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .transform(RoundedCorners(40))
                .error(R.drawable.ic_error)
                .into(binding.imgMovie)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(nowPlaying) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingAdapter.NowPlayingViewHolder {
        val view =
            ItemsNowPlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NowPlayingViewHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingAdapter.NowPlayingViewHolder, position: Int) =
        holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnItemClickNowPlayingCallback {
    fun onItemClicked(nowPlaying: NowPlayingResultsItem)
}