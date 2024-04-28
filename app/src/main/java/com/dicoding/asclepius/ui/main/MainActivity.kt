package com.dicoding.asclepius.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.ui.adapter.CancerClassificationAdapter
import com.dicoding.asclepius.ui.classification.ClassificationActivity

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory.getInstance(application)
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val ccAdapter by lazy {
        CancerClassificationAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitle("Home")

        setRecyclerView()

        fetchNews()
        getCancerClassifications()

        binding.btnClassify.setOnClickListener {
            startClassification()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchNews()
        getCancerClassifications()
    }

    private fun fetchNews() {
        viewModel.fetchTopHeadlines().observe(this) {
            when (it) {
                is Result.Error -> {
                    Log.d("MainActivity", "Error ${it.error}")
                }
                Result.Loading -> {
                    Log.d("MainActivity", "Loading...")
                }
                is Result.Success -> {
                    Glide.with(this@MainActivity)
                        .load(it.data.articles.first().urlToImage)
                        .into(binding.imgNewsHeadline)
                    binding.tvNewsTitle.text = it.data.articles.first().title
                }
            }
        }
    }

    private fun getCancerClassifications() {
        viewModel.getAllCancerClassifications().observe(this) {
            if (it.isNullOrEmpty()) {
                binding.tvSavedTitle.text = getString(R.string.empty_saved_result)
            } else {
                binding.tvSavedTitle.text = getString(R.string.saved_result)
                ccAdapter.submitList(it)
            }
        }
    }

    private fun startClassification() {
        startActivity(Intent(this, ClassificationActivity::class.java))
    }

    private fun setRecyclerView() {
        binding.rvCancerClassifications.apply {
            addItemDecoration(
                DividerItemDecoration(
                    baseContext,
                    LinearLayoutManager.VERTICAL,
                ),
            )
            adapter = ccAdapter
        }
    }
}
