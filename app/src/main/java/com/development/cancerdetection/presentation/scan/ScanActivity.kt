package com.development.cancerdetection.presentation.scan

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import com.development.cancerdetection.R
import com.development.cancerdetection.databinding.ActivityScanBinding
import com.development.cancerdetection.domain.model.Result
import com.development.cancerdetection.helper.ImageClassifierHelper
import com.development.cancerdetection.presentation.result.ResultActivity
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.NumberFormat
import java.util.UUID

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var result: Result
    private var currentImageUri: Uri? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                currentImageUri = uri
                launchUCrop(uri)
                showImage()
            } else {
                showToast(getString(R.string.image_required))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.apply {
            ivGallery.setOnClickListener {
                startGallery()
            }
            btnAnalyze.setOnClickListener {
                if (currentImageUri != Uri.parse("android.resource://$packageName/drawable/ic_place_holder")) {
                    analyzeImage()
                } else {
                    showToast(getString(R.string.image_required))
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            currentImageUri = UCrop.getOutput(data!!)
            showImage()
            binding.btnAnalyze.isEnabled = true
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Log.d("Fungsi crop error", "showImage: $cropError")
            showToast("Fungsi crop error")
        }
    }

    private fun launchUCrop(uri: Uri) {
        val destinationFileName = "${UUID.randomUUID()}.jpg"
        val destinationUri = Uri.fromFile(File(filesDir, destinationFileName))

        val options = UCrop.Options()
        options.setCompressionQuality(100)

        UCrop.of(uri, destinationUri)
            .withOptions(options)
            .withAspectRatio(0f, 0f)
            .useSourceImageAspectRatio()
            .withMaxResultSize(3000, 3000)
            .start(this)
    }

    private fun startGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun showImage() {
        binding.ivDetect.setImageURI(currentImageUri)
    }

    private fun analyzeImage() {
        currentImageUri?.let { uri ->
            imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        runOnUiThread {
                            showToast(error)
                        }
                    }

                    override fun onResults(results: List<Classifications>?) {
                        runOnUiThread {
                            results?.let { classification ->
                                if (classification.isNotEmpty() && classification[0].categories.isNotEmpty()) {
                                    val label = classification[0].categories[0].label
                                    val score = NumberFormat.getPercentInstance()
                                        .format(classification[0].categories[0].score).trim()

                                    result = Result(
                                        imageUri = uri,
                                        label = label,
                                        score = score
                                    )

                                    moveToResult()
                                }
                            }
                        }
                    }
                }
            )
            imageClassifierHelper.classifyStaticImage(uri)
        }
    }

    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("result", result)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}