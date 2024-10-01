package com.development.cancerdetection.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.development.cancerdetection.databinding.FragmentHistoryBinding
import com.development.cancerdetection.domain.model.Result
import com.development.cancerdetection.presentation.adapter.HistoryAdapter
import com.development.cancerdetection.presentation.image.DetailImageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private val viewModel by viewModels<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHistories().observe(viewLifecycleOwner) {
            setupView(it)
        }
    }

    private fun setupView(results: List<Result>) {
        historyAdapter = HistoryAdapter(
            onDeleteClick = { result ->
                viewModel.deleteHistory(result)
            },
            onShowClick = { uri ->
                val intent = Intent(requireActivity(), DetailImageActivity::class.java)
                intent.putExtra("uri", uri.toString())
                startActivity(intent)
            }
        )
        binding.rvHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        historyAdapter.submitList(results)

        if (results.isEmpty()) {
            binding.apply {
                rvHistory.visibility = View.GONE
                ivNotFound.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                rvHistory.visibility = View.VISIBLE
                ivNotFound.visibility = View.GONE
            }
        }
    }
}