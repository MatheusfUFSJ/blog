package br.com.sigga.blog.viewmodels.items

import br.com.sigga.data.model.Post

class PostItemViewModel(
    val id: String = "",
    var title: String = "",
    var body: String = ""
) {

    companion object {
        fun fromPost(post: Post): PostItemViewModel =
            PostItemViewModel(post.id.toString(), post.title, post.body)
    }

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is PostItemViewModel) {
            return false
        }

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
