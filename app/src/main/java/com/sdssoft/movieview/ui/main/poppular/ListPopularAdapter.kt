package com.sdssoft.movieview.ui.main.poppular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsListMorePopularBinding
import com.sdssoft.movieview.model.ResultsItem

class ListPopularAdapter : RecyclerView.Adapter<ListPopularAdapter.ListPopularViewHolder>() {

    private val listMovie = ArrayList<ResultsItem>()

    private var onItemMorePopularClickCallback: OnItemMorePopularClickCallback? =
        null

    fun onItemClickCallback(onItemMorePopularClickPopularCallback: OnItemMorePopularClickCallback) {
        this.onItemMorePopularClickCallback = onItemMorePopularClickPopularCallback
    }

    fun setData(listMovie: List<ResultsItem>) {
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    inner class ListPopularViewHolder(private val binding: ItemsListMorePopularBinding) :
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
                tvTitle.text = listMovie.originalTitle
                tvRating.text = listMovie.voteAverage.toString()
                tvRegion.text = listMovie.originalLanguage
                itemView.setOnClickListener {
                    onItemMorePopularClickCallback?.onItemClicked(
                        listMovie
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListPopularAdapter.ListPopularViewHolder {
        val view =
            ItemsListMorePopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListPopularViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListPopularAdapter.ListPopularViewHolder,
        position: Int
    ) = holder.bind(listMovie[position])

    override fun getItemCount(): Int = listMovie.size
}

interface OnItemMorePopularClickCallback {
    fun onItemClicked(popular: ResultsItem)
}