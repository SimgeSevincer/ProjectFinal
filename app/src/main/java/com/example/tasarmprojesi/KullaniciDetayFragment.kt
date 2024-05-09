package com.example.tasarmprojesi

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.tasarmprojesi.databinding.FragmentKullaniciDetayBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.Calendar
import java.util.UUID


class KullaniciDetayFragment : Fragment() {

    private var _binding: FragmentKullaniciDetayBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var auth: FirebaseAuth

    private lateinit var  activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    var selectedPicture : Uri? = null

    private lateinit var firestore : FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentKullaniciDetayBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage

        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_kullanici_detay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLauncher()

        binding.editTextDOB.setOnClickListener {
            showDatePickerDialog()
        }
        /*
        binding.radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButtonId = binding.radioGroupGender.checkedRadioButtonId
            val selectedRadioButton = binding.radioGroupGender.findViewById<RadioButton>(selectedRadioButtonId)
            val selectedOptionText = selectedRadioButton?.text.toString()
            println("Seçilen RadioButton ID'si: $selectedRadioButtonId")
            println("Seçilen seçenek: $selectedOptionText")
        } */


        /*
        binding.btnSignUp2.setOnClickListener {

            val uuid = UUID.randomUUID()
            val imageName = "$uuid.jpg"

            val reference = storage.reference
            val imageReference = reference.child("imageskullanici").child(imageName)



            if (selectedPicture != null){
                imageReference.putFile(selectedPicture!!).addOnSuccessListener{

                    val uploadPictureReference = storage.reference.child("imageskullanici").child(imageName)
                    uploadPictureReference.downloadUrl.addOnSuccessListener {

                        val downloadUrl = it.toString()
                        if(auth.currentUser != null){
                            val kullaniciMap = hashMapOf<String, Any>()
                            kullaniciMap.put("downloadUrlk",downloadUrl)
                            kullaniciMap.put("name",binding.editTextName.text.toString())
                            kullaniciMap.put("bdate",binding.editTextDOB.text.toString())
                            kullaniciMap.put("height",binding.editTextHeight.text.toString())
                            kullaniciMap.put("weight",binding.editTextWeight.text.toString())
                            kullaniciMap.put("userEmailk", auth.currentUser!!.email!!)
                            kullaniciMap.put("date", Timestamp.now())

                            firestore.collection("Kullanici").add(kullaniciMap).addOnSuccessListener {
                                findNavController().navigate(R.id.action_kullaniciDetayFragment_to_profileFragment)

                            }.addOnFailureListener {
                                Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }



            //val action = kullaniciDetayFragmentDirections.actionKullaniciDetayFragmentToProfilFragment3()
            //Navigation.findNavController(it).navigate(action)
        }
*/

        binding.btnSignUp2.setOnClickListener {

            val name = binding.editTextName.text.toString()
            val dob = binding.editTextDOB.text.toString()
            val height = binding.editTextHeight.text.toString()
            val weight = binding.editTextWeight.text.toString()

            // Boş alanları kontrol et ve gerekli mesajları göster
            if (name.isEmpty() || dob.isEmpty() || height.isEmpty() || weight.isEmpty()) {
                Toast.makeText(requireContext(), "Tüm alanların doldurulması gerekmektedir!", Toast.LENGTH_SHORT).show()
            } else {
                val uuid = UUID.randomUUID()
                val imageName = "$uuid.jpg"

                val reference = storage.reference
                val imageReference = reference.child("imageskullanici").child(imageName)

                if (selectedPicture != null){
                    imageReference.putFile(selectedPicture!!).addOnSuccessListener{

                        val uploadPictureReference = storage.reference.child("imageskullanici").child(imageName)
                        uploadPictureReference.downloadUrl.addOnSuccessListener {

                            val downloadUrl = it.toString()
                            if(auth.currentUser != null){
                                val kullaniciMap = hashMapOf<String, Any>()
                                kullaniciMap.put("downloadUrlk",downloadUrl)
                                kullaniciMap.put("name",name)
                                kullaniciMap.put("bdate",dob)
                                kullaniciMap.put("height",height)
                                kullaniciMap.put("weight",weight)
                                kullaniciMap.put("userEmailk", auth.currentUser!!.email!!)
                                kullaniciMap.put("date", Timestamp.now())

                                firestore.collection("Kullanici").add(kullaniciMap).addOnSuccessListener {
                                    findNavController().navigate(R.id.action_kullaniciDetayFragment_to_profileFragment)

                                }.addOnFailureListener {
                                    Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()
                                }
                            }
                        }

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }





        binding.imageView.setOnClickListener {
            //openGallery()
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES)) {
                    Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                        View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }).show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }
        }




    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.editTextDOB.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


    private  fun registerLauncher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == AppCompatActivity.RESULT_OK){
                val intentFromResult = result.data
                if (intentFromResult != null){
                    selectedPicture =   intentFromResult.data
                    selectedPicture?.let{
                        binding.imageView.setImageURI(it)
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){
                //permission granted
                val intentToGallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
            else{
                //permission denied
                Toast.makeText(requireContext(),"Permission needed!", Toast.LENGTH_LONG).show()
            }

        }
    }




}