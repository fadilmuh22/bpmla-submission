package com.dicoding.asclepius.ui.result

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.data.local.entity.CancerClassificationsEntity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.ui.main.MainActivity
import com.dicoding.asclepius.utils.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private val viewModel: ResultViewModel by viewModels {
        ResultViewModelFactory.getInstance(application)
    }

    private val binding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }

    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private var currentResult: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitle("Classification Result")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val savedResult =
            if (Build.VERSION.SDK_INT >= 33) {
                intent.getParcelableExtra(EXTRA_SAVED_RESULT, CancerClassificationsEntity::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(EXTRA_SAVED_RESULT)
            }

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_RESULT_URI) ?: "")

        if (savedResult != null) {
            binding.imgResult.setImageURI(Uri.parse(savedResult.imageUri))
            binding.tvResult.text = savedResult.result
            binding.btnSaveResult.visibility = View.GONE
            binding.btnDeleteResult.visibility = View.VISIBLE

            binding.btnDeleteResult.setOnClickListener {
                viewModel.deleteCancerClassification(savedResult)
                goBackToMainActivity()
            }
        } else {
            if (imageUri.host != null) {
                imageUri.let {
                    Log.d("Image URI", "showImage: $it")
                    binding.imgResult.setImageURI(it)
                    getResult(it)
                }
            }

            binding.btnSaveResult.setOnClickListener {
                viewModel.saveCancerClassification(
                    CancerClassificationsEntity(
                        imageUri = imageUri.toString(),
                        result = currentResult,
                    ),
                )
                goBackToMainActivity()
            }
        }
    }

    private fun goBackToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME)
        startActivity(intent)
        finish()
    }

    private fun getResult(imageUri: Uri) {
        imageClassifierHelper =
            ImageClassifierHelper(
                context = this,
                classifierListener =
                    object : ImageClassifierHelper.ClassifierListener {
                        override fun onError(error: String) {
                            runOnUiThread {
                                Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onResults(
                            results: List<Classifications>?,
                            inferenceTime: Long,
                        ) {
                            runOnUiThread {
                                results?.let { it ->
                                    if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                        Log.d(TAG, it.toString())
                                        val sortedCategories =
                                            it[0].categories.sortedByDescending { it?.score }
                                        val displayResult =
                                            sortedCategories.joinToString("\n") {
                                                "${it.label} " +
                                                    NumberFormat.getPercentInstance()
                                                        .format(it.score).trim()
                                            }
                                        binding.tvResult.text = displayResult
                                        currentResult = displayResult
                                    } else {
                                        binding.tvResult.text = ""
                                    }
                                }
                            }
                        }
                    },
            )

        imageClassifierHelper.classifyImage(imageUri)
    }

    companion object {
        const val TAG = "ResultActivity"
        const val EXTRA_IMAGE_RESULT_URI = "extra_image_result_uri"
        const val EXTRA_SAVED_RESULT = "extra_saved_result"
    }
}
