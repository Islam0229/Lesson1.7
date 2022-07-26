package com.example.lesson41.ui.onBoard

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.lesson41.databinding.FragmentOnBoardingBinding
import com.example.lesson41.ui.Pref

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BoardingAdapter {
            start()
        }

        binding.rvBoarding.adapter = adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvBoarding)

        binding.indicator.attachToRecyclerView(binding.rvBoarding, snapHelper)

        adapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)

        binding.tvSkip.setOnClickListener {
            start()
        }
    }

    private fun start() {
        Pref(requireContext()).saveShown()
        findNavController().navigateUp()
    }
}