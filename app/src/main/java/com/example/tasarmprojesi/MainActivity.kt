package com.example.tasarmprojesi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasarmprojesi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_main)



    }
}