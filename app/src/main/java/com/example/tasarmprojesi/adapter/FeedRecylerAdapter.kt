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
        holder.binding.textView18.text = post.userEmail
        holder.binding.textView19.text = post.comment
        holder.binding.likeCountTextView.text = "${post.likeCount}"
        holder.binding.commentCountTextView.text = "${post.commentCount}"
        Picasso.get().load(post.downloadUrl).into(holder.binding.imageView18)

        holder.binding.imageView16.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("post", post)
            }
            it.findNavController().navigate(R.id.action_postFragment_to_postDetailFragment, bundle)
        }
        // Beğeni butonuna tıklama olayı
        holder.binding.imageView17.setOnClickListener {
            val postId = post.id
            val userId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid

            if (userId != null) {
                toggleLike(holder.itemView.context, postId, userId, position)
            }
        }
    }

    private fun toggleLike(context: android.content.Context, postId: String, userId: String, position: Int) {
        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()
        val postRef = db.collection("Posts").document(postId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(postRef)
            val likes = snapshot.get("likes") as? MutableMap<String, Boolean> ?: mutableMapOf()
            val likeCount = snapshot.get("likeCount") as? Long ?: 0

            if (likes.containsKey(userId)) {
                // Kullanıcı zaten beğenmiş, beğeniyi kaldır
                likes.remove(userId)
                transaction.update(postRef, "likeCount", likeCount - 1)
            } else {
                // Kullanıcı beğenmiyor, beğeniyi ekle
                likes[userId] = true
                transaction.update(postRef, "likeCount", likeCount + 1)
            }

            transaction.update(postRef, "likes", likes)
        }.addOnSuccessListener {
            // Başarıyla beğenme durumu değiştirildi
            postList[position].likeCount = postList[position].likeCount + if (postList[position].likeCount > 0) -1 else 1
            notifyItemChanged(position)
        }.addOnFailureListener { e ->
            android.widget.Toast.makeText(context, "Beğenme durumu değiştirilemedi: ${e.localizedMessage}", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = postList.size
}

