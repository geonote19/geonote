package com.geonote.ui.detail

import android.os.Bundle
import android.view.View
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.FragmentDetailBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_details_edit.*

class EditDetailFragment : BaseFragment<FragmentDetailBinding, EditDetailFragmentViewModel, MainActivity>() {
    override val mViewModelClass = EditDetailFragmentViewModel::class.java
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
        mapPreview.setOnClickListener{toMapDetailFragment()}
    }

    override fun setupViewModel(viewModel: EditDetailFragmentViewModel) {
        super.setupViewModel(viewModel)
        viewModel.loadNote(mNoteId)
    }

    fun save(){

    }

    fun toMapDetailFragment() {
        mActivity.toMapDetailFragment(mNoteId)
    }
}