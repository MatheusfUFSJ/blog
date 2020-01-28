package br.com.sigga.blog.viewmodels.items

import br.com.sigga.data.model.Comment

class CommentItemViewModel(
    val id: String = "",
    var name: String = "",
    var email: String = "",
    var body: String = ""
) {

    companion object {
        fun fromComment(comment: Comment): CommentItemViewModel =
            CommentItemViewModel(comment.id.toString(), comment.name, comment.email, comment.body)
    }

    fun fullName() = "$name - $email"

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is PostItemViewModel) {
            return false
        }

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
