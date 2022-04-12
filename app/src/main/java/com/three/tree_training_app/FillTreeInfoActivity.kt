package com.three.tree_training_app

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.three.tree_training_app.databinding.ActivityFillTreeInfoBinding
import com.three.tree_training_app.databinding.ActivityTakePictureInstructionsBinding

class FillTreeInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillTreeInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillTreeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = intent.getStringExtra("img")
        binding.treePreview.setImageURI(Uri.parse(uri))
    }
}