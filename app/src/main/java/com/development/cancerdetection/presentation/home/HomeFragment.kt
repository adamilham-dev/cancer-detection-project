package com.development.cancerdetection.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.cancerdetection.data.remote.response.ArticlesItem
import com.development.cancerdetection.databinding.FragmentHomeBinding
import com.development.cancerdetection.helper.Resource
import com.development.cancerdetection.presentation.adapter.ArticleAdapter
import com.development.cancerdetection.presentation.scan.ScanActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var articleAdapter: ArticleAdapter
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancerScannerHome.setOnClickListener {
            startActivity(Intent(requireContext(), ScanActivity::class.java))
        }

        viewModel.getArticles().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(
                        view,
                        "Gagal memuat artikel",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    binding.apply {
                        rvArticles.visibility = View.GONE
                        ivNotFound.visibility = View.VISIBLE
                    }
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (result.data != null) {
                        setUpView(result.data)
                    }
                    binding.apply {
                        rvArticles.visibility = View.VISIBLE
                        ivNotFound.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setUpView(articles: List<ArticlesItem>) {
        articleAdapter = ArticleAdapter()

        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = articleAdapter
        }
        articleAdapter.submitList(articles)
    }
}