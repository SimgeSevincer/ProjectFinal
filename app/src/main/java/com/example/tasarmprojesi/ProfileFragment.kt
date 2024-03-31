package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasarmprojesi.adapter.ProfilRecylerAdapter
import com.example.tasarmprojesi.databinding.FragmentPostBinding
import com.example.tasarmprojesi.databinding.FragmentProfileBinding
import com.example.tasarmprojesi.model.Post
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore


class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    private lateinit var db : FirebaseFirestore
    private lateinit var postArrayList : ArrayList<Post>

    private lateinit var profilAdapter : ProfilRecylerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        db = Firebase.firestore

        postArrayList= ArrayList<Post>()

        getDataA()

        binding.recylerView.layoutManager = LinearLayoutManager(requireContext())
        profilAdapter = ProfilRecylerAdapter(postArrayList)
        binding.recylerView.adapter = profilAdapter

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUserEmail2 = FirebaseAuth.getInstance().currentUser?.email
        binding.textView34.text = currentUserEmail2

        binding.imageView8.setOnClickListener {

            auth.signOut()  //kullanıcı çıkışı
            findNavController().navigate(R.id.action_profileFragment_to_skipFragment)
        }
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.ic_home) {
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                return@setOnNavigationItemSelectedListener true
            }
            false
        }


    }

    private fun getDataA(){

        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        db.collection("Posts").whereEqualTo("userEmail",currentUserEmail).orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(requireContext(),error.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        val documents = value.documents

                        postArrayList.clear()

                        for (document in documents){
                            val comment = document.get("comment") as String
                            val useremail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            println(comment)

                            val post = Post(useremail,comment,downloadUrl)
                            postArrayList.add(post)
                        }

                        profilAdapter.notifyDataSetChanged()
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