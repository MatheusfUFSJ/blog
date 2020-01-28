package br.com.sigga.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.sigga.data.model.Comment
import br.com.sigga.data.model.Post

@Dao
interface CommentDao : GenericDao<Comment> {

    @Query("SELECT * FROM Comment where postId = :postId")
    fun getComment(postId: Long): List<Comment>
}