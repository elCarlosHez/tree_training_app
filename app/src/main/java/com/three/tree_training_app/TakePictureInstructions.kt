package com.three.tree_training_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.three.tree_training_app.databinding.ActivityMainBinding
import com.three.tree_training_app.databinding.ActivityTakePictureInstructionsBinding

class TakePictureInstructions : AppCompatActivity() {
    private lateinit var binding: ActivityTakePictureInstructionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakePictureInstructionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton.setOnClickListener {
            var intent = Intent(this, SuccessfulRegisterTree::class.java)
            startActivity(intent)
        }
    }
}