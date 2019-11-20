package com.geonote.ui

import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.ActivityMainBinding
import com.geonote.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override val mViewModelClass = MainActivityViewModel::class.java
    override val mLayoutId = R.layout.activity_main
    override val mBindingVariable = BR.viewmodel

}