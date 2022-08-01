package com.example.lesson41.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lesson41.App
import com.example.lesson41.databinding.FragmentTaskBinding
import com.example.lesson41.ext.Const
import com.example.lesson41.models.TaskModel

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var task: TaskModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveBtn.setOnClickListener {
            saveOrUpdate()
        }
        checkTask()
    }

    private fun saveOrUpdate() {
        val updatedTask = binding.etTask.text.toString().lowercase().replaceFirstChar {
            it.uppercase()
        }
        val isNewTask = task == null
        if (isNewTask) {
            returnNewTask(updatedTask)
        } else {
            returnExistingTask(updatedTask)
        }
        findNavController().navigateUp()
    }

    private fun checkTask() {
        task = arguments?.getSerializable(Const.ARG_TASK) as? TaskModel
        if (task != null) {
            binding.etTask.setText(task!!.task)
        }
    }

    private fun returnNewTask(updatedTask: String) {
        App.dataBase.dao().insert(TaskModel(0, updatedTask, System.currentTimeMillis()))
    }

    private fun returnExistingTask(updatedTask: String) {
        task?.id?.let { TaskModel(it, updatedTask, System.currentTimeMillis()) }
            ?.let { App.dataBase.dao().update(it) }
    }
}