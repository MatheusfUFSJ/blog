package br.com.sigga.blog.activities.listeners

import androidx.fragment.app.Fragment

interface OnFragmentActivityActionListener {

    fun openFragment(fragment: Fragment, dismissBack: Boolean = false)

    fun closeCurrentFragment()

    fun setToolbarVisibility(isVisible: Boolean)

    fun setToolbarTitle(titleResource: Int)

    fun backButtonEnable(isEnable: Boolean)

    fun toggleHomeButton(isVisible: Boolean)

    fun toogleToolbarLogo(isVisible: Boolean) {
    }

    fun toogleNavigationBar(isLocked: Boolean) {
    }

}
