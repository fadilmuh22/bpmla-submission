package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.CancerClassificationsEntity

@Database(version = 1, entities = [CancerClassificationsEntity::class])
abstract class CancerClassificationsRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): CancerClassificationsDao

    companion object {
        @Volatile
        private var instance: CancerClassificationsRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CancerClassificationsRoomDatabase {
            if (instance == null) {
                synchronized(CancerClassificationsRoomDatabase::class.java) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            CancerClassificationsRoomDatabase::class.java,
                            "note_database",
                        )
                            .build()
                }
            }
            return instance as CancerClassificationsRoomDatabase
        }
    }
}