package com.example.lesson41.ui.onBoard

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.lesson41.R
import com.example.lesson41.databinding.FragmentOnBoardingBinding
import com.example.lesson41.ext.Const

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = requireContext().getSharedPreferences(Const.PREF_NAME, MODE_PRIVATE)
        binding.rvBoarding.adapter = BoardingAdapter {
            findNavController().navigate(R.id.navigation_home)
            pref.edit().putBoolean(Const.IS_BOARDING_SHOWN, true)
        }
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvBoarding)
    }
}