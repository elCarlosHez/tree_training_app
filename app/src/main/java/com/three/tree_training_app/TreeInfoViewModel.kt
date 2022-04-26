package com.three.tree_training_app

import androidx.compose.runtime.State
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.three.tree_training_app.domain.model.TreeModel
import com.three.tree_training_app.domain.use_case.TreeCases
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
@InternalCoroutinesApi
class TreeInfoViewModel (
    private val useCases: TreeCases,
) :  ViewModel()  {
    private val _uiState = MutableStateFlow(TreeInfoUIState())
    val uiState: StateFlow<TreeInfoUIState> = _uiState.asStateFlow()
    var userLocation = MutableLiveData<LatLng>()

    private val _isTreeAdded = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isBookAddedState: State<Response<Void?>> = _isTreeAdded

    fun setUserLocation(location: LatLng) {
        userLocation.value = location
    }

    fun addTree(tree: TreeModel) {
        viewModelScope.launch {
            useCases.addTree(tree).collect { response ->
                _isTreeAdded.value = response
            }
        }
    }
}