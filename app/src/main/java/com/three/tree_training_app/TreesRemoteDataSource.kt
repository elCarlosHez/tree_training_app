package com.three.tree_training_app

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.three.tree_training_app.domain.model.TreeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TreesRemoteDataSource (
    private val storage: FirebaseStorage = Firebase.storage,
    private val database: FirebaseFirestore = Firebase.firestore,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun saveNewTree(tree: TreeModel, imageUri: String) {
        withContext(ioDispatcher) {
            // Add a new document with a generated ID
             database.collection("trees")
                .add(tree)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "DocumentSnapshot added with ID: ${documentReference.id}")
                    // @TODO Should be part of another function
                    val storageRef = storage.reference
                    val file = Uri.parse(imageUri)
                    val riversRef = storageRef.child("images/${file.lastPathSegment}")
                    val uploadTask = riversRef.putFile(file)

                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener { e ->
                        Log.w("FireStorage", "Error creating an image", e)
                    }.addOnSuccessListener { taskSnapshot ->
                        // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                        // ...
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
        }
    }

    suspend fun saveTreeImage(imageUri: String, fileName: String) {
        withContext(ioDispatcher) {
            val storageRef = storage.reference
            val file = Uri.parse(imageUri)
            val riversRef = storageRef.child("images/${file.lastPathSegment}")
            val uploadTask = riversRef.putFile(file)

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener { e ->
                Log.w("FireStorage", "Error creating an image", e)
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            }

        }
    }
}