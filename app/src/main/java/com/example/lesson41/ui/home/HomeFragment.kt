package com.example.lesson41.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson41.R
import com.example.lesson41.TaskAdapter
import com.example.lesson41.TaskModel
import com.example.lesson41.databinding.FragmentHomeBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabtn.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
        setFragmentResultListener("result") { key, bundle ->
            val text = bundle.getString("key").orEmpty()
            val model = TaskModel(text)
            taskAdapter.addTask(model)
        }
        initAdapter()
        binding.img.setOnClickListener {
            openGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val bitmap =
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, data.data)
            binding.img.setImageBitmap(bitmap)
        }
    }

    private fun initAdapter() {
        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = TaskAdapter()
        binding.rvTask.adapter = taskAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "select picture")
        startActivityForResult(chooser, 1)
    }
}
