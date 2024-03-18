package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentArticleBinding
import com.example.tasarmprojesi.databinding.FragmentPostBinding
import com.example.tasarmprojesi.model.Post
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postArrayList : ArrayList<Post>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        db = Firebase.firestore

        postArrayList= ArrayList<Post>()

        getData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView1.setOnClickListener {
            findNavController().navigate(R.id.action_postFragment_to_postAtmaFragment)
        }



        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView2
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.ic_id) {
                findNavController().navigate(R.id.action_postFragment_to_profileFragment)
                return@setOnNavigationItemSelectedListener true
            }else if(item.itemId == R.id.ic_home) {
                findNavController().navigate(R.id.action_postFragment_to_homeFragment)
                return@setOnNavigationItemSelectedListener true
            }
            false
        }
    }

    private fun getData(){
        db.collection("Posts").addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        val documents = value.documents

                        for (document in documents){
                            val comment = document.get("comment") as String
                            val useremail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            println(comment)

                            val post = Post(useremail,comment,downloadUrl)
                            postArrayList.add(post)
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}