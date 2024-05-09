package org.d3if3156.staroom.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "star")
data class Star(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val tanggal: String,
    val star: String,
)
