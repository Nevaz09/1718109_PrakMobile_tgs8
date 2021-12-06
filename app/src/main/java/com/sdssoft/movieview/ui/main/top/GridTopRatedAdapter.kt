package com.sdssoft.movieview.ui.main.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsGridMoreTopRatedBinding
import com.sdssoft.movieview.model.TopRatedResultsItem

class GridTopRatedAdapter : RecyclerView.Adapter<GridTopRatedAdapter.GridTopRatedViewHolder>() {

    private var listMovie = ArrayList<TopRatedResultsItem>()

    private var onGridMoreTopRatedClickCallback: OnGridMoreTopRatedClickCallback? = null

    fun onGridMoreTopRatedClickCallback(onGridMoreTopRatedClickCallback: OnGridMoreTopRatedClickCallback) {
        this.onGridMoreTopRatedClickCallback = onGridMoreTopRatedClickCallback
    }

    fun setData(listMovie: List<TopRatedResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    inner class GridTopRatedViewHolder(private val binding: ItemsGridMoreTopRatedBinding) :
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
                tvRating.text = listMovie.voteAverage.toString()
                itemView.setOnClickListener {
                    onGridMoreTopRatedClickCallback?.onItemClicked(
                        listMovie
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridTopRatedAdapter.GridTopRatedViewHolder {
        val view =
            ItemsGridMoreTopRatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridTopRatedViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GridTopRatedAdapter.GridTopRatedViewHolder,
        position: Int
    ) = holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnGridMoreTopRatedClickCallback {
    fun onItemClicked(topRatedResultsItem: TopRatedResultsItem)
}
