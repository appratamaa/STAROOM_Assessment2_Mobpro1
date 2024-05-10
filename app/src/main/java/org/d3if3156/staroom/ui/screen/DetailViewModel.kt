package org.d3if3156.staroom.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3156.staroom.database.StarDao
import org.d3if3156.staroom.model.Star

class DetailViewModel(private val dao: StarDao) : ViewModel() {

    fun insert(nama: String, tanggal: String, bintang: String) {
        val star = Star(
            nama = nama,
            tanggal = tanggal,
            star = bintang
        )
        viewModelScope.launch ( Dispatchers.IO ) {
            dao.insert(star)
        }
    }
    suspend fun getStar(id: Long): Star? {
        return dao.getStarById(id)
    }
    fun update(id: Long, nama: String, tanggal: String, bintang: String) {
        val star = Star(
            id = id,
            nama = nama,
            tanggal = tanggal,
            star = bintang
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(star)
        }
    }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}