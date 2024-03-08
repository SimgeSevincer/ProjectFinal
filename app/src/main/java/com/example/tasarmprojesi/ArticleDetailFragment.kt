package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentArticleDetailBinding
import com.example.tasarmprojesi.databinding.FragmentPostBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ArticleDetailFragment : Fragment() {
    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.ic_id) {
                findNavController().navigate(R.id.action_articleDetailFragment_to_profileFragment)
                return@setOnNavigationItemSelectedListener true
            }else if(item.itemId == R.id.ic_home) {
                findNavController().navigate(R.id.action_articleDetailFragment_to_homeFragment)
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