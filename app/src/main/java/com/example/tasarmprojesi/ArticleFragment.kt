package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentArticleBinding
import com.example.tasarmprojesi.databinding.FragmentSkipBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraint.setOnClickListener {
            findNavController().navigate(R.id.action_articleFragment_to_articleDetailFragment)
        }
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView1
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.ic_id) {
                findNavController().navigate(R.id.action_articleFragment_to_profileFragment)
                return@setOnNavigationItemSelectedListener true
            }else if(item.itemId == R.id.ic_home) {
                findNavController().navigate(R.id.action_articleFragment_to_homeFragment)
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