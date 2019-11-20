package com.geonote.ui.detail

import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.FragmentDetailBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailFragmentViewModel, MainActivity>() {
    override val mViewModelClass = DetailFragmentViewModel::class.java
    override val mLayoutId = R.layout.fragment_detail
    override val mBindingVariable = BR.viewmodel
}