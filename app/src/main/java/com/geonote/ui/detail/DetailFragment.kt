package com.geonote.ui.detail

import android.os.Bundle
import android.view.View
import com.geonote.BR
import com.geonote.R
import com.geonote.data.model.Event
import com.geonote.data.model.db.Note
import com.geonote.databinding.FragmentDetailBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.DateFormat

class DetailFragment :
    BaseFragment<FragmentDetailBinding, DetailFragmentViewModel, MainActivity>() {
    override val mViewModelClass = DetailFragmentViewModel::class.java
    override val mLayoutId = R.layout.fragment_detail
    override val mBindingVariable = BR.viewmodel

    private var mNoteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = DetailFragmentArgs.fromBundle(arguments!!)
        mNoteId = args.noteId
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editNoteButton.setOnClickListener {
            toEditDetailFragment(mNoteId)
        }

        val dateFormat = DateFormat.getInstance()
        mViewModel.noteDataMutable.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Event<Note>> {
                dateTo.text = dateFormat.format(it.data?.dateTo)
                dateFrom.text = dateFormat.format(it.data?.dateFrom)
            })

    }

    private fun toEditDetailFragment(noteId: Long) {
        mActivity.toEditDetailFragment(noteId)
    }

    override fun setupViewModel(viewModel: DetailFragmentViewModel) {
        super.setupViewModel(viewModel)
        viewModel.loadNote(mNoteId)
    }

    companion object {
        const val PARAM_ID = "noteId"
    }
}