package com.example.lesson41.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson41.App
import com.example.lesson41.R
import com.example.lesson41.databinding.FragmentHomeBinding
import com.example.lesson41.ext.Const
import com.example.lesson41.ext.alertDialog
import com.example.lesson41.models.TaskModel
import com.example.lesson41.ui.task.TaskAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var taskAdapter: TaskAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskAdapter = TaskAdapter(
            onClick = { task ->
                editTask(task)
            },
            onLongClick = { task ->
                deleteTaskDialog(task)
            }
        )
    }

    private fun editTask(task: TaskModel) {
        val bundle = bundleOf(Const.ARG_TASK to task)
        findNavController().navigate(R.id.taskFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabtn.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
        val data = App.dataBase.dao().getAll()
        taskAdapter.addData(data)
        initAdapter()
    }

    private fun deleteTaskDialog(task: TaskModel) {
        requireContext().alertDialog(
            getString(R.string.delete_item_title),
            getString(R.string.no), getString(R.string.yes)
        ) {
            deleteTask(task)
        }
    }

    private fun deleteTask(task: TaskModel) {
        task?.id?.let { TaskModel(it, task.toString(), System.currentTimeMillis()) }
            ?.let { App.dataBase.dao().delete(it) }
        /*taskAdapter.removeTask(task)*/
    }

    private fun initAdapter() {
        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTask.adapter = taskAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
