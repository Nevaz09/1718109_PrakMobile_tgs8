package com.sdssoft.movieview.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sdssoft.movieview.databinding.ItemsGenreBinding
import com.sdssoft.movieview.model.GenresItem

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    private val listGenre = ArrayList<GenresItem>()

    fun setData(listGenre: List<GenresItem>) {
        this.listGenre.clear()
        this.listGenre.addAll(listGenre)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemsGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenresItem) {
            binding.tvGenre.text = genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapter.ViewHolder {
        val view = ItemsGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreAdapter.ViewHolder, position: Int) =
        holder.bind(listGenre[position])

    override fun getItemCount(): Int = listGenre.size
}