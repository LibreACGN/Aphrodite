package com.sodalaboratory.aphrodite.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sodalaboratory.aphrodite.AphroditeApplication
import com.sodalaboratory.aphrodite.data.model.PersonDao
import com.sodalaboratory.aphrodite.data.model.Person

/*
    数据库
 */
@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class AphroditeAppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao

    companion object {
        private var instance: AphroditeAppDatabase? = null

        @Synchronized
        fun getDatabase(): AphroditeAppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(AphroditeApplication.context,
                AphroditeAppDatabase::class.java, "aphrodite_database")
                .build().apply {
                    instance = this
                }
        }
    }
}