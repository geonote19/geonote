package com.geonote.ui.mapAllNotes

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.geonote.BR
import com.geonote.R
import com.geonote.data.model.Status
import com.geonote.data.model.db.Note
import com.geonote.databinding.FragmentAllNotesBinding
import com.geonote.helper.VerticalSpaceItemDecoration
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_all_notes.*
import kotlinx.android.synthetic.main.bottomsheet.allNotesMapRecycler

class AllNotesFragmentBehavior :
    BaseFragment<FragmentAllNotesBinding, MapFragmentViewModel, MainActivity>(),
    AllNotesAdapter.ClickListener, OnMapReadyCallback {


    override fun onMapReady(p0: GoogleMap?) {

    }

    override val mViewModelClass = MapFragmentViewModel::class.java
    override val mLayoutId: Int = R.layout.fragment_all_notes
    override val mBindingVariable: Int = BR.viewmodel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var mAdapter: AllNotesAdapter

    private var clickListener: Listener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNotesMapRecycler.setHasFixedSize(true)
        mAdapter = AllNotesAdapter(mutableListOf(), this)
        allNotesMapRecycler.adapter = mAdapter

        allNotesMapRecycler.addItemDecoration(
            VerticalSpaceItemDecoration(5)
        )

        mapView.let {
            lifecycle.addObserver(it)
            it.onCreate(savedInstanceState)
            it.getMapAsync(this)
        }

        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheet))
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mViewDataBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mViewDataBinding.mapView.onLowMemory()
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