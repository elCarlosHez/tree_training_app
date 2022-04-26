package com.three.tree_training_app

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.three.tree_training_app.databinding.ActivityFillTreeInfoBinding
import com.three.tree_training_app.domain.model.TreeModel
import kotlinx.coroutines.launch


class FillTreeInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillTreeInfoBinding
    private var riskSelected = 0
    private var stepSelected = 0

    //private val viewModel: TreeInfoViewModel by viewModels()

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            riskSelected = view.getId()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillTreeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = intent.getStringExtra("img")
        binding.treePreview.setImageURI(Uri.parse(uri))

        binding.button.setOnClickListener {
            val tree = TreeModel(
                commonName = binding.commonName.text.toString(),
                scientificName = binding.scientificName.text.toString(),
                diameter = binding.diameter.text.toString().toFloat(),
                height = binding.height.text.toString().toFloat(),
            )

            val database: FirebaseFirestore = Firebase.firestore
            database.collection("trees")
                .add(tree)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
        }

        //lifecycleScope.launch {
        //    repeatOnLifecycle(Lifecycle.State.STARTED) {
        //
        //    }
        //}
    }
}
