package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentArticleDetailBinding
import com.squareup.picasso.Picasso

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

        val articleName = arguments?.getString("articleName")
        val articleDescription = arguments?.getString("articleDescription")
        val articleDate = arguments?.getString("articleDate")
        val articleCategory = arguments?.getString("articleCategory")

        binding.articleName.text = articleName
        binding.articleDescription.text = articleDescription
        binding.articleDate.text = articleDate
        binding.articleCategory.text = articleCategory

        // Görüntüyü yüklemek için Picasso kütüphanesi kullanılıyor
        val articleImageUrl = arguments?.getString("articleImageUrl")
        articleImageUrl?.let {
            Picasso.get().load(it).into(binding.articleImage)
        }

        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_id -> {
                    findNavController().navigate(R.id.action_articleDetailFragment_to_profileFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.ic_home -> {
                    findNavController().navigate(R.id.action_articleDetailFragment_to_homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
