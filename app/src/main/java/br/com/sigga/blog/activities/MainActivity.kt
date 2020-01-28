package br.com.sigga.blog.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import br.com.sigga.blog.R
import br.com.sigga.blog.databinding.ActivityMainBinding
import br.com.sigga.blog.fragments.BaseFragment
import br.com.sigga.blog.fragments.PostFragment
import br.com.sigga.blog.viewmodels.activities.MainViewModel

class MainActivity : BaseFragmentActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
    }

    private val viewModel: MainViewModel by lazy { loadViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initComponents() {
        setSupportActionBar(binding.toolbar)

        binding.viewModel = viewModel

        binding.viewExitMenu.setOnClickListener {
            finish()
        }
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        openFragment(BaseFragment.newInstance(PostFragment::class, this))
    }


    private fun loadViewModel(): MainViewModel =
        ViewModelProviders.of(this, MainViewModel.Factory()).get(MainViewModel::class.java)

    //region ~~~~ OnFragmentActivityActionListener Interface ~~~~
    override fun setToolbarTitle(titleResource: Int) {
        super.setToolbarTitle(titleResource)
        toogleToolbarLogo(false)
    }

    override fun toogleToolbarLogo(isVisible: Boolean) {
        if (isVisible) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
        } else {
            supportActionBar?.setDisplayShowTitleEnabled(true)
        }
    }

    override fun toogleNavigationBar(isLocked: Boolean) {
        if (isLocked) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }
    //endregion

}