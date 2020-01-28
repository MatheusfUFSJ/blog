package br.com.sigga.blog.viewmodels.fragments

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.sigga.blog.adapters.PostAdapter
import br.com.sigga.blog.viewmodels.items.PostItemViewModel
import br.com.sigga.blog.viewmodels.states.PostState
import br.com.sigga.data.model.Post
import br.com.sigga.service.PostService
import br.com.sigga.service.listeners.PostView
import org.koin.core.KoinComponent
import org.koin.core.inject

class PostViewModel (
    private val adapter: PostAdapter
) : ViewModel(), KoinComponent, PostView {

    val state = ObservableField<PostState>()
    private var currentPage = 1
    private val postService: PostService by inject()

    fun findAll(){
        state.set(PostState.PROCESS)
        postService.searchPost(this)
    }

    override fun onSuccess(data: List<Post>) {
        state.set(PostState.SUCCESS)
        update(data, true)
    }

    override fun onError(error: String) {
        state.set(PostState.ERROR)
    }

    private fun update(items: List<Post>, clear: Boolean) {
        adapter.update(items.map { ev -> PostItemViewModel.fromPost(ev) }, clear)
    }

    //region ~~~~ Factory ~~~~
    class Factory(
        private val adapter: PostAdapter
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = PostViewModel(adapter) as T
    }

}