package com.sodalaboratory.aphrodite.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
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
    private lateinit var moreButton: ImageView
    private lateinit var addPersonButton: ImageView

    private var position = 0

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_person_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FileUtil.init()
        // ???????????? toolbar
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // ??????????????????
        moreButton = view.findViewById(R.id.more_button)
        addPersonButton = view.findViewById(R.id.add_person_button)

        // ????????????
        addPersonButton.setOnClickListener {
            val intent = Intent(AphroditeApplication.context, EditPersonActivity::class.java)
            resultLauncher.launch(intent)
        }

        // ?????? RecyclerView ??????
        personListRecyclerView = view.findViewById(R.id.person_list_recycler_view)
        val layoutManager = LinearLayoutManager(view.context)
        personListRecyclerView.layoutManager =
            WrapContentLinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        adapter = PersonAdapter(view.context, personListViewModel.personList)
        personListRecyclerView.adapter = adapter
        LogUtil.d("DEBUG", "adapter set ok")

        // ??????????????????
        when (AphroditeApplication.context.resources?.configuration?.uiMode?.and(
            Configuration.UI_MODE_NIGHT_MASK
        )) {
            Configuration.UI_MODE_NIGHT_YES -> {
                LogUtil.d("PersonListFragment", "UI_MODE_NIGHT_YES")
                // ?????????????????????
                toolbar.setBackgroundColor(ContextCompat.getColor(view.context,R.color.black))
            }
        }

        // ????????????????????????
        personListViewModel.personCountLiveData.observe(viewLifecycleOwner) { newValue ->
            if (adapter.itemCount > newValue) {
                // ????????????
                adapter.notifyItemRemoved(position)
            }
            else {
                // ????????????
                adapter.notifyItemInserted(personListViewModel.personList.size - 1)
            }
        }


        // ??????????????????
        val callback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            // ????????????
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                AlertDialog.Builder(activity as AppCompatActivity).apply {
                    setTitle("Delete")
                    setMessage("Do you want to delete this item?")
                    setCancelable(false)
                    setPositiveButton("YES") { dialog, which ->
                        // ??? ??????
                        // ??????????????? livedata ?????? adapter ????????????
                        position = viewHolder.adapterPosition
                        personListViewModel.deletePerson(position)
                    }
                    setNegativeButton("NO") { dialog, which ->
                        // ??? ??????
                        adapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(callback).attachToRecyclerView(personListRecyclerView)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // ???????????????????????????
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
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
}