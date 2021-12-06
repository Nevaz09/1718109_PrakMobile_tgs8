package com.sdssoft.movieview.ui.main.poppular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsGridMorePopularBinding
import com.sdssoft.movieview.model.ResultsItem

class GridPopularAdapter : RecyclerView.Adapter<GridPopularAdapter.GridPopularViewHolder>() {

    private var listMovie = ArrayList<ResultsItem>()

    private var onGridMorePopularClickCallback: OnGridMorePopularClickCallback? = null

    fun onGridMorePopularClickCallback(onGridMorePopularClickCallback: OnGridMorePopularClickCallback) {
        this.onGridMorePopularClickCallback = onGridMorePopularClickCallback
    }

    fun setData(listMovie: List<ResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    inner class GridPopularViewHolder(private val binding: ItemsGridMorePopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listMovie: ResultsItem) {
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
                    onGridMorePopularClickCallback?.onItemClicked(
                        listMovie
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridPopularAdapter.GridPopularViewHolder {
        val view =
            ItemsGridMorePopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridPopularViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GridPopularAdapter.GridPopularViewHolder,
        position: Int
    ) = holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnGridMorePopularClickCallback {
    fun onItemClicked(popular: ResultsItem)
}
