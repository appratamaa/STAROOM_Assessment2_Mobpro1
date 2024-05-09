package org.d3if3156.staroom.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StarDao {

    @Insert
    suspend fun insert(star: Star)

    @Update
    suspend fun update(star: Star)

    @Query("SELECT * FROM star ORDER BY id DESC")
    fun getStar(): Flow<List<Star>>

    @Query("SELECT * FROM star WHERE id = :id")
    suspend fun getStarById(id: Long): Star

    @Query("DELETE FROM star WHERE id = :id")
    suspend fun deleteById(id: Long)
}