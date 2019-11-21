package com.geonote.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.geonote.ViewModelFactory

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel>: AppCompatActivity() {

    abstract val mViewModelClass: Class<V>
    abstract val mLayoutId: Int
    abstract val mBindingVariable: Int
    open val mNavHostId: Int? = null

    protected lateinit var mViewDataBinding: T
    protected lateinit var mViewModel: V
    protected var mNavController: NavController? = null
    private var mNavHostFragment: NavHostFragment? = null

    protected val currentFragment: BaseFragment<*, *, *>?
        get() {
            val fragment = mNavHostFragment?.childFragmentManager?.primaryNavigationFragment
                ?: supportFragmentManager.findFragmentById(android.R.id.content)
            return fragment as? BaseFragment<*, *, *>
        }

    protected open fun setupViewModel(viewModel: V) {
    }

    override fun onBackPressed() {
        if (currentFragment?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView(this, mLayoutId)
        mViewDataBinding.lifecycleOwner = this
        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(mViewModelClass)
        mViewDataBinding.setVariable(mBindingVariable, mViewModel)
        mViewDataBinding.executePendingBindings()
        if (mNavHostId != null) {
            mNavController = Navigation.findNavController(this, mNavHostId!!)
            mNavHostFragment = supportFragmentManager.findFragmentById(mNavHostId!!) as NavHostFragment
        }
        setupViewModel(mViewModel)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        currentFragment?.onActivityResult(requestCode, resultCode, data)
    }
}