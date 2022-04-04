package com.three.tree_training_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.three.tree_training_app.databinding.ActivityMainBinding
import com.three.tree_training_app.databinding.ActivitySuccessfulRegisterTreeBinding

class SuccessfulRegisterTree : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessfulRegisterTreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessfulRegisterTreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBackToBeginning.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.buttonRegister.setOnClickListener {
            var intent = Intent(this, TakePictureInstructions::class.java)
            startActivity(intent)
        }
    }
}