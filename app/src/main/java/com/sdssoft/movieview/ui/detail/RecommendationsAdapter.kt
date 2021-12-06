package com.sdssoft.movieview.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsRecomendationBinding
import com.sdssoft.movieview.model.RecommendationResultsItem

class RecommendationsAdapter : RecyclerView.Adapter<RecommendationsAdapter.ViewHolder>() {

    private val listMovie = ArrayList<RecommendationResultsItem>()

    private var onItemRecommendationsClickCallback: OnItemRecommendationsClickCallback? = null

    fun onItemRecommendationsClickCallback(onItemRecommendationsClickCallback: OnItemRecommendationsClickCallback) {
        this.onItemRecommendationsClickCallback = onItemRecommendationsClickCallback
    }

    fun setData(listMovie: List<RecommendationResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemsRecomendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listMovie: RecommendationResultsItem) {
            val imgUrl = "https://image.tmdb.org/t/p/w500"
            binding.tvRating.text = listMovie.voteAverage.toString()
            Glide.with(itemView.context)
                .load(imgUrl + listMovie.posterPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .transform(RoundedCorners(40))
                .error(R.drawable.ic_error)
                .into(binding.imgMovie)
            itemView.setOnClickListener {
                onItemRecommendationsClickCallback?.onItemClicked(
                    listMovie
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemsRecomendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size


}

interface OnItemRecommendationsClickCallback {
    fun onItemClicked(recommendationResultsItem: RecommendationResultsItem)
}