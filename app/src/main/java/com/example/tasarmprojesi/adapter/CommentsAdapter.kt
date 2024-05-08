package com.example.tasarmprojesi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasarmprojesi.databinding.CommentRowBinding
import com.example.tasarmprojesi.model.Comment

class CommentsAdapter(private var comments: List<Comment>) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {
    class CommentViewHolder(val binding: CommentRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = CommentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.binding.commentUserEmail.text = comment.userEmail
        holder.binding.commentText.text = comment.text
    }

    fun setComments(newComments: List<Comment>) {
        comments = newComments
        notifyDataSetChanged()
    }
}
