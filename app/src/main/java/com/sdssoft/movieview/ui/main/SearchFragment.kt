package com.sdssoft.movieview.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdssoft.movieview.R
import com.sdssoft.movieview.databinding.FragmentSearchBinding
import com.sdssoft.movieview.model.SearchResultsItem
import com.sdssoft.movieview.ui.detail.DetailActivity


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val token = activity?.getString(R.string.token)
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mainViewModel.getSearch(token!!, newText!!)
                return true
            }

        })

        mainViewModel.isLoadingSearch.observe(viewLifecycleOwner, { isLoadingSearch ->
            showProgressBar(isLoadingSearch)
        })

        mainViewModel.listSearch.observe(viewLifecycleOwner, { listSearch ->
            if (listSearch != null) {
                showRecyclerViewSearch(listSearch)
            }
        })

        return _binding!!.root
    }

    private fun showRecyclerViewSearch(listSearch: List<SearchResultsItem>) {
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        val adapter = SearchAdapter()
        adapter.setData(listSearch)
        binding.rvSearch.adapter = adapter
        adapter.onItemSearchCLickCallback(object : OnItemSearchClickCallback {
            override fun onItemClicked(searchResultsItem: SearchResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, searchResultsItem.id)
                startActivity(mIntent)
            }
        })
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}