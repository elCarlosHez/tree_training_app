package com.three.tree_training_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
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
    private var riskSelected = RadioLevelsEnum.LOW.levelName
    private var stateSelected = RadioLevelsEnum.LOW.levelName
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

        model.getUserLocation().observe(this) {latLng: LatLng ->
            binding.button.setOnClickListener {
                val tree = TreeModel(
                    commonName = binding.commonName.text.toString(),
                    scientificName = binding.scientificName.text.toString(),
                    diameter = binding.diameter.text.toString().toFloat(),
                    height = binding.height.text.toString().toFloat(),
                    risk = riskSelected,
                    state = stateSelected,
                    latitude = latLng.latitude,
                    longitude = latLng.longitude,
                )

                saveNewTree(tree, documentReference)
            }
        }

        binding.risk.setOnCheckedChangeListener{ group, checkedId ->
            if(group.id == binding.risk.id){
                riskSelected = when(checkedId) {
                    binding.riskLow.id -> RadioLevelsEnum.LOW.levelName
                    binding.riskMedium.id -> RadioLevelsEnum.MEDIUM.levelName
                    binding.riskHigh.id -> RadioLevelsEnum.HIGH.levelName
                    else -> RadioLevelsEnum.LOW.levelName
                }
            }
        }

        binding.stateGroup.setOnCheckedChangeListener{ group, checkedId ->
            if(group.id == binding.stateGroup.id){
                stateSelected = when(checkedId) {
                    binding.stateLow.id -> RadioLevelsEnum.LOW.levelName
                    binding.stateMedium.id -> RadioLevelsEnum.MEDIUM.levelName
                    binding.stateHigh.id -> RadioLevelsEnum.HIGH.levelName
                    else -> RadioLevelsEnum.LOW.levelName
                }
            }
        }
    }

    private fun saveNewTree(tree: TreeModel, document: DocumentReference) {
        document.set(tree)
                .addOnSuccessListener {
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
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            Log.w("FireStorage", "Image uploaded")
        }
    }
}
