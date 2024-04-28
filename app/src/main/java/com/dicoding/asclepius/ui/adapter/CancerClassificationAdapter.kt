package com.dicoding.asclepius.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.entity.CancerClassificationsEntity
import com.dicoding.asclepius.databinding.CcItemRowBinding
import com.dicoding.asclepius.ui.result.ResultActivity

class CancerClassificationAdapter : ListAdapter<CancerClassificationsEntity, CancerClassificationAdapter.CancerClassificationViewHolder>(DIFF_CALLBACK) {
    inner class CancerClassificationViewHolder(private val binding: CcItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cc: CancerClassificationsEntity) {
            with(binding) {
                imgResultItem.setImageURI(Uri.parse(cc.imageUri))
                tvResultItem.text = cc.result
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CancerClassificationViewHolder {
        val binding = CcItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CancerClassificationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CancerClassificationViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            val moveIntent =
                Intent(holder.itemView.context, ResultActivity::class.java).run {
                    putExtra(ResultActivity.EXTRA_SAVED_RESULT, getItem(position))
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            holder.itemView.context.startActivity(moveIntent)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<CancerClassificationsEntity> =
            object : DiffUtil.ItemCallback<CancerClassificationsEntity>() {
                override fun areItemsTheSame(
                    oldItem: CancerClassificationsEntity,
                    newItem: CancerClassificationsEntity,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: CancerClassificationsEntity,
                    newItem: CancerClassificationsEntity,
                ): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }
}