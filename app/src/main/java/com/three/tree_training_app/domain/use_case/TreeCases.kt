package com.three.tree_training_app.domain.use_case

import com.three.tree_training_app.domain.model.TreeModel
import com.three.tree_training_app.domain.repository.TreeRepository

class TreeCases (
    private val repository: TreeRepository)
{
    suspend fun addTree(
        tree: TreeModel
    ) = repository.saveNewTree(tree)
}