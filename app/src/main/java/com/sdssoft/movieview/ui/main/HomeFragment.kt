package com.sdssoft.movieview.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.FragmentHomeBinding
import com.sdssoft.movieview.model.NowPlayingResultsItem
import com.sdssoft.movieview.model.ResultsItem
import com.sdssoft.movieview.model.TopRatedResultsItem
import com.sdssoft.movieview.model.UpComingResultsItem
import com.sdssoft.movieview.ui.detail.DetailActivity
import com.sdssoft.movieview.ui.main.coming.MoreUpComingFragment
import com.sdssoft.movieview.ui.main.poppular.MorePopularFragment
import com.sdssoft.movieview.ui.main.top.MoreTopRatedFragment


class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null

    private val mainViewModel by viewModels<MainViewModel>()

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val token = getString(R.string.token)

        mainViewModel.getPopular(token)
        mainViewModel.getNowPlaying(token)
        mainViewModel.getTopRated(token)
        mainViewModel.getUpComing(token)

        mainViewModel.listMovie.observe(viewLifecycleOwner, { listMovie ->
            if (listMovie != null) {
                setRecyclerViewPopular(listMovie)

            }
        })
        mainViewModel.listNowPlaying.observe(viewLifecycleOwner, { listNowPlaying ->
            if (listNowPlaying != null) {
                setRecyclerViewNowPlaying(listNowPlaying)
            }
        })
        mainViewModel.listTopRated.observe(viewLifecycleOwner, { listTopRated ->
            if (listTopRated != null) {
                setRecyclerViewTopRated(listTopRated)
            }
        })
        mainViewModel.listUpComing.observe(viewLifecycleOwner, { listUpComing ->
            if (listUpComing != null) {
                setRecyclerViewUpComing(listUpComing)
            }
        })

        mainViewModel.listSearch.observe(viewLifecycleOwner, { listSearch ->
            if (listSearch != null) {

            }
        })



        binding.tvMorePopular.setOnClickListener(this)
        binding.tvMoreTopRated.setOnClickListener(this)
        binding.tvMoreUpComing.setOnClickListener(this)

        return _binding!!.root
    }

    private fun setRecyclerViewPopular(popular: List<ResultsItem>) {
        binding.rvPopular.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val adapter = PopularAdapter()
        adapter.setData(popular.slice(0..8))
        binding.rvPopular.adapter = adapter
        adapter.onItemClickCallback(object : OnItemClickPopularCallback {
            override fun onItemClicked(popular: ResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, popular.id)
                startActivity(mIntent)
            }

        })
    }

    private fun setRecyclerViewTopRated(topRated: List<TopRatedResultsItem>) {
        binding.rvTopRated.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val adapter = TopRatedAdapter()
        adapter.setData(topRated.slice(0..8))
        binding.rvTopRated.adapter = adapter
        adapter.onItemClickCallback(object : OnClickTopRatedCallback {
            override fun onItemClicked(topRated: TopRatedResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, topRated.id)
                startActivity(mIntent)
            }
        })
    }

    private fun setRecyclerViewUpComing(upComing: List<UpComingResultsItem>) {
        binding.rvUpComing.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val adapter = UpComingAdapter()
        adapter.setData(upComing.slice(0..8))
        binding.rvUpComing.adapter = adapter
        adapter.onItemClickCallback(object : OnClickUpComingCallback {
            override fun onItemClicked(upComing: UpComingResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, upComing.id)
                startActivity(mIntent)
            }
        })
    }

    private fun setRecyclerViewNowPlaying(nowPlaying: List<NowPlayingResultsItem>) {
        val adapter = NowPlayingAdapter()
        adapter.setData(nowPlaying)
        binding.rvCarousel.adapter = adapter
        binding.rvCarousel.set3DItem(true)
        binding.rvCarousel.setAlpha(true)
        val carouselLayoutManager = binding.rvCarousel.getCarouselLayoutManager()
        carouselLayoutManager.scrollToPosition(2)
        adapter.onItemClickCallback(object : OnItemClickNowPlayingCallback {
            override fun onItemClicked(nowPlaying: NowPlayingResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, nowPlaying.id)
                startActivity(mIntent)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            val searchFragment = SearchFragment()
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    searchFragment,
                    searchFragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_more_popular -> {
                val morePopularFragment = MorePopularFragment()
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, morePopularFragment,
                        morePopularFragment::class.java.simpleName
                    )
                    .addToBackStack(null)
                    .commit()
            }
            R.id.tv_more_top_rated -> {
                val moreTopRatedFragment = MoreTopRatedFragment()
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, moreTopRatedFragment,
                        MoreTopRatedFragment::class.java.simpleName
                    )
                    .addToBackStack(null)
                    .commit()
            }
            R.id.tv_more_up_coming -> {
                val moreUpComingFragment = MoreUpComingFragment()
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container, moreUpComingFragment,
                        MoreUpComingFragment::class.java.simpleName
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}