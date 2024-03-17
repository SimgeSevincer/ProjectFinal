package com.example.tasarmprojesi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentPostAtmaBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class PostAtmaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentPostAtmaBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentPostAtmaBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        return binding.root

        //return inflater.inflate(R.layout.fragment_post_atma, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shareButton.setOnClickListener{
            findNavController().navigate(R.id.action_postAtmaFragment_to_postFragment)

        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}