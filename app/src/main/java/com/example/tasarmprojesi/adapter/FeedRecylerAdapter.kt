package com.example.tasarmprojesi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasarmprojesi.databinding.RecyclerowBinding
import com.example.tasarmprojesi.model.Post
import com.squareup.picasso.Picasso

class FeedRecylerAdapter(private  val postList : ArrayList<Post>) : RecyclerView.Adapter<FeedRecylerAdapter.PostHolder>() {
    class PostHolder(val binding: RecyclerowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        println( postList.size)
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.textView18.text = postList.get(position).email
        holder.binding.textView19.text =postList.get(position).comment
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.imageView18)
    }
}