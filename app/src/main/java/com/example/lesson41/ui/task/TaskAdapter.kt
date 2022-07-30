package com.example.lesson41.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson41.R
import com.example.lesson41.databinding.ItemTaskBinding
import com.example.lesson41.models.TaskModel
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private val onClick: (TaskModel) -> Unit,
    private val onLongClick: (TaskModel) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = mutableListOf<TaskModel>()

    fun addTask(new: TaskModel) {
        tasks.add(0, new)
        notifyItemInserted(tasks.lastIndex)
    }

    fun editTask(new: TaskModel) {
        val index = tasks.indexOfFirst { it.time == new.time }
        tasks[index] = new
        notifyItemChanged(index)
    }

    fun addData(data: List<TaskModel>?) {
        tasks.clear()
        data?.let {
            tasks.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun removeTask(task: TaskModel) {
        val index = tasks.indexOfFirst {
            it.task == task.task
        }
        tasks.removeAt(index)
        notifyItemRemoved(index)
    }

    fun convertDate(millis: Long, dateFormat: String): String {
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        return simpleDateFormat.format(Date(millis)).toString()
    }

    inner class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: TaskModel) {
            val isThemeColor = adapterPosition % 2 == 0
            val color = if (isThemeColor) {
                R.color.app_theme_color
            } else {
                R.color.app_bg_color
            }
            itemView.setBackgroundColor(itemView.context.getColor(color))
            binding.tvTask.text = model.task
            binding.tvDate.text = convertDate(model.time, "dd-MMMM-yyyy hh:mm")

            itemView.setOnClickListener {
                onClick(model)
            }
            itemView.setOnLongClickListener {
                onLongClick(model)
                return@setOnLongClickListener true
            }
        }
    }
}