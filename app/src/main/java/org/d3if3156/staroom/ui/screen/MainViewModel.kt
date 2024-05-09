package org.d3if3156.staroom.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3156.staroom.database.StarDao
import org.d3if3156.staroom.model.Star

class MainViewModel(dao: StarDao) : ViewModel(){

    val data: StateFlow<List<Star>> = dao.getStar().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()

    )
}