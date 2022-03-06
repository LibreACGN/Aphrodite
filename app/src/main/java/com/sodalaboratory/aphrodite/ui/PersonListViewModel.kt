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
    var personCountLiveData = MutableLiveData<Int>()
    private lateinit var personDao: PersonDao
    private lateinit var personList: MutableList<Person>
    // List 初始化
    init {
        personCountLiveData.value = 0
        thread {
            personDao = AphroditeAppDatabase.getDatabase().personDao()
            personList = personDao.getAll()
            personCountLiveData.postValue(personList.size)
        }
    }

    // 加入人物到数据库中
    fun addPerson(person: Person) {
        personList.add(person)
        thread {
            personDao.insertPerson(person)
            personCountLiveData.postValue(personList.size)
        }

    }
}