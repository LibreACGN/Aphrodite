package com.sodalaboratory.aphrodite.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


/*
 Dao 接口 查询数据库用
 */
@Dao
interface PersonDao {

    @Query("SELECT * FROM Person")
    fun getAll(): MutableList<Person>

    @Query("SELECT * FROM Person WHERE uid = :uid")
    fun getUserByUID(uid: Int): Person?

    @Insert
    fun insertPerson(person: Person)

    @Insert
    fun insertAll(vararg person: Person)

    @Delete
    fun deletePerson(person: Person)
}