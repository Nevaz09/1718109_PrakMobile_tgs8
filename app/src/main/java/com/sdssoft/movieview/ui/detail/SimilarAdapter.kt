package com.sdssoft.movieview.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsSimilarBinding
import com.sdssoft.movieview.model.SimilarResultsItem

class SimilarAdapter : RecyclerView.Adapter<SimilarAdapter.ViewHolder>() {

    private val listMovie = ArrayList<SimilarResultsItem>()

    private var onItemSimilarClickCallback: OnItemSimilarClickCallback? = null

    fun onItemSimilarClickCallback(onItemSimilarClickCallback: OnItemSimilarClickCallback) {
        this.onItemSimilarClickCallback = onItemSimilarClickCallback
    }

    fun setData(listMovie: List<SimilarResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemsSimilarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listMovie: SimilarResultsItem) {
            val imgUrl = "https://image.tmdb.org/t/p/w500"
            binding.tvRating.text = listMovie.voteAverage.toString()
            Glide.with(itemView.context)
                .load(imgUrl + listMovie.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .transform(RoundedCorners(40))
                .error(R.drawable.ic_error)
                .into(binding.imgMovie)
            itemView.setOnClickListener { onItemSimilarClickCallback?.onItemClicked(listMovie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarAdapter.ViewHolder {
        val view = ItemsSimilarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarAdapter.ViewHolder, position: Int) =
        holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnItemSimilarClickCallback {
    fun onItemClicked(similarResultsItem: SimilarResultsItem)
}