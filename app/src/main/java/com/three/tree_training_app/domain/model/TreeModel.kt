package com.three.tree_training_app.domain.model

data class TreeModel (
    val scientificName: String = "",
    val commonName: String = "",
    val height: Float = 0.0f,
    val diameter: Float = 0.0f,
    val risk: Int = 0,
    val state: Int = 0,
)