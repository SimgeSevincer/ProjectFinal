package com.example.tasarmprojesi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasarmprojesi.adapter.ArticleAdapter
import com.example.tasarmprojesi.databinding.FragmentArticleBinding
import com.example.tasarmprojesi.model.ArticleData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArticleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        // _binding nesnesini oluşturduktan sonra tabAll görünümüne eriş
       // val tabLayout = binding.tabLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ArticleData sınıfından makaleleri al
        val articleData = ArticleData(requireContext())
        articleData.addArticlesToDb()
        val articles = articleData.getArticlesFromDb()

        // RecyclerView'ı ayarla
        val layoutManager = LinearLayoutManager(requireContext())
        binding.articleRecyclerView.layoutManager = layoutManager
        adapter = ArticleAdapter(articles)
        binding.articleRecyclerView.adapter = adapter


        // BottomNavigationView'da tıklama işlemleri
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView1
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_id -> {
                    findNavController().navigate(R.id.action_articleFragment_to_profileFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.ic_home -> {
                    findNavController().navigate(R.id.action_articleFragment_to_homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
        // Kategori seçimindeki tıklama işlemleri
        binding.categoryAll.setOnClickListener { filterArticlesByCategory("Tümü") }
        binding.categoryHealth.setOnClickListener { filterArticlesByCategory("Sağlık") }
        binding.categorySport.setOnClickListener { filterArticlesByCategory("Spor") }
        binding.categoryLife.setOnClickListener { filterArticlesByCategory("Yaşam") }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun filterArticlesByCategory(category: String) {
        adapter.filterArticlesByCategory(category)
    }
}
