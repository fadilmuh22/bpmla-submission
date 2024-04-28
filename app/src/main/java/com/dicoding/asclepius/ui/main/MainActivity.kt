package com.dicoding.asclepius.ui.main

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.ui.adapter.CancerClassificationAdapter
import com.dicoding.asclepius.ui.classification.ClassificationActivity
import com.dicoding.asclepius.ui.result.ResultActivity

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory.getInstance(application)
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val ccAdapter = CancerClassificationAdapter()
        setRecyclerView(ccAdapter)

        getCancerClassifications(ccAdapter)

        binding.btnClassify.setOnClickListener {
            startClassification()
        }
    }

    private fun getCancerClassifications(ccAdapter: CancerClassificationAdapter) {
        viewModel.getAllCancerClassifications().observe(this) {
            if (it.isNullOrEmpty()) {
                binding.tvNoSaved.visibility = View.VISIBLE
            } else {
                binding.tvNoSaved.visibility = View.GONE
                ccAdapter.submitList(it)
            }
        }
    }

    private fun startClassification() {
        startActivity(Intent(this, ClassificationActivity::class.java))
    }

    private fun setRecyclerView(ccAdapter: CancerClassificationAdapter) {
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