package com.development.cancerdetection.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.development.cancerdetection.R
import com.development.cancerdetection.databinding.ItemImageBinding
import com.development.cancerdetection.domain.model.Result

class HistoryAdapter(
    val onDeleteClick: (Result) -> Unit,
    val onShowClick: (Uri) -> Unit
) : ListAdapter<Result, HistoryAdapter.ResultViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ResultViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(result.imageUri)
                    .into(ivImageResultAnalyze)
                tvLabel.text = result.label
                tvScore.text = result.score
                tvAnalyzeTime.text = result.date

                ivShow.setOnClickListener {
                    onShowClick(result.imageUri)
                }
                if (result.label == "Cancer") {
                    tvLabel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cancer, 0, 0, 0)
                } else {
                    tvLabel.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_non_cancer,
                        0,
                        0,
                        0
                    )
                }
                btnDelete.setOnClickListener {
                    onDeleteClick(result)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}