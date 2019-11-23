package com.geonote.ui.mapAllNotes

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.geonote.*
import com.geonote.data.AppRepository
import com.geonote.data.model.Marker
import com.geonote.data.model.Status
import com.geonote.data.model.db.Note
import com.geonote.data.model.toMarker
import com.geonote.databinding.FragmentAllNotesBinding
import com.geonote.helper.MapHelper
import com.geonote.helper.VerticalSpaceItemDecoration
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment
import com.geonote.utils.CustomViewOutlineProvider
import com.geonote.utils.applyDefaultOutlineProvider
import com.geonote.utils.toPixels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottomsheet.*
import kotlinx.android.synthetic.main.fragment_all_notes.*
import kotlinx.android.synthetic.main.bottomsheet.allNotesMapRecycler

class AllNotesFragmentBehavior :
    BaseFragment<FragmentAllNotesBinding, MapFragmentViewModel, MainActivity>(),
    AllNotesAdapter.ClickListener, OnMapReadyCallback {

    override val mViewModelClass = MapFragmentViewModel::class.java
    override val mLayoutId: Int = R.layout.fragment_all_notes
    override val mBindingVariable: Int = BR.viewmodel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var mAdapter: AllNotesAdapter
    private var mMapHelper: MapHelper? = null

    private val mCallback = object : MapHelper.Companion.Callback {

        override fun onMarkerClicked(markerData: Marker) {
            mViewModel.getNoteById(markerData.id) {
                App.handler.post {
                    InfoDialog.newInstance(it).show(mActivity.supportFragmentManager,
                        InfoDialog::class.java.simpleName)
                }
            }
        }

        override fun onMarkerPositionChanged(markerData: com.geonote.data.model.Marker) {
            mViewModel.updateMarkerLocation(markerData)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMapHelper = MapHelper(googleMap, context!!, mCallback)
        mViewModel.noteData.observe(this, Observer {
            if (it.status == Status.SUCCESS) {
                it.data?.let {
                    mAdapter.setData(it)
                    mMapHelper?.placeMarkers(it.map { it.toMarker() })
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allNotesMapRecycler.setHasFixedSize(true)
        mAdapter = AllNotesAdapter(mutableListOf(), this)
        allNotesMapRecycler.adapter = mAdapter

        allNotesMapRecycler.addItemDecoration(
            VerticalSpaceItemDecoration(2.toPixels())
        )

        mapView.let {
            lifecycle.addObserver(it)
            it.onCreate(savedInstanceState)
            it.getMapAsync(this)
        }

        val imageArrow = view.findViewById<View>(R.id.imageArrow)
        bottomSheet.applyDefaultOutlineProvider(CustomViewOutlineProvider.RoundedArea.TOP)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, offset: Float) {
                imageArrow.rotation = 180 * offset
            }

            override fun onStateChanged(p0: View, p1: Int) {
            }

        })
        textForBehavior.setOnClickListener {
            bottomSheetBehavior.state =
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) BottomSheetBehavior.STATE_EXPANDED
                else BottomSheetBehavior.STATE_COLLAPSED
        }
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

    override fun onClickNote(note: Note) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        mMapHelper?.setCameraPosition(note.latitude, note.longitude)
    }
}