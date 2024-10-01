package com.development.cancerdetection.presentation.result

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.development.cancerdetection.R
import com.development.cancerdetection.databinding.ActivityResultBinding
import com.development.cancerdetection.domain.model.Result
import com.development.cancerdetection.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val viewModel by viewModels<ResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("result", Result::class.java)
        } else {
            intent.getParcelableExtra("result")
        }

        binding.apply {
            resultImage.setImageURI(result?.imageUri)
            resultText.text = "${result?.score}\n${result?.label}"
            if (result?.label == "Cancer") {
                resultText.setTextColor(getColor(R.color.md_theme_error))
            } else {
                resultText.setTextColor(getColor(R.color.green))
            }
            binding.btnBack.setOnClickListener {
                val intent = Intent(this@ResultActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            binding.btnSave.setOnClickListener {
                if (result != null) {
                    viewModel.insertResult(result)
                    Toast.makeText(
                        this@ResultActivity,
                        "Hasil analisis berhasil disimpan pada History!",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnSave.isEnabled = false
                } else {
                    Toast.makeText(
                        this@ResultActivity,
                        "Tidak bisa menyimpan hasil analisis, gambar dan hasil tidak terdeteksi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}