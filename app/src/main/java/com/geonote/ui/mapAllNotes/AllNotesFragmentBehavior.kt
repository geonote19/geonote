package com.geonote.ui.mapAllNotes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geonote.BR
import com.geonote.R
import com.geonote.data.model.Status
import com.geonote.data.model.db.Note
import com.geonote.databinding.FragmentRecyclerBinding
import com.geonote.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_recycler.*

class AllNotesFragmentBehavior :
    BaseFragment<FragmentRecyclerBinding, MapFragmentViewModel, MapActivity>(),
    AllNotesAdapter.ClickListener {

    override val mViewModelClass = MapFragmentViewModel::class.java
    override val mLayoutId: Int = R.layout.fragment_recycler
    override val mBindingVariable: Int = BR.viewmodel

    private lateinit var mAdapter: AllNotesAdapter

    private var clickListener: Listener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNotesMapRecycler.setHasFixedSize(true)
        mAdapter = AllNotesAdapter(mutableListOf(), this)
        allNotesMapRecycler.adapter = mAdapter
    }

    override fun setupViewModel(viewModel: MapFragmentViewModel) {
        super.setupViewModel(viewModel)
        viewModel.noteData.observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                }
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    mAdapter.setData(it.data!!)
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            clickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onClickNote(item: Note) {
        clickListener?.onCarNote(item)
    }

    interface Listener {
        fun onCarNote(item: Note)
    }
}