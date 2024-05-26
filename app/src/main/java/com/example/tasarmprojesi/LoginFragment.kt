package com.example.tasarmprojesi

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentLoginBinding
import com.example.tasarmprojesi.databinding.FragmentSkipBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth





class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    //
    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result.data
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            e.printStackTrace()
            e.message?.let { longToastShow(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        //auth = Firebase.auth

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




        //

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()



        /*
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
        */
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.googleSignInBtn.setOnClickListener {
            if (isConnected(requireContext())) {
                googleSignInClient.signOut().addOnCompleteListener {
                    googleSignInClient.revokeAccess().addOnCompleteListener {
                        val signInIntent = googleSignInClient.signInIntent
                        googleSignInLauncher.launch(signInIntent)
                    }
                }
            } else {
                longToastShow("No Internet Connection!")
            }
        }


    }
//
private fun isConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
//
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                if (result.additionalUserInfo?.isNewUser == true) {
                    findNavController().navigate(R.id.action_loginFragment_to_kullaniciDetayFragment)
                } else {
                    findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                it.message?.let { it1 -> longToastShow(it1) }
            }
    }

    private fun longToastShow(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}