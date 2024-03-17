package com.example.tasarmprojesi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentLoginBinding
import com.example.tasarmprojesi.databinding.FragmentSkipBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        val currentUser = auth.currentUser

        if(currentUser != null){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
        }
        binding.buttonLogin.setOnClickListener {

            val email =binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()


            //cinsiyet ve doğum tarihi yok

            if (email.equals("") || password.equals("")){
                Toast.makeText(requireContext(),"Email , şifre  giriniz..", Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    //succes
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
            //findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    fun createAccountButton(view: View){

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}