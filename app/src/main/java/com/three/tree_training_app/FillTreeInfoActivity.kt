package com.three.tree_training_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.three.tree_training_app.databinding.ActivityFillTreeInfoBinding


class FillTreeInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillTreeInfoBinding
    private val model: TreeInfoViewModel by viewModels()
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseFirestore
    private var riskSelected = "low"
    private var stateSelected = "low"
    private lateinit var documentReference: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillTreeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = Firebase.storage
        database = Firebase.firestore

        val uri = intent.getStringExtra("img")
        // Create the tree document in firebase to get an id
        documentReference = database.collection("trees").document()
        
        uri?.let {
            binding.treePreview.setImageURI(Uri.parse(uri))
            // upload the tree image to firebase
            saveTreeImage(it, documentReference.id)
        }

        binding.button.setOnClickListener {
            val tree = TreeModel(
                commonName = binding.commonName.text.toString(),
                scientificName = binding.scientificName.text.toString(),
                diameter = binding.diameter.text.toString().toFloat(),
                height = binding.height.text.toString().toFloat(),
                risk = riskSelected,
                state = stateSelected,
                latitude = model.userLocation.value!!.latitude,
                longitude = model.userLocation.value!!.longitude,
            )

            saveNewTree(tree, documentReference)
        }
    }

    private fun saveNewTree(tree: TreeModel, document: DocumentReference) {
        document.set(tree)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "DocumentSnapshot added with ID: ${document.id}")
                    // We send the user to the next activity after we upload the tree's info
                    startActivity(Intent(this, SuccessfulRegisterTreeActivity::class.java))
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
    }

    private fun saveTreeImage(imageUri: String, fileName: String) {
        val storageRef = storage.reference
        val file = Uri.parse(imageUri)
        val riversRef = storageRef.child("images/${fileName}")
        val uploadTask = riversRef.putFile(file)

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener { e ->
            Log.w("FireStorage", "Error creating an image", e)
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            Log.w("FireStorage", "Image uploaded")
        }
    }

    fun onRiskRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                binding.riskLow.id ->
                    if (checked) {
                        riskSelected = "low"
                    }
                binding.riskMedium.id ->
                    if (checked) {
                        riskSelected = "medium"
                    }
                binding.riskHigh.id -> {
                    if (checked) {
                        riskSelected = "high"
                    }
                }
            }
        }
    }

    fun onStateRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                binding.stateLow.id ->
                    if (checked) {
                        stateSelected = "low"
                    }
                binding.stateMedium.id ->
                    if (checked) {
                        stateSelected = "medium"
                    }
                binding.stateHigh.id -> {
                    if (checked) {
                        stateSelected = "high"
                    }
                }
            }
        }
    }
}
