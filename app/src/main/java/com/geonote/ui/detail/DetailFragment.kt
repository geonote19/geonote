package com.geonote.ui.detail

import android.os.Bundle
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.FragmentDetailBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailFragmentViewModel, MainActivity>() {
    override val mViewModelClass = DetailFragmentViewModel::class.java
    override val mLayoutId = R.layout.fragment_detail
    override val mBindingVariable = BR.viewmodel

    private var mNoteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = DetailFragmentArgs.fromBundle(arguments!!)
        mNoteId = args.noteId

       // mapPreview.setOnClickListener(DetailsFragmen)
    }

    override fun setupViewModel(viewModel: DetailFragmentViewModel) {
        super.setupViewModel(viewModel)
        viewModel.loadNote(mNoteId)
    }
}