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
import com.example.tasarmprojesi.databinding.FragmentProfileBinding
import com.example.tasarmprojesi.model.Kullanici
import com.example.tasarmprojesi.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var profileAdapter: ProfilRecylerAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initializeFirebase()
        setupRecyclerView()
        //getDataA()
        loadData()
        return binding.root
    }

    private fun initializeFirebase() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    private fun setupRecyclerView() {
        postArrayList = ArrayList()
        profileAdapter = ProfilRecylerAdapter(postArrayList)
        binding.recylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recylerView.adapter = profileAdapter
    }

    private fun loadData() {
        val currentUserEmail = auth.currentUser?.email

        db.collection("Posts").whereEqualTo("userEmail", currentUserEmail)
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    postArrayList.clear()
                    postArrayList.addAll(value?.documents?.mapNotNull { doc ->
                        doc.toObject(Post::class.java)?.apply { id = doc.id }
                    } ?: emptyList())
                    profileAdapter.notifyDataSetChanged()



                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUserDetails()
        setupSignOut()
        setupBottomNavigationView()
    }

    private fun setupUserDetails() {
        binding.textView34.text = auth.currentUser?.email
        getDataA()

    }

    private fun setupSignOut() {
        binding.imageView8.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_skipFragment)
        }
    }


    private fun getDataA(){

        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        db.collection("Kullanici").whereEqualTo("userEmailk",currentUserEmail).orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(requireContext(),error.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        val documents = value.documents


                        for (document in documents){
                            val name = document.get("name") as String
                            val useremail = document.get("userEmailk") as String
                            val downloadUrl = document.get("downloadUrlk") as String
                            val bdate = document.get("bdate") as String
                            val height = document.get("height") as String
                            val weight = document.get("weight") as String

                            println(name+"   "+useremail)

                            val kullanici = Kullanici(useremail,downloadUrl,name,bdate,weight,height)
                            //postArrayList.add(kullanici)

                            binding.textView34.text = name
                            binding.textView33.text = bdate+" - "+weight+" - "+height
                            Picasso.get().load(downloadUrl).into(binding.imageView32)
                        }


                    }
                }
            }
        }

    }


    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_home -> {
                    findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                    true
                }
                else -> false
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}