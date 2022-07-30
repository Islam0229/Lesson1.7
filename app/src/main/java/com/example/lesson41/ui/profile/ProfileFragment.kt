package com.example.lesson41.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.lesson41.R
import com.example.lesson41.databinding.FragmentProfileBinding
import com.example.lesson41.ext.alertDialog
import com.example.lesson41.ext.loadImage
import com.example.lesson41.ext.showToast
import com.example.lesson41.ui.Pref

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

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                saveText(binding.etName.text.toString())
            }
        })

        binding.etName.setText(Pref(requireContext()).getName())

        binding.img.setOnClickListener {
            loadFromGalley()
        }
        binding.img.setOnLongClickListener {
            if (binding.img.drawable != null) {
                showToast(getString(R.string.image_exists))
            } else {
                requireContext().alertDialog(
                    getString(R.string.add_photo),
                    getString(R.string.no),
                    getString(R.string.yes)
                ) {
                    loadFromGalley()
                }
            }
            true
        }
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

    private fun saveText(name: String) {
        Pref(requireContext()).saveName(name)
    }
}



