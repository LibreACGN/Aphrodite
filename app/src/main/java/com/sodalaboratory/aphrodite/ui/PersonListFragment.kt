package com.sodalaboratory.aphrodite.ui


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.sodalaboratory.aphrodite.AphroditeApplication
import com.sodalaboratory.aphrodite.R
import com.sodalaboratory.aphrodite.data.model.Person
import com.sodalaboratory.aphrodite.utils.FileUtil
import com.sodalaboratory.aphrodite.utils.LogUtil
import com.sodalaboratory.aphrodite.utils.showToast
import kotlin.concurrent.thread

class PersonListFragment : Fragment() {
    private val personListViewModel: PersonListViewModel by lazy {
        ViewModelProvider(this)[PersonListViewModel::class.java]
    }

    private lateinit var adapter: PersonAdapter
    private lateinit var personListRecyclerView: RecyclerView

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
        personListViewModel.personCountLiveData.observe(viewLifecycleOwner) {
            // 末端增加新人物
//            adapter.notifyItemInserted(personListViewModel.personList.size - 1)
            LogUtil.d("DEBUG", "adapter size ${adapter.itemCount}")
            LogUtil.d("DEBUG", "observe data changed")
        }
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