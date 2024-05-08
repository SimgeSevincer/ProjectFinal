package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasarmprojesi.adapter.FeedRecylerAdapter
import com.example.tasarmprojesi.databinding.FragmentPostBinding
import com.example.tasarmprojesi.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var feedAdapter: FeedRecylerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        initializeFirebase()
        setupRecyclerView()
        loadData()
        return binding.root
    }

    private fun initializeFirebase() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    private fun setupRecyclerView() {
        postArrayList = ArrayList()
        feedAdapter = FeedRecylerAdapter(postArrayList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = feedAdapter
        }
    }

    private fun loadData() {
        db.collection("Posts").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                snapshot?.let { value ->
                    postArrayList.clear()
                    postArrayList.addAll(value.documents.mapNotNull { doc ->
                        doc.toObject(Post::class.java)?.apply { id = doc.id }
                    })
                    feedAdapter.notifyDataSetChanged()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView2.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_id -> {
                    findNavController().navigate(R.id.action_postFragment_to_profileFragment)
                    true
                }
                R.id.ic_home -> {
                    findNavController().navigate(R.id.action_postFragment_to_homeFragment)
                    true
                }
                else -> false
            }

        }
        binding.imageView1.setOnClickListener {
            findNavController().navigate(R.id.action_postFragment_to_postAtmaFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}