package com.example.lesson41.ui.profile

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.lesson41.databinding.FragmentProfileBinding
import com.example.lesson41.ext.loadImage
import com.example.lesson41.ext.showToast

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == RESULT_OK) {
                val image = it?.data?.data
                binding.img.loadImage(image)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.img.setOnClickListener {
            loadFromGalley()
        }
        binding.img.setOnLongClickListener {
            if (binding.img.drawable != null) {
                showToast("Photo is already inserted.")
            } else {
                galleryDialog()
            }
            true
        }
    }

    private fun galleryDialog() {
        val d = AlertDialog.Builder(requireContext())
        d.setTitle("Add photo")
        d.setNegativeButton("No") { dialog, p1 ->
            dialog.cancel()
        }
        d.setPositiveButton("Yes") { dialog, p1 ->
            loadFromGalley()
            dialog.cancel()
        }
        d.create().show()
    }

    private fun loadFromGalley() {
        val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = "image/*"
        launcher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

