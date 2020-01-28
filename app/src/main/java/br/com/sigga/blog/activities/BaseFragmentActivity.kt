package br.com.sigga.blog.activities

import androidx.fragment.app.Fragment
import br.com.sigga.blog.R
import br.com.sigga.blog.activities.listeners.OnFragmentActivityActionListener
import br.com.sigga.blog.fragments.BaseFragment

open class BaseFragmentActivity : BaseActivity(), OnFragmentActivityActionListener {

    private var backButtonEnable: Boolean = true

    override fun onBackPressed() {
        if (backButtonEnable) {
            popFragment(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        popFragment(true)
        return true
    }

    private fun popFragment(closesActivity: Boolean = false) {
        runOnUiThread {
            supportFragmentManager.popBackStackImmediate()

            for (frag in supportFragmentManager.fragments.reversed()) {
                if (BaseFragment::class.java.isAssignableFrom(frag::class.java)) {
                    frag.onResume()
                    (frag as? BaseFragment)?.executePendingActionsNotResumed()
                    break
                }
            }

            if (closesActivity && supportFragmentManager.fragments.isEmpty()) {
                finish()
            }
        }
    }

    //region ~~~~ OnFragmentActivityActionListener Interface ~~~~
    override fun openFragment(fragment: Fragment, dismissBack: Boolean) {
        val tag = System.currentTimeMillis().toString()

        if (dismissBack) popFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun closeCurrentFragment() {
        popFragment(true)
    }

    override fun setToolbarVisibility(isVisible: Boolean) {
        if (!isVisible) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.show()
        }
    }

    override fun setToolbarTitle(titleResource: Int) {
        supportActionBar?.setTitle(titleResource)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    override fun backButtonEnable(isEnable: Boolean) {
        backButtonEnable = isEnable
    }

    override fun toggleHomeButton(isVisible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isVisible)
    }
    //endregion

}
