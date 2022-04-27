package com.three.tree_training_app

data class TreeModel (
    val scientificName: String = "",
    val commonName: String = "",
    val height: Float = 0.0f,
    val diameter: Float = 0.0f,
    val risk: String = "",
    val state: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)
