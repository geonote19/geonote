package com.geonote.ui.detail.edit

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.geonote.BR
import com.geonote.R
import com.geonote.data.model.db.Note
import com.geonote.databinding.FragmentDetailsEditBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import com.geonote.utils.addDays
import kotlinx.android.synthetic.main.fragment_details_edit.*
import java.util.*


class EditDetailFragment :
    BaseFragment<FragmentDetailsEditBinding, EditDetailFragmentViewModel, MainActivity>() {
    override val mViewModelClass = EditDetailFragmentViewModel::class.java
    override val mLayoutId = R.layout.fragment_details_edit
    override val mBindingVariable = BR.viewmodel

    private var mNote = Note(
        1,
        "Цветы на др ксюхи",
        "Структурал",
        "",
        53.899604,
        27.557117,
        100,
        Date().addDays(-1).time,
        Date().addDays(2).time
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = EditDetailFragmentArgs.fromBundle(arguments!!)
        mNote = args.note
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapPreview.setOnClickListener { toMapDetailFragment() }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        saveNote.setOnClickListener { save() }

    }

    override fun setupViewModel(viewModel: EditDetailFragmentViewModel) {
        super.setupViewModel(viewModel)
        viewModel.note.postValue(mNote)
    }

    private fun save() {
        mNote.title = title.editableText.toString()
        mNote.description = description.editableText.toString()
        mViewModel.save(mNote)
        Toast.makeText(mActivity, "Сохранено", Toast.LENGTH_SHORT).show()
    }

    fun toMapDetailFragment() {
        mActivity.toMapDetailFragment(mNote.id)
    }
}