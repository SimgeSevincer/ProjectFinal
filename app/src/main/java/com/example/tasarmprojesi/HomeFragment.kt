package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentHomeBinding
import com.example.tasarmprojesi.model.Kullanici
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        getDataHome()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cardView3.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_postFragment)
        }
        binding.textView11.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_articleFragment)
        }
        binding.constraintLayout2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_postAtmaFragment)
        }
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.ic_id) {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                return@setOnNavigationItemSelectedListener true
            }
            false
        }

    }


    private fun getDataHome(){
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        db.collection("Kullanici").whereEqualTo("userEmailk",currentUserEmail).orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(requireContext(),error.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        val documents2 = value.documents

                        for (document in documents2){
                            val name = document.get("name") as String
                            val useremail = document.get("userEmailk") as String
                            val downloadUrl = document.get("downloadUrlk") as String
                            val bdate = document.get("bdate") as String
                            val height = document.get("height") as String
                            val weight = document.get("weight") as String

                            val kullanici = Kullanici(useremail,downloadUrl,name,bdate,weight,height)
                            binding.textView.text = "Merhaba $name!"
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