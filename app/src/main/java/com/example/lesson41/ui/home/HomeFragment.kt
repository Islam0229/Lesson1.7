package com.example.lesson41.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson41.R
import com.example.lesson41.TaskAdapter
import com.example.lesson41.TaskModel
import com.example.lesson41.databinding.FragmentHomeBinding
import com.example.lesson41.ext.Const

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
        setFragmentResultListener(Const.REQUEST_TASK_RESULT) { key, bundle ->
            if (bundle.containsKey(Const.KEY_FOR_EXISTING_TASK)) {
                val task = bundle.getSerializable(Const.KEY_FOR_EXISTING_TASK) as TaskModel
                taskAdapter.editTask(task)
            }
            if (bundle.containsKey(Const.KEY_FOR_TASK)) {
                val text = bundle.getSerializable(Const.KEY_FOR_TASK) as TaskModel
                taskAdapter.addTask(text)
            }
        }
        initAdapter()
    }

    private fun deleteTaskDialog(task: TaskModel) {

        val d = AlertDialog.Builder(requireContext())
        d.setTitle("Delete the task?")
        d.setNegativeButton("No") { dialog, p1 ->
            dialog.cancel()
        }
        d.setPositiveButton("Yes") { dialog, p1 ->
            deleteTask(task)
            dialog.cancel()
        }
        d.create().show()
    }

    private fun deleteTask(task: TaskModel) {
        taskAdapter.removeTask(task)
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
