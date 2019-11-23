package com.geonote.ui.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.geonote.BR
import com.geonote.R
import com.geonote.data.model.Status
import com.geonote.data.model.db.Note
import com.geonote.databinding.FragmentListBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*

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
        buttonSeeOnMap.setOnClickListener {
            toMapActivity()
        }
    }

    private fun toDetailFragment(noteId: Long) {
        mActivity.toDetailFragment(noteId)
    }

    private fun toMapActivity(){
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