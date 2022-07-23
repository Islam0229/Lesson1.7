package com.example.lesson41.ui.onBoard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson41.R
import com.example.lesson41.databinding.ItemBoardingBinding

class BoardingAdapter(private val onStartClick: () -> Unit) :
    RecyclerView.Adapter<BoardingAdapter.ViewHolder>() {

    val arrayList = arrayListOf("Create your tasks", "Edit them", "Manage")
    val arrayListImages =
        arrayListOf(R.drawable.illustration, R.drawable.illustration2, R.drawable.illustration3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBoardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = arrayList.size

    inner class ViewHolder(private val binding: ItemBoardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvTitle.text = arrayList.get(adapterPosition)
            binding.ivOnBoard.setImageResource(arrayListImages.get(adapterPosition))
            binding.btnStart.isVisible = adapterPosition == arrayList.lastIndex
            binding.btnStart.setOnClickListener {
                onStartClick()
            }
        }
    }
}