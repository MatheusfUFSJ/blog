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
import br.com.sigga.blog.activities.listeners.OnFragmentActivityActionListener
import br.com.sigga.blog.adapters.CommentAdapter
import br.com.sigga.blog.databinding.FragmentCommentBinding
import br.com.sigga.blog.ui.listeners.OnRecyclerViewClickListener
import br.com.sigga.blog.ui.managers.CustomDialogManager
import br.com.sigga.blog.viewmodels.fragments.CommentViewModel
import br.com.sigga.blog.viewmodels.items.CommentItemViewModel
import br.com.sigga.blog.viewmodels.items.PostItemViewModel
import br.com.sigga.blog.viewmodels.states.CommentState
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.runOnUiThread
import java.lang.ref.WeakReference

class CommentFragment : MainBaseFragment(),
    OnRecyclerViewClickListener<CommentItemViewModel> {

    companion object {
        fun newInstance(post: PostItemViewModel, listener: OnFragmentActivityActionListener?): CommentFragment {
            val fragment = CommentFragment()
            fragment.actionListener = listener
            fragment.post = post
            return fragment
        }
    }

    private lateinit var binding: FragmentCommentBinding

    private val customDialogManager: CustomDialogManager by lazy {
        CustomDialogManager(requireContext())
    }

    private lateinit var post: PostItemViewModel

    private val adapter: CommentAdapter by lazy { CommentAdapter(this) }

    private val viewModel: CommentViewModel by lazy {
        ViewModelProviders.of(this, CommentViewModel.Factory(adapter)).get(CommentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        actionListener?.let {
            it.setToolbarVisibility(false)
            it.backButtonEnable(true)
            it.toogleNavigationBar(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        context?.let { context ->

            viewModel.state.addOnPropertyChangedCallback(StateCallback(this))
            binding.imageButtonClose.setOnClickListener {  dismiss() }
            val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.recyclerComments.adapter = adapter
            binding.recyclerComments.layoutManager = layoutManager

            if (::post.isInitialized) {
                binding.swipeComments.onRefresh {
                    viewModel.findAll(post)
                    binding.swipeComments.isRefreshing = false
                }

                viewModel.findAll(post)
            }

        }
    }

    private fun handleState() {
        when (viewModel.state.get()) {
            CommentState.SUCCESS -> customDialogManager.dismiss()
            CommentState.PROCESS -> customDialogManager.showLoading("Processando...")
            CommentState.ERROR -> {
                customDialogManager.dismiss()
                mainActivity?.parentView?.snackbar(R.string.error_text)
            }
        }
    }

    //region ~~~~ StateCallback ~~~~
    private class StateCallback(view: CommentFragment) : Observable.OnPropertyChangedCallback() {
        private val context = WeakReference<CommentFragment>(view)

        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            context.get()?.let {
                if (it.isAdded) {
                    it.runOnUiThread { it.handleState() }
                }
            }
        }
    }

    override fun onItemSelected(item: CommentItemViewModel, position: Int) {

    }
    //endregion
}