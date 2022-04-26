package com.three.tree_training_app

data class TreeInfoUIState (
    val scientificName: String = "",
    val commonName: String = "",
    val height: Float = 0.0f,
    val diameter: Float = 0.0f,
    val risk: Int = 0,
    val state: Int = 0,
    val dataSubmitted: Boolean = false,
)