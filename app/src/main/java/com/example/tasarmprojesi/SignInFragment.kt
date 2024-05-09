package com.example.tasarmprojesi

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



class SignInFragment : Fragment()  {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private lateinit var auth: FirebaseAuth

    private lateinit var  activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    var selectedPicture : Uri? = null
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
            //val name = binding.editTextName.text.toString()
            val email =binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            //val height = binding.editTextHeight.text.toString()
            //val weight = binding.editTextWeight.text.toString()

            //cinsiyet ve doğum tarihi yok

            if (email.equals("") || password.equals("")){
                Toast.makeText(requireContext(),"Email ve şifre giriniz..",Toast.LENGTH_LONG).show()
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    //succes
                    findNavController().navigate(R.id.action_signInFragment_to_kullaniciDetayFragment)
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