package org.d3if3156.staroom.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3156.staroom.database.StarDao
import org.d3if3156.staroom.ui.screen.DetailViewModel
import org.d3if3156.staroom.ui.screen.MainViewModel

class ViewModelFactory (
    private val dao: StarDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}