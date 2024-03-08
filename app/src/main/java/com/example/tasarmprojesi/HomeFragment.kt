package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentHomeBinding
import com.example.tasarmprojesi.databinding.FragmentSkipBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintLayout2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_postFragment)
        }
        binding.textView11.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment)
        }
        binding.constraint1.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_articleDetailFragment)
        }
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.ic_id) {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                return@setOnNavigationItemSelectedListener true
            }
            false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}