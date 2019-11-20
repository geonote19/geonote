package com.geonote.ui.list

import com.geonote.BR
import com.geonote.R
import com.geonote.databinding.FragmentListBinding
import com.geonote.ui.MainActivity
import com.geonote.ui.base.BaseFragment

class ListFragment : BaseFragment<FragmentListBinding, ListFragmentViewModel, MainActivity>() {

    override val mViewModelClass = ListFragmentViewModel::class.java
    override val mLayoutId = R.layout.fragment_list
    override val mBindingVariable = BR.viewmodel

}