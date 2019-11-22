package com.geonote.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.FragmentDetailBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*

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

       // mapPreview.setOnClickListener(DetailsFragmen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editNoteButton.setOnClickListener {
            toEditDetailFragment(mNoteId)
        }
    }

    private fun toEditDetailFragment(noteId: Long) {
        mActivity.toEditDetailFragment(noteId)
    }

    override fun setupViewModel(viewModel: DetailFragmentViewModel) {
        super.setupViewModel(viewModel)
        viewModel.loadNote(mNoteId)
    }

    fun save() {

    }
}