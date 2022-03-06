package com.sodalaboratory.aphrodite.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sodalaboratory.aphrodite.AphroditeApplication
import com.sodalaboratory.aphrodite.R
import com.sodalaboratory.aphrodite.data.model.Person
import com.sodalaboratory.aphrodite.utils.LogUtil
import com.sodalaboratory.aphrodite.utils.showToast

class PersonListFragment : Fragment() {
    private val personListViewModel: PersonListViewModel by lazy {
        ViewModelProvider(this)[PersonListViewModel::class.java]
    }

    private lateinit var adapter: PersonAdapter
    private lateinit var personListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 需要设置 optionMenu
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_person_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 设置 RecyclerView 布局
        personListRecyclerView = view.findViewById(R.id.person_list_recycler_view)
        val layoutManager = LinearLayoutManager(AphroditeApplication.context)
        personListRecyclerView.layoutManager = layoutManager
        adapter = PersonAdapter(view.context, personListViewModel.personList)
        personListRecyclerView.adapter = adapter
        LogUtil.d("DEBUG", "adapter set ok")

        // 绑定监控人数变化
        personListViewModel.personCountLiveData.observe(viewLifecycleOwner) {
            // 增加
//            adapter.notifyItemInserted(0)
            LogUtil.d("DEBUG", "adapter size ${adapter.itemCount}")
            LogUtil.d("DEBUG", "observe data changed")
        }
    }

    // 顶部工具栏加载
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.actionbar, menu)
    }

    // 工具栏按钮 + 点击事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.aphrodite_add -> {
                personListViewModel.addPerson(Person())
                LogUtil.d("DEBUG", "add person")
            }
        }
        return true
    }
}


