package com.geonote.ui.detail

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Toast
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.FragmentDetailsEditBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_details_edit.*
import java.util.*


class EditDetailFragment :
    BaseFragment<FragmentDetailsEditBinding, EditDetailFragmentViewModel, MainActivity>() {
    override val mViewModelClass = EditDetailFragmentViewModel::class.java
    override val mLayoutId = R.layout.fragment_details_edit
    override val mBindingVariable = BR.viewmodel

    private var mNoteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = EditDetailFragmentArgs.fromBundle(arguments!!)
        mNoteId = args.noteId
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapPreview.setOnClickListener { toMapDetailFragment() }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        saveNote.setOnClickListener { save() }

    }

    override fun setupViewModel(viewModel: EditDetailFragmentViewModel) {
        super.setupViewModel(viewModel)
        viewModel.loadNote(mNoteId)
    }

    private fun save() {
        mViewModel.save()
        Toast.makeText(mActivity, "Сохранено", Toast.LENGTH_SHORT).show()
    }

    fun toMapDetailFragment() {
        mActivity.toMapDetailFragment(mNoteId)
    }
}