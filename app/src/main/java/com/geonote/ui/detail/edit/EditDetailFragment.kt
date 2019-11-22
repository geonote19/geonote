package com.geonote.ui.detail

import android.os.Bundle
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.FragmentDetailBinding
import com.geonote.databinding.FragmentDetailsEditBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment

class EditDetailFragment : BaseFragment<FragmentDetailsEditBinding, EditDetailFragmentViewModel, MainActivity>() {
    override val mViewModelClass = EditDetailFragmentViewModel::class.java
    override val mLayoutId = R.layout.fragment_details_edit
    override val mBindingVariable = BR.viewmodel

    private var mNoteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = EditDetailFragmentArgs.fromBundle(arguments!!)
        mNoteId = args.noteId
    }

    override fun setupViewModel(viewModel: EditDetailFragmentViewModel) {
        super.setupViewModel(viewModel)
        viewModel.loadNote(mNoteId)
    }

    fun save(){

    }
}