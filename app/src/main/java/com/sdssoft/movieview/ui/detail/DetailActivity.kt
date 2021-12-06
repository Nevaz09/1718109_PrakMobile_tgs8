package com.sdssoft.movieview.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.ActivityDetailBinding
import com.sdssoft.movieview.model.GenresItem
import com.sdssoft.movieview.model.RecommendationResultsItem
import com.sdssoft.movieview.model.SimilarResultsItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val ID_EXTRA = "EXTRA_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val token = getString(R.string.token)
        val imgUrl = "https://image.tmdb.org/t/p/w500"
        val id = intent.getIntExtra(ID_EXTRA, 0)

        detailViewModel.getDetail(token, id)
        detailViewModel.detailMovie.observe(this, { detailMovie ->
            binding.toolbarLayout.title = detailMovie.originalTitle
            Glide.with(this)
                .load(imgUrl + detailMovie.backdropPath)
                .apply(RequestOptions.placeholderOf(android.R.color.holo_purple))
                .error(R.drawable.ic_error)
                .into(binding.imgPoster)
            showRecycleViewGenre(detailMovie.genres)
            binding.rbMovie.rating = detailMovie.voteAverage.toFloat() / 2
            binding.tvOverview.text = detailMovie.overview
        })

        detailViewModel.getSimilar(token, id)
        detailViewModel.similarMovie.observe(this, { listMovie ->
            showRecyclerViewSimilar(listMovie)
        })

        detailViewModel.getRecommendation(token, id)
        detailViewModel.recommendationResponseMovie.observe(this, { listMovie ->
            showRecyclerViewRecommendation(listMovie)
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showRecycleViewGenre(genre: List<GenresItem>) {
        binding.rvGenre.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val adapter = GenreAdapter()
        adapter.setData(genre)
        binding.rvGenre.adapter = adapter
    }

    private fun showRecyclerViewSimilar(similar: List<SimilarResultsItem>) {
        binding.rvSimilar.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val adapter = SimilarAdapter()
        adapter.setData(similar)
        binding.rvSimilar.adapter = adapter
        adapter.onItemSimilarClickCallback(object : OnItemSimilarClickCallback {
            override fun onItemClicked(similarResultsItem: SimilarResultsItem) {
                val mIntent = Intent(this@DetailActivity, DetailActivity::class.java)
                mIntent.putExtra(ID_EXTRA, similarResultsItem.id)
                startActivity(mIntent)
            }

        })
    }

    private fun showRecyclerViewRecommendation(recommendationResultsItem: List<RecommendationResultsItem>) {
        binding.rvRecommendations.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val adapter = RecommendationsAdapter()
        adapter.setData(recommendationResultsItem)
        binding.rvRecommendations.adapter = adapter
        adapter.onItemRecommendationsClickCallback(object : OnItemRecommendationsClickCallback {
            override fun onItemClicked(recommendationResultsItem: RecommendationResultsItem) {
                val mIntent = Intent(this@DetailActivity, DetailActivity::class.java)
                mIntent.putExtra(ID_EXTRA, recommendationResultsItem.id)
                startActivity(mIntent)
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}