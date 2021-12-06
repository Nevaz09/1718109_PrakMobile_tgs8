package com.sdssoft.movieview.ui.main.coming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsListMoreUpComingBinding
import com.sdssoft.movieview.model.UpComingResultsItem

class ListUpComingAdapter : RecyclerView.Adapter<ListUpComingAdapter.ListUpComingViewHolder>() {

    private val listMovie = ArrayList<UpComingResultsItem>()

    private var onItemMoreUpComingClickCallback: OnItemMoreUpComingClickCallback? =
        null

    fun onItemClickCallback(onItemMoreUpComingClickPopularCallback: OnItemMoreUpComingClickCallback) {
        this.onItemMoreUpComingClickCallback = onItemMoreUpComingClickPopularCallback
    }

    fun setData(listMovie: List<UpComingResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    inner class ListUpComingViewHolder(private val binding: ItemsListMoreUpComingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listMovie: UpComingResultsItem) {
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
                    onItemMoreUpComingClickCallback?.onItemClicked(
                        listMovie
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListUpComingAdapter.ListUpComingViewHolder {
        val view =
            ItemsListMoreUpComingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUpComingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListUpComingAdapter.ListUpComingViewHolder,
        position: Int
    ) = holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnItemMoreUpComingClickCallback {
    fun onItemClicked(upComing: UpComingResultsItem)
}