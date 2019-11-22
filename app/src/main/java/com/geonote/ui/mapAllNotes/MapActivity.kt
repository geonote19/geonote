package com.geonote.ui.mapAllNotes

import android.os.Bundle
import android.widget.LinearLayout
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.ActivityAllNotesBinding
import com.geonote.ui.base.BaseActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import timber.log.Timber

class MapActivity : BaseActivity<ActivityAllNotesBinding, MapActivityViewModel>() {
    override val mViewModelClass = MapActivityViewModel::class.java
    override val mLayoutId: Int = R.layout.activity_all_notes
    override val mBindingVariable: Int = BR.viewmodel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.containerForList))
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        Timber.e("FJVKSJFKVJFSKVMKSF")
    }
}