package com.sdssoft.movieview.ui.main.top

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
import com.sdssoft.movieview.databinding.FragmentMoreTopRatedBinding
import com.sdssoft.movieview.model.TopRatedResultsItem
import com.sdssoft.movieview.ui.detail.DetailActivity
import com.sdssoft.movieview.ui.main.MainViewModel

class MoreTopRatedFragment : Fragment() {

    private var _binding: FragmentMoreTopRatedBinding? = null

    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    private val list = ArrayList<TopRatedResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMoreTopRatedBinding.inflate(inflater, container, false)
        val token = getString(R.string.token)
        mainViewModel.getTopRated(token)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        mainViewModel.listTopRated.observe(viewLifecycleOwner, { topRated ->
            this.list.addAll(topRated)
            listViewAdapter(list)
        })

        mainViewModel.isLoadingTopRated.observe(viewLifecycleOwner, { progressBarState ->
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

    private fun listViewAdapter(list: ArrayList<TopRatedResultsItem>) {
        val adapter = ListTopRatedAdapter()
        adapter.setData(list)
        binding.rvMoreTopRated.layoutManager = LinearLayoutManager(context)
        binding.rvMoreTopRated.adapter = adapter
        adapter.onItemClickCallback(object : OnItemMoreTopRatedClickCallback {
            override fun onItemClicked(topRated: TopRatedResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, topRated.id)
                startActivity(mIntent)
            }
        })
    }

    private fun gridViewAdapter(list: ArrayList<TopRatedResultsItem>) {
        val adapter = GridTopRatedAdapter()
        adapter.setData(list)
        binding.rvMoreTopRated.layoutManager = GridLayoutManager(context, 2)
        binding.rvMoreTopRated.adapter = adapter
        adapter.onGridMoreTopRatedClickCallback(object : OnGridMoreTopRatedClickCallback {
            override fun onItemClicked(topRatedResultsItem: TopRatedResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, topRatedResultsItem.id)
                startActivity(mIntent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}