package com.example.tasarmprojesi.adapter

import android.os.Bundle
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
        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("post", post)
            }
            it.findNavController().navigate(R.id.action_profileFragment_to_postDetailFragment, bundle)
        }

        // Beğeni butonuna tıklama olayı
        holder.binding.imageView26.setOnClickListener {
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
            profilpostList[position].likeCount = profilpostList[position].likeCount + if (profilpostList[position].likeCount > 0) -1 else 1
            notifyItemChanged(position)
        }.addOnFailureListener { e ->
            android.widget.Toast.makeText(context, "Beğenme durumu değiştirilemedi: ${e.localizedMessage}", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}
