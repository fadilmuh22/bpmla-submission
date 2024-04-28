package com.dicoding.asclepius.ui.classification

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityClassificationBinding
import com.dicoding.asclepius.ui.result.ResultActivity
import com.dicoding.asclepius.utils.getImageUri
import com.yalantis.ucrop.UCrop

class ClassificationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityClassificationBinding.inflate(layoutInflater)
    }

    private var currentImageUri: Uri? = null

    private val launcherGallery =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia(),
        ) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                cropImage.launch(listOf<Uri>(uri, getImageUri(this@ClassificationActivity)))
            } else {
                Log.d("Photo Picker", "No media selected")
            }
        }

    private val uCropContract =
        object : ActivityResultContract<List<Uri>, Uri>() {
            override fun createIntent(
                context: Context,
                input: List<Uri>,
            ): Intent {
                return UCrop.of(input[0], input[1])
                    .withAspectRatio(5f, 5f)
                    .withMaxResultSize(512, 512)
                    .getIntent(context)
            }

            override fun parseResult(
                resultCode: Int,
                intent: Intent?,
            ): Uri {
                return UCrop.getOutput(intent!!)!!
            }
        }

    private val cropImage =
        registerForActivityResult(uCropContract) {
            currentImageUri = it
            showImage()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitle("Cancer Classification")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let {
                analyzeImage()
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMAGE_RESULT_URI, currentImageUri.toString())
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
