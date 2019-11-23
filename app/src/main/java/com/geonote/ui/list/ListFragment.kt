package com.geonote.ui.list

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.geonote.BR
import com.geonote.R
import com.geonote.data.model.Status
import com.geonote.data.model.db.Note
import com.geonote.databinding.FragmentListBinding
import com.geonote.helper.VerticalSpaceItemDecoration
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import com.geonote.utils.addDays
import com.geonote.utils.toPixels
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*

class ListFragment : BaseFragment<FragmentListBinding, ListFragmentViewModel, MainActivity>() {

    override val mViewModelClass = ListFragmentViewModel::class.java
    override val mLayoutId = R.layout.fragment_list
    override val mBindingVariable = BR.viewmodel

    private lateinit var mAdapter: ListFragmentAdapter

    private val mOnNoteClickListener = object : ListFragmentAdapter.Companion.Callback {
        override fun onItemClick(note: Note) {
            toDetailFragment(note.id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = ListFragmentAdapter(context!!, mOnNoteClickListener)
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(2.toPixels()))
        buttonSeeOnMap.setOnClickListener {
            toMapActivity()
        }
        buttonAddNew.setOnClickListener {
            var newNote = Note(
                0,
                "",
                "",
                "",
                53.899604,
                27.557117,
                100,
                Date().addDays(-1).time,
                Date().addDays(2).time,
                Color.RED
            )
            mActivity.toEditDetailFragment(newNote)
        }

        searchListFragmentEditText.addTextChangedListener(

            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    mAdapter.searchList(s.toString())

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }
            })
    }

    private fun toDetailFragment(noteId: Long) {
        mActivity.toDetailFragment(noteId)
    }

    private fun toMapActivity() {
        mActivity.toMapActivity()
    }

    override fun setupViewModel(viewModel: ListFragmentViewModel) {
        super.setupViewModel(viewModel)
        viewModel.noteDataList.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    mAdapter.setData(it.data!!)
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {

                }
            }
        })
    }

    override fun onResume() {
        mViewModel.load()
        super.onResume()
    }
}