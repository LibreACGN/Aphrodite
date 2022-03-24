package com.sodalaboratory.aphrodite.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.sodalaboratory.aphrodite.AphroditeApplication
import com.sodalaboratory.aphrodite.R
import com.sodalaboratory.aphrodite.data.model.Person
import com.sodalaboratory.aphrodite.ui.recyclerview.PersonAdapter
import com.sodalaboratory.aphrodite.ui.recyclerview.WrapContentLinearLayoutManager
import com.sodalaboratory.aphrodite.utils.FileUtil
import com.sodalaboratory.aphrodite.utils.LogUtil
import com.sodalaboratory.aphrodite.utils.showToast
import kotlin.concurrent.thread

class PersonListFragment() : Fragment() {
    private val personListViewModel: PersonListViewModel by lazy {
        ViewModelProvider(this)[PersonListViewModel::class.java]
    }

    private lateinit var adapter: PersonAdapter
    private lateinit var personListRecyclerView: RecyclerView
    private var position = 0

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

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

        // 初始化图片存储文件夹
        FileUtil.init()
        // 设置顶部 toolbar
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // 设置 RecyclerView 布局
        personListRecyclerView = view.findViewById(R.id.person_list_recycler_view)
        val layoutManager = LinearLayoutManager(view.context)
        personListRecyclerView.layoutManager =
            WrapContentLinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        adapter = PersonAdapter(view.context, personListViewModel.personList)
        personListRecyclerView.adapter = adapter
        LogUtil.d("DEBUG", "adapter set ok")

        // 绑定监控人数变化
        personListViewModel.personCountLiveData.observe(viewLifecycleOwner) { newValue ->
            if (adapter.itemCount > newValue) {
                // 人数减少
                adapter.notifyItemRemoved(position)
            }
            else {
                // 人数增加
                adapter.notifyItemInserted(personListViewModel.personList.size - 1)
            }
        }


        // 增加滑动删除
        val callback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false
                // }

            // 滑动删除
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                AlertDialog.Builder(activity as AppCompatActivity).apply {
                    setTitle("Delete")
                    setMessage("Do you want to delete this item?")
                    setCancelable(false)
                    setPositiveButton("YES") { dialog, which ->
                        // 是 删除
                        // 操作数据库 livedata 通知 adapter 数据变化
                        position = viewHolder.adapterPosition
                        personListViewModel.deletePerson(position)
                    }
                    setNegativeButton("NO") { dialog, which ->
                        // 否 还原
                        adapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                    show()
                }
            }

        }
        val helper = ItemTouchHelper(callback).attachToRecyclerView(personListRecyclerView)
    }

    // 顶部工具栏加载
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.actionbar, menu)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 处理返回的人物信息
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                "success".showToast()
                thread() {
                    val name = data?.getStringExtra("name")
                    val birthday = data?.getStringExtra("birthday")
                    val imagePath = data?.getStringExtra("image_path")
                    LogUtil.d("personData", "$name $birthday $imagePath")
                    personListViewModel.addPerson(
                        Person(name = name!!, birthday = birthday!!, imagePath = imagePath!!))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d("PersonListFragment", "onResume() called.")
        when (AphroditeApplication.context.resources?.configuration?.uiMode?.and(
            Configuration.UI_MODE_NIGHT_MASK
        )) {
            Configuration.UI_MODE_NIGHT_YES -> {
                LogUtil.d("PersonListFragment", "UI_MODE_NIGHT_YES")
                val toolbar = requireView().findViewById<MaterialToolbar>(R.id.toolbar)
                toolbar.setBackgroundColor(resources.getColor(R.color.black))
            }
        }
    }

    // 工具栏按钮 + 点击事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 准备启动活动
        when (item.itemId) {
            R.id.aphrodite_add -> {
                val intent = Intent(AphroditeApplication.context, EditPersonActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
        return true
    }
}