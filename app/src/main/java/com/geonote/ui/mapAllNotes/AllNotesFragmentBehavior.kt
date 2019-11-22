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
import com.geonote.data.model.toMarker
import com.geonote.databinding.FragmentAllNotesBinding
import com.geonote.helper.MapHelper
import com.geonote.helper.VerticalSpaceItemDecoration
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import com.geonote.utils.toPixels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottomsheet.*
import kotlinx.android.synthetic.main.fragment_all_notes.*

class AllNotesFragmentBehavior :
    BaseFragment<FragmentAllNotesBinding, MapFragmentViewModel, MainActivity>(),
    AllNotesAdapter.ClickListener, OnMapReadyCallback {

    override val mViewModelClass = MapFragmentViewModel::class.java
    override val mLayoutId: Int = R.layout.fragment_all_notes
    override val mBindingVariable: Int = BR.viewmodel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var mAdapter: AllNotesAdapter
    private var mMapHelper: MapHelper? = null

    private var clickListener: Listener? = null

    override fun onMapReady(googleMap: GoogleMap) {
        mMapHelper = MapHelper(googleMap, context!!)
        mViewModel.noteData.observe(this, Observer {
            if (it.status == Status.SUCCESS) {
                it.data?.let {
                    mAdapter.setData(it)
                    mMapHelper!!.placeMarkers(it.map { it.toMarker() })
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNotesMapRecycler.setHasFixedSize(true)
        mAdapter = AllNotesAdapter(mutableListOf(), this)
        allNotesMapRecycler.adapter = mAdapter

        mapView.let {
            lifecycle.addObserver(it)
            it.onCreate(savedInstanceState)
            it.getMapAsync(this)
        }

        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheet))
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        allNotesMapRecycler.addItemDecoration(
            VerticalSpaceItemDecoration(2.toPixels())
        )
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