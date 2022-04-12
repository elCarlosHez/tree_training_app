package com.three.tree_training_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.three.tree_training_app.databinding.ActivityTakePictureInstructionsBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class TakePictureInstructionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTakePictureInstructionsBinding

    private var latestTmpUri: Uri? = null

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                Intent(this, FillTreeInfoActivity::class.java).let {
                    it.putExtra("img",uri.toString())
                    startActivity(it)
                }
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val tempName: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val tmpFile = File.createTempFile(tempName, ".png", cacheDir).apply {
            createNewFile()
            //@TODO: Determinate if its necessary to delete the image
            deleteOnExit()
        }

        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakePictureInstructionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton.setOnClickListener {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }
}
