package com.three.tree_training_app.domain.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.three.tree_training_app.Response
import com.three.tree_training_app.domain.model.TreeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class TreeRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore = Firebase.firestore,
    private val ioDispatcher: CoroutineDispatcher
): TreeRepository {
    override suspend fun saveNewTree(tree: TreeModel) {
        withContext(ioDispatcher) {
            // Add a new document with a generated ID
            database.collection("trees")
                .add(tree)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
        }
    }
}