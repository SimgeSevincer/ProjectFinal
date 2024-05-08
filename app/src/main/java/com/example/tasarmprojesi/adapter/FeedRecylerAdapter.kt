package com.example.tasarmprojesi.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tasarmprojesi.R
import com.example.tasarmprojesi.databinding.RecyclerowBinding
import com.example.tasarmprojesi.model.Post
import com.squareup.picasso.Picasso

class FeedRecylerAdapter(private val postList: ArrayList<Post>) : RecyclerView.Adapter<FeedRecylerAdapter.PostHolder>() {
    class PostHolder(val binding: RecyclerowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = postList[position]
        holder.binding.textView19.text = post.comment
        Picasso.get().load(post.downloadUrl).into(holder.binding.imageView18)

        holder.binding.imageView16.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("post", post)
            }
            it.findNavController().navigate(R.id.action_postFragment_to_postDetailFragment, bundle)
        }
    }

    override fun getItemCount() = postList.size
}
