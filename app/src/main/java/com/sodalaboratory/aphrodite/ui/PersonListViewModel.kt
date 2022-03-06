package com.sodalaboratory.aphrodite.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sodalaboratory.aphrodite.data.db.AphroditeAppDatabase
import com.sodalaboratory.aphrodite.data.model.Person
import com.sodalaboratory.aphrodite.data.model.PersonDao
import com.sodalaboratory.aphrodite.utils.showToast
import kotlin.concurrent.thread

class PersonListViewModel : ViewModel() {
    val personListLiveData = MutableLiveData<MutableList<Person>>()
    var personCount =  0
    private lateinit var personDao: PersonDao
    // List 初始化
    init {
        personListLiveData.value = mutableListOf()
        thread {
            personDao = AphroditeAppDatabase.getDatabase().personDao()
            personListLiveData.postValue(personDao.getAll())

        }
    }

    // 加入人物到数据库中
    fun addPerson(person: Person) {
        val persons = personListLiveData.value ?: mutableListOf()
        // 添加人物
        thread {
            persons.add(person)
            personListLiveData.postValue(persons)
            personDao.insertPerson(person)
        }
        "添加成功".showToast()
    }

    // 添加默认人物
    fun addDefaultPerson() {
        addPerson(Person())

    }

    // 清除所有人物
    fun clearAphroditePersonList() {
    }
}