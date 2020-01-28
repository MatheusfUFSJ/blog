package br.com.sigga.blog.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.sigga.blog.R
import br.com.sigga.blog.adapters.PostAdapter
import br.com.sigga.blog.databinding.FragmentPostBinding
import br.com.sigga.blog.ui.listeners.OnRecyclerViewClickListener
import br.com.sigga.blog.ui.managers.CustomDialogManager
import br.com.sigga.blog.viewmodels.fragments.PostViewModel
import br.com.sigga.blog.viewmodels.items.PostItemViewModel
import br.com.sigga.blog.viewmodels.states.PostState
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.runOnUiThread
import java.lang.ref.WeakReference

class PostFragment : MainBaseFragment(),
    OnRecyclerViewClickListener<PostItemViewModel> {

    private lateinit var binding: FragmentPostBinding
    private val customDialogManager: CustomDialogManager by lazy {
        CustomDialogManager(requireContext())
    }

    private val adapter: PostAdapter by lazy { PostAdapter(this) }

    private val viewModel: PostViewModel by lazy {
        ViewModelProviders.of(this, PostViewModel.Factory(adapter)).get(PostViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        actionListener?.let {
            it.backButtonEnable(false)
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
            binding.recyclerPosts.adapter = adapter
            binding.recyclerPosts.layoutManager = layoutManager

            binding.swipePosts.onRefresh {
                viewModel.findAll()
                binding.swipePosts.isRefreshing = false
            }
            viewModel.findAll()
        }
    }

    private fun handleState() {
        when (viewModel.state.get()) {
            PostState.SUCCESS -> customDialogManager.dismiss()
            PostState.PROCESS -> customDialogManager.showLoading("Processando...")
            PostState.ERROR -> {
                customDialogManager.dismiss()
                mainActivity?.parentView?.snackbar(R.string.error_text)
            }
        }
    }


    //region ~~~~ StateCallback ~~~~
    private class StateCallback(view: PostFragment) : Observable.OnPropertyChangedCallback() {
        private val context = WeakReference<PostFragment>(view)

        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            context.get()?.let {
                if (it.isAdded) {
                    it.runOnUiThread { it.handleState() }
                }
            }
        }
    }

    override fun onItemSelected(item: PostItemViewModel, position: Int) {
        actionListener?.openFragment(CommentFragment.newInstance(item, actionListener))
    }
    //endregion
}