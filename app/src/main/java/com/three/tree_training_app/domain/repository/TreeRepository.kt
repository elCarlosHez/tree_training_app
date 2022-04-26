package com.three.tree_training_app.domain.repository

import com.three.tree_training_app.Response
import com.three.tree_training_app.domain.model.TreeModel
import kotlinx.coroutines.flow.Flow

interface TreeRepository {
    suspend fun saveNewTree(tree: TreeModel): Flow<Response<Void?>>
}