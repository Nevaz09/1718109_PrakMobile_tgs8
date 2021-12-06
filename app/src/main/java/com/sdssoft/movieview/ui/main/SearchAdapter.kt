package com.sdssoft.movieview.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ItemsSearchBinding
import com.sdssoft.movieview.model.SearchResultsItem

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var onItemSearchClickCallback: OnItemSearchClickCallback? = null

    private val list = ArrayList<SearchResultsItem>()

    fun onItemSearchCLickCallback(onItemSearchClickCallback: OnItemSearchClickCallback) {
        this.onItemSearchClickCallback = onItemSearchClickCallback
    }

    fun setData(list: List<SearchResultsItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: ItemsSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listMovie: SearchResultsItem) {
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
                    onItemSearchClickCallback?.onItemClicked(
                        listMovie
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder {
        val mView = ItemsSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(mView)
    }

    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size
}

interface OnItemSearchClickCallback {
    fun onItemClicked(searchResultsItem: SearchResultsItem)
}