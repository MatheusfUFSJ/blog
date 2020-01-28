package br.com.sigga.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.sigga.data.model.Post

@Dao
interface PostDao : GenericDao<Post> {

    @Query("SELECT * FROM Post")
    fun getPost(): List<Post>
}