package com.geonote.ui.mapAllNotes

import android.os.Bundle
import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.ActivityAllNotesBinding
import com.geonote.ui.base.BaseActivity

class MapActivity : BaseActivity<ActivityAllNotesBinding, MapActivityViewModel>() {
    override val mViewModelClass = MapActivityViewModel::class.java
    override val mLayoutId: Int = R.layout.activity_all_notes
    override val mBindingVariable: Int = BR.viewmodel
}