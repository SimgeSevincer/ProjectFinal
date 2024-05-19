package com.example.tasarmprojesi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tasarmprojesi.R
import com.example.tasarmprojesi.databinding.ProfilrecyclerrowBinding
import com.example.tasarmprojesi.model.Post
import com.squareup.picasso.Picasso

class ProfilRecylerAdapter(private val profilpostList: ArrayList<Post>) : RecyclerView.Adapter<ProfilRecylerAdapter.ProfilpostHolder>() {

    class ProfilpostHolder(val binding: ProfilrecyclerrowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilpostHolder {
        val binding = ProfilrecyclerrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfilpostHolder(binding)
    }

    override fun getItemCount(): Int {
        println(profilpostList.size)
        return profilpostList.size
    }

    override fun onBindViewHolder(holder: ProfilpostHolder, position: Int) {
        val post = profilpostList[position]
        holder.binding.textView25.text = post.userEmail
        holder.binding.textView28.text = post.comment
        holder.binding.likeCountTextView.text = "${post.likeCount}"
        holder.binding.commentCountTextView.text = "${post.commentCount}"
        Picasso.get().load(post.downloadUrl).into(holder.binding.imageView25)

        // Yorum simgesine tıklama olayı
        holder.binding.imageView6.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_profileFragment_to_postDetailFragment)
        }
    }
}
