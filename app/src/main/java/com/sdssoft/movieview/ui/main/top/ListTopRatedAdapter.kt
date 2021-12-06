package com.sdssoft.movieview.ui.main.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsListMoreTopRatedBinding
import com.sdssoft.movieview.model.ResultsItem
import com.sdssoft.movieview.model.TopRatedResultsItem

class ListTopRatedAdapter : RecyclerView.Adapter<ListTopRatedAdapter.ListTopRatedViewHolder>() {

    private val listMovie = ArrayList<TopRatedResultsItem>()

    private var onItemMoreTopRatedClickCallback: OnItemMoreTopRatedClickCallback? =
        null

    fun onItemClickCallback(onItemMoreTopRatedClickPopularCallback: OnItemMoreTopRatedClickCallback) {
        this.onItemMoreTopRatedClickCallback = onItemMoreTopRatedClickPopularCallback
    }

    fun setData(listMovie: List<TopRatedResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    inner class ListTopRatedViewHolder(private val binding: ItemsListMoreTopRatedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listMovie: TopRatedResultsItem) {
            val imgUrl = "https://image.tmdb.org/t/p/w500"
            with(binding) {
                Glide.with(itemView.context)
                    .load(imgUrl + listMovie.posterPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .transform(RoundedCorners(40))
                    .error(R.drawable.ic_error)
                    .into(binding.imgMovie)
                tvTitle.text = listMovie.originalTitle
                tvRating.text = listMovie.voteAverage.toString()
                tvRegion.text = listMovie.originalLanguage
                itemView.setOnClickListener {
                    onItemMoreTopRatedClickCallback?.onItemClicked(
                        listMovie
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTopRatedAdapter.ListTopRatedViewHolder {
        val view =
            ItemsListMoreTopRatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTopRatedViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListTopRatedAdapter.ListTopRatedViewHolder,
        position: Int
    ) = holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnItemMoreTopRatedClickCallback {
    fun onItemClicked(topRated: TopRatedResultsItem)
}