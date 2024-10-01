package com.development.cancerdetection.presentation.image

import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.development.cancerdetection.R
import com.development.cancerdetection.databinding.ActivityDetailImageBinding
import com.development.cancerdetection.domain.model.Result

class DetailImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra("uri"))

        binding.apply {
            ivPlace.setImageURI(imageUri)
            ivClose.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}