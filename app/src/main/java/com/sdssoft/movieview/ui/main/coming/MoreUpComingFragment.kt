package com.sdssoft.movieview.ui.main.coming

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
import com.sdssoft.movieview.databinding.FragmentMoreUpComingBinding
import com.sdssoft.movieview.model.UpComingResultsItem
import com.sdssoft.movieview.ui.detail.DetailActivity
import com.sdssoft.movieview.ui.main.MainViewModel


class MoreUpComingFragment : Fragment() {

    private var _binding: FragmentMoreUpComingBinding? = null

    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    private val list = ArrayList<UpComingResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoreUpComingBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val token = getString(R.string.token)

        mainViewModel.getUpComing(token)
        mainViewModel.listUpComing.observe(viewLifecycleOwner, { upComing ->
            this.list.addAll(upComing)
            listViewAdapter(list)
        })

        mainViewModel.isLoadingUpComing.observe(viewLifecycleOwner, { progressBarState ->
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

    private fun listViewAdapter(list: ArrayList<UpComingResultsItem>) {
        val adapter = ListUpComingAdapter()
        adapter.setData(list)
        binding.rvMoreUpComing.layoutManager = LinearLayoutManager(context)
        binding.rvMoreUpComing.adapter = adapter
        adapter.onItemClickCallback(object : OnItemMoreUpComingClickCallback {
            override fun onItemClicked(upComing: UpComingResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, upComing.id)
                startActivity(mIntent)
            }
        })
    }

    private fun gridViewAdapter(list: ArrayList<UpComingResultsItem>) {
        val adapter = GridUpComingAdapter()
        adapter.setData(list)
        binding.rvMoreUpComing.layoutManager = GridLayoutManager(context, 2)
        binding.rvMoreUpComing.adapter = adapter
        adapter.onGridMoreUpComingClickCallback(object : OnGridMoreUpComingClickCallback {
            override fun onItemClicked(upComing: UpComingResultsItem) {
                val mIntent = Intent(context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.ID_EXTRA, upComing.id)
                startActivity(mIntent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}