package com.sodalaboratory.aphrodite.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sodalaboratory.aphrodite.data.db.AphroditeAppDatabase
import com.sodalaboratory.aphrodite.data.model.Person
import com.sodalaboratory.aphrodite.data.model.PersonDao
import com.sodalaboratory.aphrodite.utils.FileUtil
import java.text.FieldPosition
import kotlin.concurrent.thread

class PersonListViewModel : ViewModel() {
    var personCountLiveData = MutableLiveData<Int>()
    private lateinit var personDao: PersonDao
    var personList: MutableList<Person> = mutableListOf()
    init {
        personCountLiveData.value = 0
        thread {
            personDao = AphroditeAppDatabase.getDatabase().personDao()
            val persons = personDao.getAll()
            for (person in persons) personList.add(person)
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

    fun deletePerson(position: Int) {
        // 清除
        thread {
            // 清除数据库
            personDao.deletePerson(personList[position])
            // 修改值
            personCountLiveData.postValue(personList.size - 1)
            // 清除头像文件
            FileUtil.delete(personList[position].name)
            // 清除列表
            personList.removeAt(position)

        }
        //
    }
}