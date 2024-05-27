package com.example.tasarmprojesi.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController


import kotlin.require


import androidx.recyclerview.widget.RecyclerView
import com.example.tasarmprojesi.ArticleDetailFragment
import com.example.tasarmprojesi.R
import com.example.tasarmprojesi.model.Article
import com.squareup.picasso.Picasso

class ArticleAdapter(private var articles: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleImage: ImageView = itemView.findViewById(R.id.articleImage)
        val articleName: TextView = itemView.findViewById(R.id.articleName)

    }
    // Orijinal makalelerin bir kopyasını saklamak için değişken
    private val originalArticles: List<Article> = articles.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = articles[position]
        holder.articleName.text = currentArticle.makaleName
        Picasso.get().load(currentArticle.makaleImage).into(holder.articleImage)
        holder.itemView.setOnClickListener { view ->
            val bundle = Bundle().apply {
                putString("articleName", currentArticle.makaleName)
                putString("articleDescription", currentArticle.makaleDescription)
                putString("articleDate", currentArticle.makaleDate)
                putString("articleCategory",currentArticle.makaleCategory)
                putInt("articleFavCount", currentArticle.makaleFavCount)
                putString("articleImageUrl", currentArticle.makaleImage) // Makale resim URL'sini ekleyin
            }
            view.findNavController().navigate(R.id.action_articleFragment_to_articleDetailFragment, bundle)
        }

    }


    override fun getItemCount(): Int {
        return articles.size
    }

    fun filterArticlesByCategory(category: String) {
        articles = if (category == "Tümü") {
            originalArticles
        } else {
            originalArticles.filter { it.makaleCategory == category }
        }
        notifyDataSetChanged()
    }

}
