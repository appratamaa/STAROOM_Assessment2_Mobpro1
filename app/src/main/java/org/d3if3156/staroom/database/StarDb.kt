package org.d3if3156.staroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Star::class], version = 1, exportSchema = false)
abstract class StarDb : RoomDatabase() {

    abstract val dao: StarDao

    companion object {
        @Volatile
        private var INSTANCE: StarDb? = null
        fun getInstance(context: Context): StarDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StarDb::class.java,
                        "star.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}