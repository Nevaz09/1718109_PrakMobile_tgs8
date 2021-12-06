package com.sdssoft.movieview.ui.main.coming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsGridMoreUpComingBinding
import com.sdssoft.movieview.model.ResultsItem
import com.sdssoft.movieview.model.UpComingResultsItem

class GridUpComingAdapter : RecyclerView.Adapter<GridUpComingAdapter.GridUpComingViewHolder>() {

    private var listMovie = ArrayList<UpComingResultsItem>()

    private var onGridMoreUpComingClickCallback: OnGridMoreUpComingClickCallback? = null

    fun onGridMoreUpComingClickCallback(onGridMoreUpComingClickCallback: OnGridMoreUpComingClickCallback) {
        this.onGridMoreUpComingClickCallback = onGridMoreUpComingClickCallback
    }

    fun setData(listMovie: List<UpComingResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    inner class GridUpComingViewHolder(private val binding: ItemsGridMoreUpComingBinding) :
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
                tvRating.text = listMovie.voteAverage.toString()
                itemView.setOnClickListener {
                    onGridMoreUpComingClickCallback?.onItemClicked(
                        listMovie
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridUpComingAdapter.GridUpComingViewHolder {
        val view =
            ItemsGridMoreUpComingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridUpComingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GridUpComingAdapter.GridUpComingViewHolder,
        position: Int
    ) = holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnGridMoreUpComingClickCallback {
    fun onItemClicked(upComing: UpComingResultsItem)
}
