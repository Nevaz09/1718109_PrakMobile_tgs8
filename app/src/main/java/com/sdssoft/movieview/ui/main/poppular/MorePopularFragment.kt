package com.sdssoft.movieview.ui.main.poppular

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.FragmentMorePopularBinding
import com.sdssoft.movieview.model.ResultsItem
import com.sdssoft.movieview.model.TopRatedResultsItem
import com.sdssoft.movieview.ui.detail.DetailActivity
import com.sdssoft.movieview.ui.main.MainViewModel
import com.sdssoft.movieview.ui.main.top.GridTopRatedAdapter
import com.sdssoft.movieview.ui.main.top.ListTopRatedAdapter
import com.sdssoft.movieview.ui.main.top.OnGridMoreTopRatedClickCallback
import com.sdssoft.movieview.ui.main.top.OnItemMoreTopRatedClickCallback


class MorePopularFragment : Fragment() {

    private var _binding: FragmentMorePopularBinding? = null

    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    private val list = ArrayList<ResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMorePopularBinding.inflate(inflater, container, false)
        val token = getString(R.string.token)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        mainViewModel.getPopular(token)
        mainViewModel.listMovie.observe(viewLifecycleOwner, { popular ->
            this.list.addAll(popular)
            listViewAdapter(list)
        })

        mainViewModel.isLoadingPopular.observe(viewLifecycleOwner, { progressBarState ->
            showProgressBar(progressBarState)
        })

        binding.spList.setOnSpinnerItemSelectedListener<String> { _, _, _, item ->
            if (item == "List") {
                listViewAdapter(list)
            } else if (item == "Grid") {
                gridViewAdapter(list)
            }
        }

        return _binding!!.root
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun listViewAdapter(list: ArrayList<ResultsItem>) {
        val adapter = ListPopularAdapter()
        adapter.setData(list)
        binding.rvMorePopular.layoutManager = LinearLayoutManager(context)
        binding.rvMorePopular.adapter = adapter
        adapter.onItemClickCallback(object : OnItemMorePopularClickCallback {
            override fun onItemClicked(popular: ResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, popular.id)
                startActivity(mIntent)
            }
        })
    }

    private fun gridViewAdapter(list: ArrayList<ResultsItem>) {
        val adapter = GridPopularAdapter()
        adapter.setData(list)
        binding.rvMorePopular.layoutManager = GridLayoutManager(context, 2)
        binding.rvMorePopular.adapter = adapter
        adapter.onGridMorePopularClickCallback(object : OnGridMorePopularClickCallback {
            override fun onItemClicked(popular: ResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, popular.id)
                startActivity(mIntent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}