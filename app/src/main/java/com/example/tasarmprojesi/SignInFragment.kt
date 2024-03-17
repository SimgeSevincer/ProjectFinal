package com.example.tasarmprojesi

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentSignInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SignInFragment : Fragment()  {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email =binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val height = binding.editTextHeight.text.toString()
            val weight = binding.editTextWeight.text.toString()

            //cinsiyet ve doğum tarihi yok

            if (email.equals("") || password.equals("") || name.equals("")){
                Toast.makeText(requireContext(),"Email , şifre ve isim giriniz..",Toast.LENGTH_LONG).show()
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    //succes
                    findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }

            //findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}