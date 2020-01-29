package br.com.sigga.blog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.sigga.blog.R
import br.com.sigga.blog.adapters.AlbumAdapter
import br.com.sigga.blog.databinding.FragmentAlbumBinding
import br.com.sigga.blog.ui.managers.CustomDialogManager
import br.com.sigga.blog.viewmodels.fragments.AlbumViewModel
import br.com.sigga.blog.viewmodels.states.AlbumState
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.runOnUiThread
import java.lang.ref.WeakReference

class AlbumFragment : MainBaseFragment() {

    private lateinit var binding: FragmentAlbumBinding
    private val customDialogManager: CustomDialogManager by lazy {
        CustomDialogManager(requireContext())
    }

    private val adapter: AlbumAdapter by lazy { AlbumAdapter(requireContext()) }

    private val viewModel: AlbumViewModel by lazy {
        ViewModelProviders.of(this, AlbumViewModel.Factory(adapter)).get(AlbumViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        actionListener?.let {
            it.backButtonEnable(true)
            it.setToolbarVisibility(true)
            it.toggleHomeButton(false)
            it.toogleToolbarLogo(true)
            it.toogleNavigationBar(false)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        context?.let { context ->
            viewModel.state.addOnPropertyChangedCallback(StateCallback(this))

            val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.recyclerAlbums.adapter = adapter
            binding.recyclerAlbums.layoutManager = layoutManager

            binding.swipeAlbums.onRefresh {
                viewModel.findAll()
                binding.swipeAlbums.isRefreshing = false
            }
            viewModel.findAll()
        }
    }

    private fun handleState() {
        when (viewModel.state.get()) {
            AlbumState.SUCCESS -> customDialogManager.dismiss()
            AlbumState.PROCESS -> customDialogManager.showLoading("Processando...")
            AlbumState.ERROR -> {
                customDialogManager.dismiss()
                mainActivity?.parentView?.snackbar(R.string.error_text)
            }
        }
    }


    //region ~~~~ StateCallback ~~~~
    private class StateCallback(view: AlbumFragment) : Observable.OnPropertyChangedCallback() {
        private val context = WeakReference<AlbumFragment>(view)

        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            context.get()?.let {
                if (it.isAdded) {
                    it.runOnUiThread { it.handleState() }
                }
            }
        }
    }
    //endregion
}