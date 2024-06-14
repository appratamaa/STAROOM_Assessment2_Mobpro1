package org.d3if3156.staroom.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.d3if3156.staroom.database.StarDao
import org.d3if3156.staroom.model.Sky
import org.d3if3156.staroom.model.Star
import org.d3if3156.staroom.network.ApiStatus
import org.d3if3156.staroom.network.StarApi

class MainViewModel(dao: StarDao) : ViewModel(){

    val data: StateFlow<List<Star>> = dao.getStar().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()

    )

    var datasky = mutableStateOf(emptyList<Sky>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set
    init {
        retrieveData()
    }
    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
//               datasky.value = StarApi.service.getStar()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
}