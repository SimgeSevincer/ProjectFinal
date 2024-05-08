package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasarmprojesi.adapter.CommentsAdapter
import com.example.tasarmprojesi.databinding.FragmentPostDetailBinding
import com.example.tasarmprojesi.model.Comment
import com.example.tasarmprojesi.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class PostDetailFragment : Fragment() {
    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var commentsAdapter: CommentsAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Post detaylarını al ve göster
        val post = arguments?.getParcelable<Post>("post")
        displayPostDetails(post)

        // RecyclerView setup
        setupRecyclerView()

        // Yorumları yükle
        post?.id?.let { loadComments(it) }

        // Yorum gönderme butonunun click listener'ını ayarla
        binding.sendCommentButton.setOnClickListener {
            val commentText = binding.commentEditText.text.toString()
            if (commentText.isNotEmpty()) {
                post?.id?.let {
                    sendComment(it, commentText)
                    binding.commentEditText.text.clear()
                }
            }
        }
    }

    private fun displayPostDetails(post: Post?) {
        binding.textViewDetail.text = post?.comment
        post?.downloadUrl?.let { url ->
            Picasso.get().load(url).into(binding.imageViewDetail)
        }
    }

    private fun setupRecyclerView() {
        commentsAdapter = CommentsAdapter(listOf())
        binding.commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentsAdapter
        }
    }

    private fun loadComments(postId: String) {
        db.collection("Posts").document(postId).collection("Comments")
            .orderBy("timestamp")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    // Log error or show to the user
                    return@addSnapshotListener
                }
                val comments = value?.map { document ->
                    document.toObject(Comment::class.java)
                } ?: listOf()
                commentsAdapter.setComments(comments)
            }
    }

    private fun sendComment(postId: String, commentText: String) {
        // Mevcut oturum açmış kullanıcının e-posta adresini al
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        userEmail?.let { email ->
            val newComment = hashMapOf(
                "userEmail" to email,
                "text" to commentText,
                "timestamp" to System.currentTimeMillis()
            )
            db.collection("Posts").document(postId).collection("Comments").add(newComment)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Yorum gönderildi!", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Yorum gönderilemedi :(", Toast.LENGTH_SHORT).show()

                }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
