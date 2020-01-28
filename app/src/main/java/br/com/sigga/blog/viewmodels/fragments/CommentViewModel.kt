package br.com.sigga.blog.viewmodels.fragments

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.sigga.blog.adapters.CommentAdapter
import br.com.sigga.blog.viewmodels.items.CommentItemViewModel
import br.com.sigga.blog.viewmodels.items.PostItemViewModel
import br.com.sigga.blog.viewmodels.states.CommentState
import br.com.sigga.data.model.Comment
import br.com.sigga.service.CommentService
import br.com.sigga.service.listeners.CommentView
import org.koin.core.KoinComponent
import org.koin.core.inject

class CommentViewModel(
    private val adapter: CommentAdapter
) : ViewModel(), KoinComponent, CommentView {

    val name = ObservableField<String>()
    val body = ObservableField<String>()

    val state = ObservableField<CommentState>()
    private val commentService: CommentService by inject()

    fun findAll(post: PostItemViewModel) {
        state.set(CommentState.PROCESS)
        name.set(post.title)
        body.set(post.body)
        commentService.searchComment(post.id.toLong(), this)
    }

    override fun onSuccess(data: List<Comment>) {
        state.set(CommentState.SUCCESS)
        update(data, true)
    }

    override fun onError(error: String) {
        state.set(CommentState.ERROR)
    }

    private fun update(items: List<Comment>, clear: Boolean) {
        adapter.update(items.map { ev -> CommentItemViewModel.fromComment(ev) }, clear)
    }

    //region ~~~~ Factory ~~~~
    class Factory(
        private val adapter: CommentAdapter
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            CommentViewModel(adapter) as T
    }

}